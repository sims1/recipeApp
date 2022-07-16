package auth

import api.AuthResult

interface Authenticator {

    fun authenticate(id: String?, password: String?, authToken: String?): AuthResult

}