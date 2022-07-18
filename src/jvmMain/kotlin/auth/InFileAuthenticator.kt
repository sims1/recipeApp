package auth

import api.AuthRequest
import api.AuthResult
import api.ReAuthRequest

class InFileAuthenticator: Authenticator {
    override fun authenticate(authRequest: AuthRequest): AuthResult {
        println("authenticating with id ${authRequest.id} password ${authRequest.password}")
        return AuthResult(true) // todo
    }

    override fun reAuthenticate(reAuthRequest: ReAuthRequest): AuthResult {
        println("authenticated with authToken ${reAuthRequest.authToken}")
        return AuthResult(true)
    }
}