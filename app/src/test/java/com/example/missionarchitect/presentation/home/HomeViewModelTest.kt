package com.example.missionarchitect.presentation.home

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    // 1. Create a Test Dispatcher to replace the Android Main Thread
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        // Force the ViewModel to use our Test Dispatcher instead of the real Android Main thread
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        // Clean up after the test
        Dispatchers.resetMain()
    }

    @Test
    fun `when fetchDashboardData is called, state updates correctly`() = runTest {
        // Arrange
        val viewModel = HomeViewModel()

        // Assert Initial State
        assertEquals("Initializing Dashboard...", viewModel.uiState.value)

        // Act
        viewModel.fetchDashboardData()

        // Fast-forward the virtual time to skip the 2000ms delay instantly!
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert Final State
        assertEquals("Dashboard Ready", viewModel.uiState.value)
    }
}