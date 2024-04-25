package com.gemmacodes.randomusersinc.data

import com.gemmacodes.randomusersinc.data.api.RandomUserRetrofit
import com.gemmacodes.randomusersinc.data.room.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

interface RemoteDataSource {
    fun getUsers(amount: Int): Flow<List<User>>
}

class RemoteUserRepository(
    retrofit: RandomUserRetrofit,
) : RemoteDataSource {

    private val apiService = retrofit.service

    override fun getUsers(amount: Int): Flow<List<User>> = flow {
        val response = apiService.getRandomUsers(
            "picture,name,phone,email,gender,location,registered,id",
            amount,
        )
        if (response.isSuccessful) {
            response.body()!!.results.map { user -> user.toUser() }.let { emit(it) }
        } else {
            throw HttpException(response)
        }
    }

}


