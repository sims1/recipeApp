package auth

class InMemoryAuthenticator {

    companion object {
        fun verify(id: String, password: String): Boolean {
            val credentialValue = credentialMap[id]
            return (credentialValue != null && credentialValue == password)
        }

        private val credentialMap = mapOf("Ling" to "ok")
    }
/*
    fun validate(token: String) {
        val verifier: JWTVerifier = JWT.require(algorithm)
            .withAudience(AUDIENCE)
            .withIssuer(ISSUER)
            .build() //Reusable verifier instance

        println("Validating token")
        try {
            val jwt: DecodedJWT = verifier.verify(token)
            println("Validated token with " +
                    "$CLAIM_KEY_USER_ID=${jwt.claims[CLAIM_KEY_USER_ID]} " +
                    "$CLAIM_KEY_USER_PASSWORD=${jwt.claims[CLAIM_KEY_USER_PASSWORD]}"
            )
        } catch (e: TokenExpiredException) {
            println("Error while validating token, expired token: $e")
        } catch (e: JWTVerificationException) {
            println("Error while validating token, invalid token: $e")
        } catch (e: Exception) {
            println("Error while validating token, unknown exception: $e")
        }
    }
*/

}
