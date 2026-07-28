package com.example.missionarchitect.data.remote

import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<UserResponseDto>

    @GET("todos/1")
    suspend fun getTodo(): TodoDto
}


