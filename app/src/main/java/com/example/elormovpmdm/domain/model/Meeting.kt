package com.example.elormovpmdm.domain.model

data class Meeting (
    val id_reunion: Int,
    val estado: String,
    val aula: String,
    val fecha: String,
    val usersByAlumnoId: User,
    val usersByProfesorId: User
)