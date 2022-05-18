import api.recipeIdParameterKey
import atomics.Recipe
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import store.InMemoryRecipeStore
import store.MongoDBStore

// if slow, set env variable ORG_GRADLE_PROJECT_isProduction=true
// https://play.kotlinlang.org/hands-on/Full%20Stack%20Web%20App%20with%20Kotlin%20Multiplatform/04_Frontend_Setup

fun main() {
    embeddedServer(Netty, 9090) {
        install(ContentNegotiation) {
            json()
        }
        install(CORS) {
            method(HttpMethod.Get)
            method(HttpMethod.Post)
            method(HttpMethod.Delete)
            anyHost()
        }
        install(Compression) {
            gzip()
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
                    val allRecipes = MongoDBStore.getAll()
                    if (allRecipes.isNotEmpty()) {
                        call.respond(allRecipes)
                    } else {
                        InMemoryRecipeStore.getAll().forEach { recipe -> MongoDBStore.add(recipe) }
                        call.respond(MongoDBStore.getAll())
                    }
                }
            }
            route(Recipe.get_by_recipe_id_path) {
                get {
                    val recipeId = call.parameters[recipeIdParameterKey]!!
                    val recipe = MongoDBStore.get(recipeId)
                    call.respond(recipe)
                }
            }
            route(Recipe.create_path) {
                post {
                    val receivedRecipe = call.receive<Recipe>()
                    when(MongoDBStore.add(receivedRecipe)) {
                        true -> call.respond(HttpStatusCode.OK)
                        else -> call.respond(HttpStatusCode.Conflict)
                    }
                }
            }
        }
    }.start(wait = true)
}