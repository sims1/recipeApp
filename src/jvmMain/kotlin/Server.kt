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
import store.image.InFileImageStore
import java.io.File

// if slow, set env variable ORG_GRADLE_PROJECT_isProduction=true
// https://play.kotlinlang.org/hands-on/Full%20Stack%20Web%20App%20with%20Kotlin%20Multiplatform/04_Frontend_Setup

//private val recipeStore = MongoDBRecipeStore()

// the following 2 lines are for testing only
private val recipeStore = InMemoryRecipeStore()
//private val recipeStore = InFileRecipeStore()

private val imageStore = InFileImageStore()

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
                    call.respond(recipeStore.getAll())
                }
            }
            route(Recipe.get_by_recipe_id_path) {
                get {
                    val recipeId = call.parameters[recipeIdParameterKey]!!
                    val recipe = recipeStore.get(recipeId)
                    call.respond(recipe)
                }
            }
            route(Recipe.create_path) {
                post {
                    val receivedRecipe = call.receive<Recipe>()
                    when(recipeStore.add(receivedRecipe)) {
                        true -> call.respond(HttpStatusCode.OK)
                        else -> call.respond(HttpStatusCode.Conflict)
                    }
                }
            }
            route(Recipe.get_image_by_recipe_id_path) {
                get {
                    val recipeId = call.parameters[recipeIdParameterKey]!!
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