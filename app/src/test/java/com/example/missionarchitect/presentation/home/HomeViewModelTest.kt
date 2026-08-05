package com.example.missionarchitect.presentation.home

import app.cash.turbine.test
import com.example.missionarchitect.domain.model.User
import com.example.missionarchitect.domain.repository.UserRepository
import com.example.missionarchitect.domain.util.NetworkResult
import com.example.missionarchitect.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule= MainDispatcherRule()


    // Faked Dependency
    val userRepository: UserRepository = mockk(relaxed = true)

    // System Under Test (SUT)
    private lateinit var viewModel: HomeViewModel

    private val testUsers = listOf(
        User(id = 1, fullName =  "Alex Architect", contactEmail = "alex@december.com"),
        User(id = 2, fullName = "Dev Developer", contactEmail = "dev@december.com")
    )


    @Before
    fun setup() {
        // Mock Room stream return
        every { userRepository.getUsersStream() } returns flowOf(testUsers)
        coEvery { userRepository.refreshUsers() } returns NetworkResult.Success(Unit)
    }

    @After
    fun tearDown() {
        // Clean up after the test
        Dispatchers.resetMain()
    }

    @Test
    fun `init streams cached users from repository successfully`() = runTest {
        // Arrange
        val viewModel = HomeViewModel(userRepository)
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(testUsers, state.users)
            assertFalse(`state`.isLoading)
            assertNull(state.error)
        }
    }

    @Test
    fun `refreshUsers failure sets error state without clearing cached users`() = runTest {
        val errorMessage = "Network timeout"
        coEvery { userRepository.refreshUsers() } returns NetworkResult.Error(message = errorMessage)
        val viewModel= HomeViewModel(userRepository)
        viewModel.retry()
        viewModel.uiState.test {
            val state=awaitItem()
            assertEquals(testUsers, state.users)
            assertFalse(`state`.isLoading)
            assertEquals(errorMessage, state.error)

        }
        // Verify repository refresh was called
        coVerify(atLeast = 1) { userRepository.refreshUsers() }
    }
}