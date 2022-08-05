package auth

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import com.auth0.jwt.interfaces.DecodedJWT
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.util.*

class JWTVerifier {

    companion object {

        private val algorithm = createAlgorithm()

        private val verifier: JWTVerifier = JWT.require(algorithm)
            .withAudience(AuthConfig.AUDIENCE)
            .withIssuer(AuthConfig.ISSUER)
            .build()

        fun validate(name: String, authToken: String): Boolean {
            try {
                val jwt: DecodedJWT = verifier.verify(authToken)
                val id = jwt.claims[CLAIM_KEY_USER_ID]!!.asString()
                val password = jwt.claims[CLAIM_KEY_USER_PASSWORD]!!.asString()
                return id == name && InMemoryAuthenticator.verify(id, password)
            } catch (e: TokenExpiredException) {
                println("Error while validating token, expired token: $e")
            } catch (e: JWTVerificationException) {
                println("Error while validating token, invalid token: $e")
            } catch (e: Exception) {
                println("Error while validating token, unknown exception: $e")
            }
            return false
        }

        fun generateJWTToken(userid: String, password: String): String {
            return JWT.create()
                .withAudience(AuthConfig.AUDIENCE)
                .withIssuer(AuthConfig.ISSUER)
                .withClaim(CLAIM_KEY_USER_ID, userid)
                .withClaim(CLAIM_KEY_USER_PASSWORD, password)
                .withExpiresAt(Date(System.currentTimeMillis() + AUTH_TOKEN_EXPIRY_IN_MILLI_SECONDS))
                .sign(algorithm)
        }

        private fun createAlgorithm(): Algorithm {
            val keyPairGen = KeyPairGenerator.getInstance("RSA")
            keyPairGen.initialize(2048)
            val pair: KeyPair = keyPairGen.generateKeyPair()
            return Algorithm.RSA256(pair.public as RSAPublicKey, pair.private as RSAPrivateKey)
        }

        const val CLAIM_KEY_USER_ID = "user_id"
        const val CLAIM_KEY_USER_PASSWORD = "user_password"
        const val UNAUTHORIZED_REASON = "Unauthenticated due to mismatch username and password"
    }
}