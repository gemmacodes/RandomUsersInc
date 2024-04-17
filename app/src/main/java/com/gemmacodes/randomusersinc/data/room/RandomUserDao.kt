package com.gemmacodes.randomusersinc.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gemmacodes.randomusersinc.data.RandomUser

@Dao
interface RandomUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRandomUser(user: RandomUser): RandomUser

    @Query("SELECT * FROM RandomUser LIMIT :amount")
    fun getRandomUsers(amount: Int): List<RandomUser>

    @Delete
    fun deleteRandomUser(user: RandomUser)
}