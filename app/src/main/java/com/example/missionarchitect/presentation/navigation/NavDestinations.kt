package com.example.missionarchitect.presentation.navigation

import kotlinx.serialization.Serializable

// 1. Home Screen Route (No arguments)
@Serializable
object HomeRoute

// 2. User Detail Screen Route (Takes userId and userName as arguments)
@Serializable
data class DetailRoute(
    val userId: Int,
    val userName: String
)