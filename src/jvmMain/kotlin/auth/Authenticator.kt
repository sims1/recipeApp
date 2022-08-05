package auth
class Authenticator {

    companion object {
        fun authenticate(authRequest: AuthRequest): String? {
            return if (InMemoryAuthenticator.verify(authRequest.id, authRequest.password)) {
                JWTVerifier.generateJWTToken(authRequest.id, authRequest.password)
            } else {
                null
            }
        }

        fun authenticate(userSession: UserSession): Boolean {
            return JWTVerifier.validate(userSession.name, userSession.authToken)
        }
    }

}