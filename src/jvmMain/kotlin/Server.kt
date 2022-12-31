import api.recipeIdParameterKey
import atomics.Recipe
import auth.AUTH_TOKEN_EXPIRY_IN_SECONDS
import auth.AuthRequest
import auth.Authenticator
import auth.JWTVerifier.Companion.UNAUTHORIZED_REASON
import auth.UserSession
import store.recipe.InMemoryRecipeStore

import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.util.*
import store.DatabaseClients
import store.image.RedisImageStore
import store.ingredientType.InFileIngredientTypeStore
import store.ingredientType.InMemoryIngredientTypeStore
import store.ingredientType.MongoDBIngredientTypeStore
import store.recipe.InFileRecipeStore
import store.recipe.MongoDBRecipeStore
import store.spiceAndSauceType.InFileSpiceAndSauceTypeStore
import store.spiceAndSauceType.InMemorySpiceAndSauceTypeStore
import store.spiceAndSauceType.MongoDBSpiceAndSauceTypeStore
import java.util.concurrent.TimeUnit


// if slow, set env variable ORG_GRADLE_PROJECT_isProduction=true
// https://play.kotlinlang.org/hands-on/Full%20Stack%20Web%20App%20with%20Kotlin%20Multiplatform/04_Frontend_Setup

private val recipeStore =
    MongoDBRecipeStore()
    //RedisRecipeStore() // not used
    //InMemoryRecipeStore() // testing only
// how to back up
// 1. go to http://0.0.0.0:9090/getall
// 2. copy the content, and paste in RecipeBackUp.txt
// 3. enable the next line
    //InFileRecipeStore() // backup only

private val ingredientTypeStore =
    MongoDBIngredientTypeStore()
    //InMemoryIngredientTypeStore() // testing only
    //InFileIngredientTypeStore()  // backup only

private val spiceAndSauceTypeStore =
    MongoDBSpiceAndSauceTypeStore()
    //InMemorySpiceAndSauceTypeStore() // testing only
    //InFileSpiceAndSauceTypeStore()  // backup only

private val imageStore =
    //MongoDBImageStore()
    RedisImageStore()
    //InFileImageStore() // backup only
    //TestingImageStore() // testing only
