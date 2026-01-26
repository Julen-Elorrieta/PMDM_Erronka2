package com.example.elormovpmdm.data.students

import com.example.elormovpmdm.domain.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET

interface StudentsApiService {
    @GET("users/getAlumnos")
    suspend fun getStudents(): Response<List<UserResponse>>
}