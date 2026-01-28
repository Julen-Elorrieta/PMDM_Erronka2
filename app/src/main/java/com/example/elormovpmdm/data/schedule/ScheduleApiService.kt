package com.example.elormovpmdm.data.schedule

import com.example.elormovpmdm.domain.model.Schedule
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ScheduleApiService {
    @GET("horarios/getHorarios")
    suspend fun getHorario(@Query("id") id: Int): Response<List<Schedule>>
}