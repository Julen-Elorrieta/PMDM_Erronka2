package com.example.elormovpmdm.data.modules

import android.content.Context
import com.example.elormovpmdm.SettingsDataStore
import com.example.elormovpmdm.data.login.LoginApiService
import com.example.elormovpmdm.data.schedule.ScheduleApiService
import com.example.elormovpmdm.data.students.UsersApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val ipHome: String = "192.168.0.12"
        val ipClase: String = "10.5.104.176"
        return Retrofit.Builder()
            .baseUrl("http://$ipClase:8080/")
            .addConverterFactory(
                GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideLoginApiService(retrofit: Retrofit): LoginApiService {
        return retrofit.create(LoginApiService::class.java)
    }
    
    @Provides
    @Singleton
    fun provideStudentsApiService(retrofit: Retrofit): UsersApiService {
        return retrofit.create(UsersApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideScheduleApiService(retrofit: Retrofit): ScheduleApiService {
        return retrofit.create(ScheduleApiService::class.java)
    }
}