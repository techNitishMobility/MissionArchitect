package com.example.missionarchitect.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.missionarchitect.presentation.home.HomeScreen
import com.example.missionarchitect.presentation.home.HomeScreenContent
import com.example.missionarchitect.presentation.home.HomeViewModel
import com.example.missionarchitect.presentation.theme.MissionArchitectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MissionArchitectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // THE MAGIC LINE: Hilt will automatically find, create, and inject the ViewModel!
                    val viewModel: HomeViewModel = hiltViewModel()

                    // 2. Pass the innerPadding to your screen via a Modifier
                    HomeScreen(
                        homeViewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }



    @Preview(showBackground = true, name = "1. Loading State")
    @Composable
    fun PreviewHomeScreenContentLoading() {
        // Wrapping it in your theme ensures it gets the right typography and colors
        MissionArchitectTheme {
            HomeScreenContent(
                stateMessage = "Fetching data..."
            )
        }
    }

    @Preview(showBackground = true, name = "2. Ready State")
    @Composable
    fun PreviewHomeScreenContentReady() {
        MissionArchitectTheme {
            HomeScreenContent(
                stateMessage = "Dashboard Ready"
            )
        }
    }
}