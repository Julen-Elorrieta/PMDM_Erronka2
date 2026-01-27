package com.example.elormovpmdm.data.network

import com.example.elormovpmdm.data.login.LoginApiService
import com.example.elormovpmdm.data.students.UsersApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
        return Retrofit.Builder()
            .baseUrl("http://10.5.104.176:8080/")
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
}