package auth
interface Authenticator {

    fun authenticate(authRequest: AuthRequest): Boolean

}