package com.example.elormovpmdm.data.login

import com.example.elormovpmdm.domain.model.LoginRequest
import com.example.elormovpmdm.domain.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiService {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<UserResponse>
}