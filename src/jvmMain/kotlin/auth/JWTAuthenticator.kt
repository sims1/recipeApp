package auth

import auth.JWTAuthenticator.Companion.CLAIM_KEY_USER_ID
import auth.JWTAuthenticator.Companion.CLAIM_KEY_USER_PASSWORD
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.util.*

class JWTAuthenticator: Authenticator {

    val algorithm: Algorithm
    init {
        val keyPairGen = KeyPairGenerator.getInstance("RSA")
        keyPairGen.initialize(2048)
        val pair: KeyPair = keyPairGen.generateKeyPair()
        this.algorithm = Algorithm.RSA256(pair.public as RSAPublicKey, pair.private as RSAPrivateKey)
    }

    override fun authenticate(authRequest: AuthRequest): Boolean {
        println("authenticating with id ${authRequest.id} password ${authRequest.password}")
        return InMemoryAuthenticator.verify(authRequest.id, authRequest.password)
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

    companion object {

        const val CLAIM_KEY_USER_ID = "user_id"
        const val CLAIM_KEY_USER_PASSWORD = "user_password"
        const val UNAUTHORIZED_REASON = "Unauthenticated due to mismatch username and password"
    }
}

suspend fun ApplicationCall.validate(credential: JWTCredential): Principal? {
    val id = credential.payload.getClaim(CLAIM_KEY_USER_ID).asString()
    val password = credential.payload.getClaim(CLAIM_KEY_USER_PASSWORD).asString()
    return when (InMemoryAuthenticator.verify(id, password)) {
        true -> JWTPrincipal(credential.payload)
        else -> null
    }
}