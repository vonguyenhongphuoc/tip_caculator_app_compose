package com.devhp.firstcompose.model

data class MUser(
    val iD: String?,
    val userID: String,
    val displayName: String,
    val avatarUrl: String,
    val quote: String,
    val profession: String,
) {
    fun toMap(): MutableMap<String, Any> {
        return mutableMapOf(
            "user_id" to this.userID,
            "display_name" to this.displayName,
            "quote" to this.quote,
            "profession" to this.profession,
            "avatar_url" to this.avatarUrl
        )
    }
}