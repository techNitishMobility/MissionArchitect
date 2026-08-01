package com.example.missionarchitect.domain.repository

import com.example.missionarchitect.domain.model.User
import com.example.missionarchitect.domain.util.NetworkResult

interface UserRepository {
    suspend fun fetchUsers(): NetworkResult<List<User>>
}