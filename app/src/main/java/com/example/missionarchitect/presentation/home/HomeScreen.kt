package com.example.missionarchitect.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeScreen(homeViewModel: HomeViewModel,modifier: Modifier = Modifier)
{
    // 1. Observe the StateFlow safely, matching the host lifecycle state
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
// 2. Trigger side effects cleanly when the component enters composition
    LaunchedEffect(key1 = Unit) {
        homeViewModel.fetchDashboardData()
    }
    // 3. Render the localized UI content
    HomeScreenContent(stateMessage = uiState,modifier = modifier)

}

@Composable
fun HomeScreenContent(stateMessage: String,modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize().padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = stateMessage)
    }
}