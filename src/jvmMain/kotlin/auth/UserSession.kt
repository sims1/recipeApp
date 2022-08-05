package auth

import io.ktor.server.auth.*

data class UserSession(val name:String, val authToken: String) : Principal