package com.example.elormovpmdm.data.schedule

import com.example.elormovpmdm.domain.model.Schedule
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ScheduleApiService {
    @GET("horarios/getHorarios/{id}")
    suspend fun getHorario(@Path("id") id: Int): Response<List<Schedule>>
}