package auth

import kotlinx.serialization.Serializable

//const val AUTH_TOKEN_EXPIRY_IN_DAYS= 7
//const val AUTH_TOKEN_EXPIRY_IN_MILLI_SECONDS: Int = AUTH_TOKEN_EXPIRY_IN_DAYS * 24 * 60 * 60 * 1000

const val AUTH_TOKEN_EXPIRY_IN_MINUTES = 120L

const val AUTH_TOKEN_EXPIRY_IN_SECONDS: Long = AUTH_TOKEN_EXPIRY_IN_MINUTES * 60
const val AUTH_TOKEN_EXPIRY_IN_MILLI_SECONDS: Long = AUTH_TOKEN_EXPIRY_IN_MINUTES * 60 * 1000

const val LING: String = "Ling"
@Serializable
data class AuthRequest(val id: String, val password:String)