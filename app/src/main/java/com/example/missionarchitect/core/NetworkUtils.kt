package com.example.missionarchitect.core

import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

suspend fun fetchDashBoard():String
{
    println("Fetching User ID...")
    delay(1000L.milliseconds) // Suspension Point 1

    println("Fetching Profile...")
    delay(1000L.milliseconds) // Suspension Point 2

    return "Dashboard Ready"
}