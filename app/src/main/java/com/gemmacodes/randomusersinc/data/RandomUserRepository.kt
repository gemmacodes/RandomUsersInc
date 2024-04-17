package com.gemmacodes.randomusersinc.data

import com.gemmacodes.randomusersinc.data.room.RandomUserDao
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RandomUserRepository : KoinComponent {
    private val randomUserDao: RandomUserDao by inject()


    fun getRandomUsers(amount: Int): List<RandomUser> {
        return randomUserDao.getRandomUsers(amount)
    }

    fun deleteUserPlant(user: RandomUser) {
        randomUserDao.deleteRandomUser(user)
    }
}