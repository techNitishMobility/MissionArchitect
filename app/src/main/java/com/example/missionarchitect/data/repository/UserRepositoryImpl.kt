package com.example.missionarchitect.data.repository


import com.example.missionarchitect.data.mapper.toDomain
import com.example.missionarchitect.data.remote.ApiService
import com.example.missionarchitect.domain.model.User
import com.example.missionarchitect.domain.repository.UserRepository
import com.example.missionarchitect.domain.util.NetworkResult
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : UserRepository {
    override suspend fun fetchUsers(): NetworkResult<List<User>> {
        return try {
            val dtos = apiService.getUsers()
            NetworkResult.Success(dtos.map { it.toDomain() })
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "An unknown error occurred")
        }
    }
}