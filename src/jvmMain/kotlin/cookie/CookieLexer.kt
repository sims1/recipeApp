package cookie

import auth.AUTH_TOKEN_COOKIE_ID
import auth.AUTH_TOKEN_EXPIRY_IN_MILLI_SECONDS
import java.text.SimpleDateFormat
import java.util.*

class CookieLexer {

    companion object {

        fun build(authToken: String): String {
            val expiresDate = Date(System.currentTimeMillis() + AUTH_TOKEN_EXPIRY_IN_MILLI_SECONDS)
            return "$AUTH_TOKEN_COOKIE_ID=$authToken; $EXPIRES=${expiresDate.toGMTString()}"
        }

        private const val EXPIRES = "Expires"

        private val dateFormat = SimpleDateFormat().apply {
            timeZone = TimeZone.getTimeZone("GMT")
        }
    }
}