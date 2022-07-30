import api.recipeIdParameterKey
import atomics.Recipe
import auth.AUTH_TOKEN_COOKIE_ID
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.js.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.browser.*
import auth.AuthRequest
import components.common.LoginState
import io.ktor.client.plugins.cookies.*

val endpoint = window.location.origin // only needed until https://youtrack.jetbrains.com/issue/KTOR-453 is resolved

val cookieStorage = AcceptAllCookiesStorage()

val jsonClient = HttpClient(Js) {
    install(ContentNegotiation) { json() }
    install(Auth) {
        bearer {
            loadTokens {
                println("loadTokens")
                AuthTokenStorage.get()
            }
            refreshTokens {
                println("refreshTokens")
                AuthTokenStorage.get()
            }
        }
    }
    install(HttpCookies) { storage = cookieStorage }
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

suspend fun reAuthenticateWithAuthToken(): LoginState {
    println("authenticateWithAuthToken")
    loadAuthTokenFromCookieToAuthHeader()
    AuthTokenStorage.getString()?.let {
        val response: HttpResponse = jsonClient.post(endpoint + Recipe.reauth_path) {
            contentType(ContentType.Application.Json)
            bearerAuth(it)
        }
        println("authenticateWithAuthToken response: $response")
        if (response.status == HttpStatusCode.OK) {
            return LoginState.LOGGED_IN_AS_LING
        }
    }
    return LoginState.GUEST
}
suspend fun authenticateWithPassword(id: String, password: String): LoginState {
    println("authenticateWithPassword")
    val response: HttpResponse = jsonClient.post(endpoint + Recipe.auth_path) {
        contentType(ContentType.Application.Json)
        setBody(AuthRequest(id, password))
    }
    println("authenticateWithPassword response: $response")
    return when (response.status) {
        HttpStatusCode.OK -> {
            AuthTokenStorage.set(response.body())
            LoginState.LOGGED_IN_AS_LING
        } else -> {
            LoginState.GUEST
        }
    }
}

suspend fun loadAuthTokenFromCookieToAuthHeader() {
    println("loadAuthTokenFromCookieToAuthHeader: ${jsonClient.cookies(endpoint)[AUTH_TOKEN_COOKIE_ID]}")
    println("cookieStorage ${cookieStorage.get(Url(endpoint))}")
    jsonClient.cookies(endpoint + Recipe.auth_path)[AUTH_TOKEN_COOKIE_ID]?.value?.let {
        AuthTokenStorage.set(it)
    }
}