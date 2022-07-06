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
import store.InFileRecipeStore
import store.InMemoryRecipeStore
import store.MongoDBRecipeStore

// if slow, set env variable ORG_GRADLE_PROJECT_isProduction=true
// https://play.kotlinlang.org/hands-on/Full%20Stack%20Web%20App%20with%20Kotlin%20Multiplatform/04_Frontend_Setup

private val mongoDBRecipeStore = MongoDBRecipeStore()
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
                    call.respond(mongoDBRecipeStore.getAll())
                }
            }
            route(Recipe.get_by_recipe_id_path) {
                get {
                    val recipeId = call.parameters[recipeIdParameterKey]!!
                    val recipe = mongoDBRecipeStore.get(recipeId)
                    call.respond(recipe)
                }
            }
            route(Recipe.create_path) {
                post {
                    val receivedRecipe = call.receive<Recipe>()
                    when(mongoDBRecipeStore.add(receivedRecipe)) {
                        true -> call.respond(HttpStatusCode.OK)
                        else -> call.respond(HttpStatusCode.Conflict)
                    }
                }
            }
            // debug purpose
            route(Recipe.get_in_memory_path) {
                get {
                    val inMemoryRecipeStore = InMemoryRecipeStore()
                    call.respond(inMemoryRecipeStore.getAll())

                    // write in-memory recipe into mongodb
                    inMemoryRecipeStore.getAll().forEach { recipe -> mongoDBRecipeStore.add(recipe) }
                    call.respond(mongoDBRecipeStore.getAll())
                }
            }
            // debug purpose
            route(Recipe.get_in_file_path) {
                get {
                    val inFileRecipeStore = InFileRecipeStore()
                    call.respond(inFileRecipeStore.getAll())
                }
            }
        }
    }.start(wait = true)
}