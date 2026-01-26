package com.example.elormovpmdm.domain

object SessionManager {
    var currentUser: com.example.elormovpmdm.domain.model.UserResponse? = null

    fun clearSession() {
        currentUser = null
    }
}