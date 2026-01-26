package com.example.elormovpmdm.domain.model

import java.sql.Timestamp

data class UserResponse(
    val id: Int,
    val email: String,
    val username: String,
    val nombre: String,
    val apellidos: String,
    val tipoId: Int,
    val direccion: String,
    val telefono1: String
)
