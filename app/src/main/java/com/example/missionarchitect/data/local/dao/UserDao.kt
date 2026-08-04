package com.example.missionarchitect.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.missionarchitect.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface UserDao {
    @Query("SELECT * FROM Users")
    fun getUsers(): Flow<List<UserEntity>>

    @Query("SELECT * FROM Users WHERE id = :id")
    suspend fun getUserById(id: Int): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users:List<UserEntity>)

    @Query("DELETE FROM Users")
    suspend fun clearUsers()
}