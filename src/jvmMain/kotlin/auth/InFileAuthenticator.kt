package auth

import api.AuthResult
import api.authTokenParameterKey

class InFileAuthenticator: Authenticator {

    override fun authenticate(id: String?, password: String?, authToken: String?): AuthResult {
        return when (authToken) {
            null -> authenticate(id, password)
            else -> reAuthenticate(authToken)
        }
    }

    private fun authenticate(id: String?, password: String?): AuthResult {
        println("authenticating with id $id password $password")
        return when {
            id == null -> AuthResult(false, "id is not set")
            password == null -> AuthResult(false, "password is not set")
            else -> AuthResult(true)
        }
    }

    private fun reAuthenticate(authToken: String): AuthResult {
        println("authenticated with authToken $authToken")
        return AuthResult(true)
    }
}