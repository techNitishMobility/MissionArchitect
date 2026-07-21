package com.example.missionarchitect.data.remote

import retrofit2.http.GET

interface ApiService {
    @GET("user/profile")
    suspend fun getUsers(): List<UserResponseDto>
}