fun main() {
    val server = embeddedServer(Netty, 9090) {
        install(ContentNegotiation) {
            json()
        }
        install(CORS) {
            allowMethod(HttpMethod.Get)
            allowMethod(HttpMethod.Post)
            allowMethod(HttpMethod.Delete)
            //allowNonSimpleContentTypes = true
            allowHeader(HttpHeaders.ContentType)
            anyHost()
        }
        install(Compression) {
            gzip()
        }
        install(Sessions) {
            cookie<UserSession>("user_session") {
                cookie.path = "/"
                cookie.maxAgeInSeconds = AUTH_TOKEN_EXPIRY_IN_SECONDS
            }
        }
        install(Authentication) {
            session<UserSession>("reauth-session") {
                validate { session ->
                    when {
                        Authenticator.authenticate(session) -> session
                        else -> null
                    }
                }
                challenge {
                    call.respond(HttpStatusCode.Unauthorized, "Token is invalid or has expired")
                }
            }
        }
        routing {
            get("/") {
                call.respondText(
                    this::class.java.classLoader.getResource("index.html")!!.readText(),
                    ContentType.Text.Html
                )
            }
            static("/") {
                resources("")
            }
            route(Recipe.get_all_path) {
                get {
                    call.respond(recipeStore.getAll())
                }
            }
            route(Recipe.get_ingredient_types_path) {
                get {
                    call.respond(ingredientTypeStore.getAll().sortedBy { it.getValue() })
                }
            }
            route(Recipe.get_spice_and_sauce_types_path) {
                get {
                    call.respond(spiceAndSauceTypeStore.getAll().sortedBy { it.getValue() })
                }
            }
            route(Recipe.auth_path) {
                post {
                    val authRequest = call.receive<AuthRequest>()
                    when (val authToken = Authenticator.authenticate(authRequest)) {
                        null -> call.respond(HttpStatusCode.Unauthorized, UNAUTHORIZED_REASON)
                        else -> {
                            call.sessions.set(UserSession(authRequest.id, authToken))
                            call.respond(HttpStatusCode.OK, authRequest.id)
                        }
                    }
                }
            }
            authenticate("reauth-session") {
                route(Recipe.reauth_path) {
                    post {
                        val userSession = call.sessions.get<UserSession>()!!
                        call.respond(HttpStatusCode.OK, userSession.name)
                    }
                }
            }
            authenticate("reauth-session") {
                post(Recipe.create_path) {
                    val receivedRecipe = call.receive<Recipe>()
                    val recipe = when(val userName = call.principal<UserSession>()?.name) {
                        null -> receivedRecipe
                        else -> receivedRecipe.createNewWithAuthor(userName)
                    }
                    when {
                        recipeStore.add(recipe) -> call.respond(HttpStatusCode.OK)
                        else -> call.respond(HttpStatusCode.Conflict)
                    }
                }
            }

            post(Recipe.create_picture_path) {
                var recipeId = ""
                val multipartData = call.receiveMultipart()
                multipartData.forEachPart { part ->
                    when (part) {
                        is PartData.FormItem -> {
                            recipeId = part.value
                        }
                        is PartData.FileItem -> {
                            val fileBytes = part.streamProvider().readBytes()
                            imageStore.save(recipeId, fileBytes)
                            call.respond(HttpStatusCode.OK)
                        }
                        else -> {
                            println("Error while assembling multipart: ${part.javaClass.canonicalName}")
                            call.respond(HttpStatusCode.InternalServerError)
                        }
                    }
                }
            }

            route(Recipe.get_by_recipe_id_path) {
                get {
                    val recipeId = call.request.queryParameters.getOrFail(recipeIdParameterKey)
                    val recipe = recipeStore.get(recipeId)
                    call.respond(recipe)
                }
            }
            route(Recipe.get_image_by_recipe_id_path) {
                get {
                    val recipeId = call.request.queryParameters.getOrFail(recipeIdParameterKey)
                    val recipeImage = imageStore.get(recipeId)
                    call.respondFile(recipeImage)
                }
            }
            // debug purpose
            route(Recipe.load_recipe_from_in_memory_path) {
                get {
                    val inMemoryRecipeStore = InMemoryRecipeStore()
                    // write in-memory recipe into recipeStore
                    inMemoryRecipeStore.getAll().forEach { recipe -> recipeStore.add(recipe) }
                    call.respond(inMemoryRecipeStore.getAll())
                }
            }
            route(Recipe.load_recipe_from_in_file_path) {
                get {
                    val inFileRecipeStore = InFileRecipeStore()
                    // write in-file recipe into recipeStore
                    inFileRecipeStore.getAll().forEach { recipe -> recipeStore.add(recipe) }
                    call.respond(inFileRecipeStore.getAll())
                }
            }
            route(Recipe.load_ingredient_type_from_in_file_path) {
                get {
                    val inFileIngredientTypeStore = InFileIngredientTypeStore()
                    // write in-file IngredientType into ingredientTypeStore
                    inFileIngredientTypeStore.getAll().forEach { ingredientType -> ingredientTypeStore.add(ingredientType) }
                    call.respond(inFileIngredientTypeStore.getAll())
                }
            }
            route(Recipe.load_spice_and_sauce_type_from_in_file_path) {
                get {
                    val inFileSpiceAndSauceTypeStore = InFileSpiceAndSauceTypeStore()
                    // write in-file SpiceAndSauceType into spiceAndSauceTypeStore
                    inFileSpiceAndSauceTypeStore.getAll().forEach { spiceAndSauceType -> spiceAndSauceTypeStore.add(spiceAndSauceType) }
                    call.respond(inFileSpiceAndSauceTypeStore.getAll())
                }
            }
        }
    }.start(wait = true)

    Runtime.getRuntime().addShutdownHook(Thread {
        server.stop(1, 5, TimeUnit.SECONDS)
        recipeStore.shutDown()
        imageStore.shutDown()
        DatabaseClients.shutDown()
    })
}