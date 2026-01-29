package com.example.elormovpmdm.data.students

import com.example.elormovpmdm.domain.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path

interface UsersApiService {
    @GET("users/profesor/{profId}/alumnos")
    suspend fun getStudentsFromTeacher(@Path ("profId") id: Int): Response<List<User>>
    
    @GET("users/getUsers")
    suspend fun getAllUsers(@Query("id") id: Int): Response<List<User>>
}