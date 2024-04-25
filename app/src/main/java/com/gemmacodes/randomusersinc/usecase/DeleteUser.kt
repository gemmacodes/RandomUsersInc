package com.gemmacodes.randomusersinc.usecase

import com.gemmacodes.randomusersinc.data.LocalDataSource
import com.gemmacodes.randomusersinc.data.room.DeletedUser
import com.gemmacodes.randomusersinc.data.room.User

class DeleteUser(private val localDataSource: LocalDataSource) {

    suspend fun deleteUser(user: User) {
        localDataSource.deleteUser(user)
        localDataSource.insertDeletedUserId(DeletedUser(user.uuid))
    }
}