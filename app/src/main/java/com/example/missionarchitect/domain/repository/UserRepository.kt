package com.example.missionarchitect.domain.repository

import com.example.missionarchitect.domain.model.User

interface UserRepository {
    suspend fun fetchUsers(): List<User>
}