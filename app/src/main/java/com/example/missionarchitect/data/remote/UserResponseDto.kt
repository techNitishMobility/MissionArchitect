package com.example.missionarchitect.data.remote

import com.google.gson.annotations.SerializedName

data class UserResponseDto(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("username") val username: String?
)
