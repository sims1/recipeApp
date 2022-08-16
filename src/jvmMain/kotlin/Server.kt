import api.recipeIdParameterKey
import api.recipeImageParameterKey
import atomics.Recipe
import auth.AUTH_TOKEN_EXPIRY_IN_SECONDS
import auth.AuthRequest
import auth.Authenticator
import auth.JWTVerifier.Companion.UNAUTHORIZED_REASON
import auth.UserSession
import store.InMemoryRecipeStore
import store.image.InFileImageStore

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
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.util.*
import store.InFileRecipeStore
import store.MongoDBRecipeStore
import java.io.File


// if slow, set env variable ORG_GRADLE_PROJECT_isProduction=true
// https://play.kotlinlang.org/hands-on/Full%20Stack%20Web%20App%20with%20Kotlin%20Multiplatform/04_Frontend_Setup

private val recipeStore = MongoDBRecipeStore()

// the following 2 lines are for testing only
//private val recipeStore = InMemoryRecipeStore()
//private val recipeStore = InFileRecipeStore()

private val imageStore = InFileImageStore()
fun main() {
    embeddedServer(Netty, 9090) {
        install(ContentNegotiation) {
            json()
        }
        install(CORS) {
            allowMethod(HttpMethod.Get)
            allowMethod(HttpMethod.Post)
            allowMethod(HttpMethod.Delete)
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
            route(Recipe.auth_path) {
                post {
                    val authRequest = call.receive<AuthRequest>()
                    when (val authToken = Authenticator.authenticate(authRequest)) {
                        null -> call.respond(HttpStatusCode.Unauthorized, UNAUTHORIZED_REASON)
                        else -> {
                            call.sessions.set(UserSession(authRequest.id, authToken))
                            call.respond(HttpStatusCode.OK, authToken)
                        }
                    }
                }
            }
            authenticate("reauth-session") {
                route(Recipe.reauth_path) {
                    post {
                        call.respond(HttpStatusCode.OK)
                    }
                }
            }
            authenticate("reauth-session") {
                post(Recipe.create_path) {
                    val receivedRecipe = call.receive<Recipe>()
                    when {
                        recipeStore.add(receivedRecipe) -> call.respond(HttpStatusCode.OK)
                        else -> call.respond(HttpStatusCode.Conflict)
                    }
                }
            }

            post(Recipe.create_picture_path) {
                val multipartData = call.receiveMultipart()
                multipartData.forEachPart { part ->
                    when (part) {
                        is PartData.FileItem -> {
                            val fileName = part.originalFileName as String
                            val fileBytes = part.streamProvider().readBytes()
                            imageStore.save(fileName, fileBytes)
                            call.respond(HttpStatusCode.OK)
                        }
                        else -> {
                            println("Error while assembling multipart: ${part.javaClass.canonicalName}")
                            call.respond(HttpStatusCode.InternalServerError)
                        }
                    }
                }

                call.respond(HttpStatusCode.OK)
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
                    //val recipe = imageStore.get(recipeId)
                    val recipeImage = imageStore.get(recipeId)
                    call.respondFile(recipeImage)
                }
            }
            // debug purpose
            route(Recipe.get_in_memory_path) {
                get {
                    val inMemoryRecipeStore = InMemoryRecipeStore()
                    // write in-memory recipe into mongodb
                    inMemoryRecipeStore.getAll().forEach { recipe -> recipeStore.add(recipe) }
                    call.respond(inMemoryRecipeStore.getAll())
                }
            }
        }
    }.start(wait = true)
}