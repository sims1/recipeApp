import api.*
import atomics.Recipe
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.js.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.util.date.*
import kotlinx.browser.*
import auth.AUTH_TOKEN_EXPIRY_IN_MILLI_SECONDS
import components.common.LoginState
import kotlin.js.Date

val cookiesStorage = AcceptAllCookiesStorage()

val endpoint = window.location.origin // only needed until https://youtrack.jetbrains.com/issue/KTOR-453 is resolved

val jsonClient = HttpClient(Js) {
    install(ContentNegotiation) { json() }
    install(Auth) {
        bearer {
            loadTokens {
                println("loadTokens 1")
                // Load tokens from a local storage and return them as the 'BearerTokens' instance
                val authToken = cookiesStorage.get(Url(endpoint + Recipe.auth_path))
                println("loadTokens 2 $authToken")
                when (authToken.isNullOrEmpty() || authToken.last() != null) {
                    true -> null
                    else -> BearerTokens(authToken.last().value, "dummyToken")
                }
            }
        }
    }
    install(HttpCookies) {
        storage = cookiesStorage
    }
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
    jsonClient
    return jsonClient.post(endpoint + Recipe.create_path) {
        contentType(ContentType.Application.Json)
        setBody(recipe)
    }
}

suspend fun authenticate(id: String, password: String): LoginState {
    val result: AuthResult = jsonClient.post(endpoint + Recipe.auth_path) {
        contentType(ContentType.Application.Json)
        setBody(AuthRequest(id, password))
    }.body()
    return when {
        result.isAuthenticated -> {
            cookiesStorage.addCookie(
                endpoint + Recipe.auth_path,
                Cookie(
                    "authToken",
                    result.token,
                    expires = GMTDate(Date.now().toLong() + AUTH_TOKEN_EXPIRY_IN_MILLI_SECONDS)
                )
            )
            LoginState.LOGGED_IN_AS_LING
        }
        else -> LoginState.GUEST
    }
}

suspend fun reAuthenticate(): HttpResponse {
    val authToken = jsonClient.cookies(endpoint + Recipe.auth_path).last()
    return jsonClient.post(endpoint + Recipe.reauth_path) {
        contentType(ContentType.Application.Json)
        setBody(ReAuthRequest(authToken.value))
    }
}