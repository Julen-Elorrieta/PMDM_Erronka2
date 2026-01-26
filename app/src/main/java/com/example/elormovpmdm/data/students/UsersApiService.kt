package com.example.elormovpmdm.data.students

import com.example.elormovpmdm.domain.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET

interface UsersApiService {
    @GET("users/getAlumnos")
    suspend fun getStudents(): Response<List<UserResponse>>
    
    @GET("users/getAllUsers")
    suspend fun getAllUsers(): Response<List<UserResponse>>
}