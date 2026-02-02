package com.example.elormovpmdm.data.meetings

import com.example.elormovpmdm.domain.model.Center
import com.example.elormovpmdm.domain.model.CenterListResponse
import com.example.elormovpmdm.domain.model.Meeting
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MeetingsApiService {
    @GET("reuniones/getReuniones/{id}")
    suspend fun getReuniones(@Path ("id") id: Int): Response<List<Meeting>>
    
    @POST("reuniones/create")
    suspend fun addMeeting(@Body request: Meeting): Meeting
    
    @GET("getCenterList")
    suspend fun getCenterList(): Response<CenterListResponse>
}