package com.example.elormovpmdm.domain.model

import java.sql.Timestamp

data class UserResponse(
    val id: Int,
    val email: String,
    val username: String,
    val password: String,
    val nombre: String?,
    val apellidos: String?,
    val tipo_id: TipoResponse
)

data class TipoResponse(
    val id: Int,
    val nombre: String
)
