package com.example.elormovpmdm.data.login

import com.example.elormovpmdm.domain.model.User

sealed class SessionState {
    object Loading: SessionState()
    data class Authenticated(val user: User): SessionState()
    object NotAuthenticated: SessionState()
}