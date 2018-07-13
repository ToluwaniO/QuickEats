package toluog.quickeats.model

data class User(var uid: String = "", var email: String = "", var displayName: String = "") {
    fun toMap() = mapOf<Any,Any>(
            "uid" to uid,
            "email" to email,
            "displayName" to displayName
    )
}