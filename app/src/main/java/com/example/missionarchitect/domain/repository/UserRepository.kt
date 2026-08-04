package com.example.missionarchitect.domain.repository

import com.example.missionarchitect.domain.model.User
import com.example.missionarchitect.domain.util.NetworkResult
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsersStream(): Flow<List<User>>

    suspend fun refreshUsers(): NetworkResult<Unit>
}