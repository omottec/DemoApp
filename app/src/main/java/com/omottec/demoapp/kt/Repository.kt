package com.omottec.demoapp.kt

// extension property
val User.formattedName: String
    get() {
        return if (lastName != null) {
            if (firstName != null) {
                "$firstName $lastName"
            } else {
                lastName ?: "Unknown"
            }
        } else {
            firstName ?: "Unknown"
        }
    }

object Repository {
    private val _users = mutableListOf(User("Jane", ""), User("John", null), User("Anne", "Doe"))

    val users: List<User>
        get() = _users

    val formattedUserNames: List<String>
        get() {
            return _users.map { user ->
                user.formattedName
            }
        }
}