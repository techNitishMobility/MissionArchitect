package com.example.missionarchitect.data.repository

import app.cash.turbine.test
import com.example.missionarchitect.data.local.dao.UserDao
import com.example.missionarchitect.data.local.entity.UserEntity
import com.example.missionarchitect.data.remote.ApiService
import com.example.missionarchitect.data.remote.UserResponseDto
import com.example.missionarchitect.domain.util.NetworkResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserRepositoryImplTest {

    private val apiService: ApiService = mockk()
    private val userDao: UserDao = mockk(relaxed = true)

    private lateinit var repository: UserRepositoryImpl
    val entities = listOf(UserEntity(id = 10, name = "Secure User", email = "secure@test.com"))
    val remoteDtos = listOf(UserResponseDto(id = 1, name = "Remote User", email = "remote@api.com",""))
    @Before
    fun setup()
    {
        repository= UserRepositoryImpl(apiService,userDao)
        every { userDao.getUsers() } returns flowOf(entities)
        coEvery { apiService.getUsers() } returns remoteDtos
    }

    @Test
    fun `getUsersStream maps entity list to domain list correctly`() = runTest {
            repository.getUsersStream().test {
                val domainList = awaitItem()
                assertEquals(1, domainList.size)
                assertEquals("Secure User",domainList[0].fullName)
                awaitComplete()
            }
    }

    @Test
    fun `refreshUsers clears old cache and inserts new entities on API success`() = runTest {

        val result = repository.refreshUsers()
        assertTrue(result is NetworkResult.Success)
        coVerify { userDao.clearUsers() }
        coVerify { userDao.insertUsers(any()) }
    }
}