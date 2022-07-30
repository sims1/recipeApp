import io.ktor.client.plugins.auth.providers.*

class AuthTokenStorage {
    companion object {

        suspend fun get(): BearerTokens? {
            loadAuthTokenFromCookieToAuthHeader()
            return authToken?.let { BearerTokens(authToken!!, "dummyToken") }
        }

        fun getString() = authToken

        fun set(token: String) {
            authToken = token
        }

        private var authToken: String? = null
    }
}