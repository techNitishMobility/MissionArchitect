package com.example.missionarchitect.data.mapper

import android.R.attr.name
import com.example.missionarchitect.data.local.entity.UserEntity
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

// Extension function: Entity -> Domain Model
fun UserEntity.toDomain(): User {
    return User(
        id = id,
        fullName = name,
        contactEmail = email
    )
}

// Extension function: Domain Model -> Entity
fun User.toEntity(): UserEntity {
    return UserEntity(
        id = id,
        name = fullName,
        email = contactEmail
    )
}