package com.gemmacodes.randomusersinc.usecase

import com.gemmacodes.randomusersinc.data.LocalDataSource
import com.gemmacodes.randomusersinc.data.room.User
import kotlinx.coroutines.flow.Flow

class RequestUserDetail(private val localDataSource: LocalDataSource) {

    fun requestUserDetail(id: String): Flow<User> =
        localDataSource.getUser(id)

}