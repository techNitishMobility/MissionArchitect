package com.example.missionarchitect.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.missionarchitect.presentation.home.HomeScreen
import com.example.missionarchitect.presentation.navigation.AppNavHost

import com.example.missionarchitect.presentation.theme.MissionArchitectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MissionArchitectTheme {
                AppNavHost()
            }
        }
    }
}