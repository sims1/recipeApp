import api.*
import atomics.Recipe
import io.ktor.http.*
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer

import kotlinx.browser.window

val endpoint = window.location.origin // only needed until https://youtrack.jetbrains.com/issue/KTOR-453 is resolved

val jsonClient = HttpClient {
    install(JsonFeature) { serializer = KotlinxSerializer() }
}

suspend fun getRecipeList(): List<Recipe> {
    return jsonClient.get(endpoint + Recipe.get_all_path)
}
suspend fun getRecipesById(recipeId: String): Recipe {
    return jsonClient.get(endpoint + Recipe.get_by_recipe_id_path) {
        contentType(ContentType.Application.Json)
        parameter(recipeIdParameterKey, recipeId)
    }
}

suspend fun addRecipe(recipe: Recipe) {
    jsonClient.post<Unit>(endpoint + Recipe.create_path) {
        contentType(ContentType.Application.Json)
        body = recipe
    }
}

suspend fun authenticate(id: String, password: String): AuthResult {
    return jsonClient.post(endpoint + Recipe.log_in_path) {
        contentType(ContentType.Application.Json)
        parameter(loginIdParameterKey, id)
        parameter(loginPasswordParameterKey, password)
    }
}

suspend fun authenticate(authToken: String): AuthResult {
    return jsonClient.post(endpoint + Recipe.log_in_path) {
        contentType(ContentType.Application.Json)
        parameter(authTokenParameterKey, authToken)
    }
}