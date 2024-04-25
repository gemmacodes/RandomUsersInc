package com.gemmacodes.randomusersinc.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class, DeletedUser::class], exportSchema = false, version = 12)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun deletedUserDao(): DeletedUserDao
}
