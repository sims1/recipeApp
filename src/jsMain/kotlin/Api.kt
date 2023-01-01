import api.recipeIdParameterKey
import atomics.Recipe
import atomics.Recipe.Companion.create_picture_path
import atomics.ingredient.Ingredient
import atomics.ingredient.Seasoning
import auth.AuthRequest
import components.common.LoginState
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.js.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.browser.*
import kotlinx.coroutines.await
import org.khronos.webgl.ArrayBuffer
import org.khronos.webgl.Uint8Array
import org.khronos.webgl.get
import org.w3c.files.File
import org.w3c.files.FileReader
import kotlin.js.Promise


val endpoint = window.location.origin // only needed until https://youtrack.jetbrains.com/issue/KTOR-453 is resolved

val jsonClient = HttpClient(Js) {
    install(ContentNegotiation) { json() }
}

suspend fun getRecipeList(): List<Recipe> {
    return jsonClient.get(endpoint + Recipe.get_all_path).body()
}
suspend fun getRecipesById(recipeId: String): Recipe {
    return jsonClient.get(endpoint + Recipe.get_by_recipe_id_path) {
        contentType(ContentType.Application.Json)
        parameter(recipeIdParameterKey, recipeId)
    }.body()
}

suspend fun getListOfIngredients(): List<Ingredient> {
    return jsonClient.get(endpoint + Recipe.get_ingredients_path).body()
}

suspend fun getListOfSeasonings(): List<Seasoning> {
    return jsonClient.get(endpoint + Recipe.get_seasoning_path).body()
}

suspend fun addIngredient(ingredient: Ingredient): HttpResponse {
    return jsonClient.post(endpoint + Recipe.add_ingredient) {
        contentType(ContentType.Application.Json)
        setBody(ingredient)
    }
}

suspend fun addSeasoning(seasoning: Seasoning): HttpResponse {
    return jsonClient.post(endpoint + Recipe.add_seasoning) {
        contentType(ContentType.Application.Json)
        setBody(seasoning)
    }
}

suspend fun addRecipe(recipe: Recipe): HttpResponse {
    return jsonClient.post(endpoint + Recipe.create_path) {
        contentType(ContentType.Application.Json)
        setBody(recipe)
    }
}

suspend fun reAuthenticateWithAuthToken(): LoginState {
    val response: HttpResponse = jsonClient.post(endpoint + Recipe.reauth_path) {
        contentType(ContentType.Application.Json)
    }
    return when (response.status) {
        HttpStatusCode.OK -> LoginState.loginAs(response.body())
        else -> LoginState.guest()
    }
}
suspend fun authenticateWithPassword(id: String, password: String): LoginState {
    val response: HttpResponse = jsonClient.post(endpoint + Recipe.auth_path) {
        contentType(ContentType.Application.Json)
        setBody(AuthRequest(id, password))
    }
    return when (response.status) {
        HttpStatusCode.OK -> LoginState.loginAs(response.body())
        else -> LoginState.guest()
    }
}

suspend fun uploadRecipePicture(recipeId: String, recipeImage: File): HttpResponse {
    val fileInBytes = fileToByteArray(recipeImage).await()
    return jsonClient.post(endpoint + create_picture_path) {
        setBody(
            MultiPartFormDataContent(
                formData {
                    append("description", recipeId)
                    append("image", fileInBytes, Headers.build {
                        append(HttpHeaders.ContentType, "image/png")
                        append(HttpHeaders.ContentDisposition, "filename=\"$recipeId.png\"")
                    })
                },
                boundary = "WebAppBoundary"
            )
        )
        onUpload { bytesSentTotal, contentLength ->
            //println("Sent $bytesSentTotal bytes from $contentLength")
        }
    }
}

private fun fileToByteArray(file: File) = Promise<ByteArray> { resolve, reject ->
    try {
        val reader = FileReader()
        var fileByteArray = byteArrayOf()
        reader.readAsArrayBuffer(file);
        reader.onloadend = {
            if (reader.readyState == FileReader.DONE) {
                val arrayBuffer = reader.result as ArrayBuffer
                val array = Uint8Array(arrayBuffer)
                for (i in 0..array.length) {
                    fileByteArray += array[i]
                }
            }
            resolve(fileByteArray)
        }
    }
    catch (e: Exception) {
        reject(e)
    }
}
