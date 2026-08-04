package com.example.missionarchitect.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class UserEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val email: String
)
