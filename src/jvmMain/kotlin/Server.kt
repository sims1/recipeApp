import api.*
import atomics.Recipe
import auth.AuthConfig.Companion.AUDIENCE
import auth.AuthConfig.Companion.ISSUER
import auth.InMemoryAuthenticator
import auth.JWTAuthenticator
import store.InMemoryRecipeStore
import store.image.InFileImageStore

import io.ktor.http.*
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
import io.ktor.server.util.*


// if slow, set env variable ORG_GRADLE_PROJECT_isProduction=true
// https://play.kotlinlang.org/hands-on/Full%20Stack%20Web%20App%20with%20Kotlin%20Multiplatform/04_Frontend_Setup

//private val recipeStore = MongoDBRecipeStore()

// the following 2 lines are for testing only
private val recipeStore = InMemoryRecipeStore()
//private val recipeStore = InFileRecipeStore()

private val imageStore = InFileImageStore()

private val authenticator = JWTAuthenticator()

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
        install(Authentication) {
            jwt("auth-jwt") {
                realm = "Access to 'hello'"
                verifier(ISSUER, AUDIENCE, authenticator.algorithm) {
                    acceptLeeway(3)
                }
                validate { credential ->
                    val id = credential.payload.getClaim(JWTAuthenticator.CLAIM_KEY_USER_ID).asString()
                    val password = credential.payload.getClaim(JWTAuthenticator.CLAIM_KEY_USER_PASSWORD).asString()
                    if (InMemoryAuthenticator.verify(id, password)) {
                        JWTPrincipal(credential.payload)
                    } else {
                        null
                    }
                }
                challenge { _, _ ->
                    call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
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
                    val authResult = authenticator.authenticate(authRequest)
                    call.respond(authResult)
                }
            }
            authenticate("auth-jwt") {
                route(Recipe.reauth_path) {
                    post {
                        call.respond(AuthResult(true))
                    }
                }
            }
            //authenticate("auth-jwt") {
                post(Recipe.create_path) {
                    val principal = call.principal<JWTPrincipal>()
                    val username = principal!!.payload.getClaim("username").asString()
                    val expiresAt = principal.expiresAt?.time?.minus(System.currentTimeMillis())
                    call.respondText("Hello, $username! Token is expired at $expiresAt ms.")

                    val receivedRecipe = call.receive<Recipe>()
                    when(recipeStore.add(receivedRecipe)) {
                        true -> call.respond(HttpStatusCode.OK)
                        else -> call.respond(HttpStatusCode.Conflict)
                    }
                }
            //}
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
                    call.respond(inMemoryRecipeStore.getAll())

                    // write in-memory recipe into mongodb
                    //inMemoryRecipeStore.getAll().forEach { recipe -> mongoDBRecipeStore.add(recipe) }
                    //call.respond(mongoDBRecipeStore.getAll())
                }
            }
        }
    }.start(wait = true)
}