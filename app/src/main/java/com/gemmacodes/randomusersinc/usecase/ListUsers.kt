package com.gemmacodes.randomusersinc.usecase

import com.gemmacodes.randomusersinc.data.LocalDataSource
import com.gemmacodes.randomusersinc.data.room.User
import kotlinx.coroutines.flow.Flow

class ListUsers(private val localDataSource: LocalDataSource) {

    fun listUsers(filter: String): Flow<List<User>> =
        if (filter == "") {
            localDataSource.getAllUsers()
        } else {
            localDataSource.getFilteredUsers(filter)
        }
}