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
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.*
import org.litote.kmongo.reactivestreams.KMongo

val client = KMongo.createClient().coroutine
val recipesDatabase = client.getDatabase("recipes")
val recipesCollection = recipesDatabase.getCollection<Recipe>()

// if slow, set env variable ORG_GRADLE_PROJECT_isProduction=true
// https://play.kotlinlang.org/hands-on/Full%20Stack%20Web%20App%20with%20Kotlin%20Multiplatform/04_Frontend_Setup

// return true if inserted successfully
// return false if already exists
private suspend fun CoroutineCollection<Recipe>.insertIfNotExist(recipe: Recipe): Boolean {
    return when (this.findOne(Recipe::id eq recipe.id)) {
        null -> {
            this.insertOne(recipe)
            true
        }
        else -> false
    }
}

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
                    val allRecipes = recipesCollection.find().toList()
                    if (allRecipes.isNotEmpty()) {
                        call.respond(allRecipes)
                    } else {
                        InMemoryRecipeStore.getAll().forEach { recipe -> recipesCollection.insertIfNotExist(recipe) }
                        call.respond(recipesCollection.find().toList())
                    }
                }
            }
            route(Recipe.get_by_recipe_id_path) {
                get {
                    val recipeId = call.parameters[recipeIdParameterKey]!!
                    val recipe = recipesCollection.findOne(Recipe::id eq recipeId) as Recipe
                    call.respond(recipe)
                }
            }
            route(Recipe.create_path) {
                post {
                    val receivedRecipe = call.receive<Recipe>()
                    when(recipesCollection.insertIfNotExist(receivedRecipe)) {
                        true -> call.respond(HttpStatusCode.OK)
                        else -> call.respond(HttpStatusCode.Conflict)
                    }
                }
            }
        }
    }.start(wait = true)
}