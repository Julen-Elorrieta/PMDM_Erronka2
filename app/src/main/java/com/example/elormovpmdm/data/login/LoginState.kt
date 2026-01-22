package com.example.elormovpmdm.data.login

import com.example.elormovpmdm.domain.model.UserResponse

sealed class LoginState {
    object Loading: LoginState()
    data class Success(val user: UserResponse): LoginState()
    data class Error(val message: String): LoginState()
    object Idle: LoginState() // Estado inicial (esperando a que el usuario puelse el botónd de login)
}