package com.gemmacodes.randomusersinc.data.api

import android.util.Log
import com.gemmacodes.randomusersinc.data.api.RandomUserRetrofit.RandomUserService
import com.gemmacodes.randomusersinc.data.room.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface ApiHelper {
    fun getUsers(amount: Int): Flow<List<User>>
}

class ApiHelperImpl(
    private val apiService: RandomUserService,
) : ApiHelper {

    override fun getUsers(amount: Int): Flow<List<User>> = flow {
        val response = apiService.getRandomUsers(
            "picture,name,phone,email,gender,location,registered,id",
            amount,
        )
        if (response.isSuccessful) {
            response.body()!!.results.map { user -> user.toUser() }.let { emit(it) }
        } else {
            Log.e("HTTP Error Tag", "${response.errorBody()}")
        }
    }
}
