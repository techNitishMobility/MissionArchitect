package com.example.missionarchitect.data.mapper

import com.example.missionarchitect.data.remote.TodoDto
import com.example.missionarchitect.data.remote.UserResponseDto
import com.example.missionarchitect.domain.model.User

fun UserResponseDto.toDomain(): User
{
    return User(
        id = this.id ?: 0,
        fullName = this.name ?: "Unknown Name",
        contactEmail = this.email ?: "No Email"
    )
}

fun TodoDto.toDomain(): User
{
    return User(
        id=this.id,
        fullName = title
    )
}