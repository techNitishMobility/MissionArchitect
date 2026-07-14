package com.example.missionarchitect.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    // 1. Mutable State: Private so the UI layer cannot modify it directly.
    private val _uiState=MutableStateFlow("Initializing Dashboard...")
    // 2. Immutable State: Public for the Compose UI to observe.
    val uiState : StateFlow<String> = _uiState.asStateFlow()

    fun fetchDashboardData() {
        // Launching a Coroutine tied to the ViewModel's lifecycle
        viewModelScope.launch {
            _uiState.value = "Fetching data..."

            // Simulating a network call (Suspension point)
            delay(2000L)

            _uiState.value = "Dashboard Ready"
        }
    }
}