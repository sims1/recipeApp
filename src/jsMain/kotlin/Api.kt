import api.*
import atomics.Recipe
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.js.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.browser.*

val endpoint = window.location.origin // only needed until https://youtrack.jetbrains.com/issue/KTOR-453 is resolved

val jsonClient = HttpClient(Js) {
    install(ContentNegotiation) { json() }
}

suspend fun getRecipeList(): List<Recipe> {
    println(endpoint + Recipe.get_all_path)
    return jsonClient.get(endpoint + Recipe.get_all_path).body()
}
suspend fun getRecipesById(recipeId: String): Recipe {
    return jsonClient.get(endpoint + Recipe.get_by_recipe_id_path) {
        contentType(ContentType.Application.Json)
        parameter(recipeIdParameterKey, recipeId)
    }.body()
}

suspend fun addRecipe(recipe: Recipe): HttpResponse {
    return jsonClient.post(endpoint + Recipe.create_path) {
        contentType(ContentType.Application.Json)
        setBody(recipe)
    }
}

suspend fun authenticate(id: String, password: String): HttpResponse {
    return jsonClient.post(endpoint + Recipe.auth_path) {
        contentType(ContentType.Application.Json)
        setBody(AuthRequest(id, password))
    }
}

suspend fun reAuthenticate(authToken: String): HttpResponse {
    return jsonClient.post(endpoint + Recipe.reauth_path) {
        contentType(ContentType.Application.Json)
        setBody(ReAuthRequest(authToken))
    }
}