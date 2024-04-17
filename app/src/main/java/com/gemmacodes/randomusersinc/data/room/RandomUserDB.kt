package com.gemmacodes.randomusersinc.data.room


import androidx.room.Database
import androidx.room.RoomDatabase
import com.gemmacodes.randomusersinc.data.RandomUser


@Database(entities = [RandomUser::class], version = 1)
abstract class RandomUserDB: RoomDatabase(){
    abstract fun randomUsersDao(): RandomUserDao
}
