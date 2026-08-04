package com.example.missionarchitect.data.repository


import com.example.missionarchitect.data.local.dao.UserDao
import com.example.missionarchitect.data.mapper.toDomain
import com.example.missionarchitect.data.mapper.toEntity
import com.example.missionarchitect.data.remote.ApiService
import com.example.missionarchitect.domain.model.User
import com.example.missionarchitect.domain.repository.UserRepository
import com.example.missionarchitect.domain.util.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
) : UserRepository {

    // 1. UI observes Room database Flow (Single Source of Truth)
    override fun getUsersStream(): Flow<List<User>> {
        return userDao.getUsers().map { entities ->
            entities.map { it.toDomain() }
        }
    }
    // 2. Fetch fresh remote data and securely overwrite Room cache
    override suspend fun refreshUsers(): NetworkResult<Unit> {
        return try {
            val remoteDtos=apiService.getUsers().map { it.toDomain() }
            val entities = remoteDtos.map { it.toEntity() }
            userDao.clearUsers()
            userDao.insertUsers(entities)
            NetworkResult.Success(Unit)
        }catch (e:Exception)
        {
            NetworkResult.Error("Offline mode: ${e.localizedMessage ?: "Failed to reach remote server"}")
        }

    }
}