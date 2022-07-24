package api

import io.ktor.http.*
import kotlinx.serialization.Serializable

const val recipeIdParameterKey = "recipeId"

const val authTokenParameterKey = "authToken"
const val loginIdParameterKey = "loginId"
const val loginPasswordParameterKey = "loginPassword"

@Serializable
data class AuthRequest(val id: String, val password:String)

@Serializable
data class ReAuthRequest(val authToken: String)

@Serializable
data class AuthResult(
    val isAuthenticated: Boolean,
    val reason: String = "",
    val token: String = ""
) {
    fun toHttpStatusCode(): HttpStatusCode = when {
        isAuthenticated -> HttpStatusCode.OK
        else -> HttpStatusCode.Unauthorized
    }
}