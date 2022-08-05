package auth

class InMemoryAuthenticator {
    companion object {

        fun verify(id: String, password: String): Boolean {
            val credentialValue = credentialMap[id]
            return (credentialValue != null && credentialValue == password)
        }

        private val credentialMap = mapOf("Ling" to "ok")
    }

}