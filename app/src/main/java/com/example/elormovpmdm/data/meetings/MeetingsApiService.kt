package com.example.elormovpmdm.data.meetings

import com.example.elormovpmdm.domain.model.Meeting
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MeetingsApiService {
    @GET("reuniones/getReuniones/{id}")
    suspend fun getReuniones(@Path ("id") id: Int): Response<List<Meeting>>
}