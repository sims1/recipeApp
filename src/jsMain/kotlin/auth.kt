import io.ktor.client.plugins.auth.providers.*

class AuthTokenStorage {
    companion object {

        fun get(): BearerTokens? {
            return authToken?.let { BearerTokens(authToken!!, "dummyToken") }
        }

        fun getString() = authToken

        fun set(token: String) {
            authToken = token
        }

        private var authToken: String? = null
    }
}