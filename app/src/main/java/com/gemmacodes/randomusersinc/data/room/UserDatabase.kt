package com.gemmacodes.randomusersinc.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 11)
abstract class UserDatabase: RoomDatabase(){
    abstract fun randomUserDao(): UserDao
}
