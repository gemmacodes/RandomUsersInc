package com.gemmacodes.randomusersinc.data.room


import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [RandomUser::class], version = 1)
abstract class RandomUserDatabase: RoomDatabase(){
    abstract fun randomUserDao(): RandomUserDao
}
