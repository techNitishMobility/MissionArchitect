package com.example.missionarchitect.data.repository

import com.example.missionarchitect.data.mapper.toDomain
import com.example.missionarchitect.data.remote.ApiService
import com.example.missionarchitect.domain.model.User
import com.example.missionarchitect.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : UserRepository {
    override suspend fun fetchUsers(): List<User> {
        val dtos = apiService.getUsers()
        return dtos.map { it.toDomain() }
    }
}