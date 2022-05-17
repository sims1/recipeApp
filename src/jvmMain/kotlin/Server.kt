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
val recipesCollection = recipesDatabase.getCollection<ShoppingListItem>()

val shoppingListDatabase = client.getDatabase("shoppingList")
val shoppingListCollection = shoppingListDatabase.getCollection<ShoppingListItem>()

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
                    call.respond(TemporaryInMemoryRecipeStore.getAll())
                }
            }
            route(Recipe.get_by_recipe_id_path) {
                get {
                    val recipeId = call.parameters[recipeIdParameterKey]!!
                    call.respond(TemporaryInMemoryRecipeStore.get(recipeId))
                }
            }
            route(Recipe.create_path) {
                post {
                    TemporaryInMemoryRecipeStore.add(call.receive<Recipe>())
                    call.respond(HttpStatusCode.OK)
                }
            }
            route(ShoppingListItem.path) {
                get {
                    call.respond(shoppingListCollection.find().toList())
                }
                post {
                    shoppingListCollection.insertOne(call.receive<ShoppingListItem>())
                    call.respond(HttpStatusCode.OK)
                }
                delete("/{id}") {
                    val id = call.parameters["id"]?.toInt() ?: error("Invalid delete request")
                    shoppingListCollection.deleteOne(ShoppingListItem::id eq id)
                    call.respond(HttpStatusCode.OK)
                }
            }
        }
    }.start(wait = true)
}