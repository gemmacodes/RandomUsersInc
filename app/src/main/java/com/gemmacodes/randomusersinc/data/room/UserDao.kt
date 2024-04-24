package com.gemmacodes.randomusersinc.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: User)

    @Query("SELECT * from random_users WHERE uuid = :id")
    fun getUser(id: String): Flow<User>

    @Query("SELECT * from random_users ORDER BY name ASC")
    fun getAllUsers(): Flow<List<User>>

    @Delete
    suspend fun deleteUser(user: User)
}