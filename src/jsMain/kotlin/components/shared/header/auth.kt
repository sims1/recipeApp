package components.shared.header

class LoginState private constructor(private val userName: String? = null) {

    var message: String = when (userName) {
        null ->  "Log in"
        else -> "Hi, $userName!"
    }
    fun isGuest() = (userName == null)

    companion object {

        fun guest() = LoginState()
        fun loginAs(userName: String) = LoginState(userName)
    }
}
