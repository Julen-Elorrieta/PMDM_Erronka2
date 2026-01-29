package com.example.elormovpmdm.data.students

import com.example.elormovpmdm.domain.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path
import com.example.elormovpmdm.domain.SessionManager

interface UsersApiService {
    @GET("users/profesor/{profId}/alumnos")
    suspend fun getStudentsFromTeacher(@Path ("profId") id: Int): Response<List<UserResponse>>
    
    @GET("users/getUsers")
    suspend fun getAllUsers(@Query("id") id: Int): Response<List<UserResponse>>
}