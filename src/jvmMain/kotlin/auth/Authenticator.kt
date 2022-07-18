package auth

import api.AuthRequest
import api.AuthResult
import api.ReAuthRequest

interface Authenticator {

    fun authenticate(authRequest: AuthRequest): AuthResult

    fun reAuthenticate(reAuthRequest: ReAuthRequest): AuthResult

}