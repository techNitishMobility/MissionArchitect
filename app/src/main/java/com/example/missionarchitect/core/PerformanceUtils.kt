package com.example.missionarchitect.core

import android.util.Log

/**
 * Utility to measure the execution time of a block of code.
 * Marked as [inline] to prevent object creation overhead during high-frequency tracking.
 */
inline fun <T> measeureExecutionTime(blockName:String,block: ()-> T):T
{
    val startTime= System.currentTimeMillis()
    val result= block()
    val endTime= System.currentTimeMillis()
    Log.d("ArchitectTracker", "$blockName took ${endTime - startTime}ms to execute.")
    return result
}