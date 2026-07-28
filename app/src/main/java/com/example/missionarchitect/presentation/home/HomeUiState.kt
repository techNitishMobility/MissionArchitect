package com.example.missionarchitect.presentation.home

import com.example.missionarchitect.domain.model.User

data class HomeUiState(
    val isLoading: Boolean = false,
    val users: List<User> = emptyList(),
    val error: String? = null
)
