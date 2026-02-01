package com.example.elormovpmdm.domain.model

data class CreateMeetingRequest(
    val estado: String,
    val aula: String?,
    val fecha: String?,
    val id_centro: String?,
    val usersByAlumnoId: User,
    val usersByProfesorId: User
)

