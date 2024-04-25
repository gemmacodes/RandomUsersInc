package com.gemmacodes.randomusersinc.usecase

import android.util.Log
import com.gemmacodes.randomusersinc.data.LocalDataSource
import com.gemmacodes.randomusersinc.data.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

class RequestNewUsers(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) {

    suspend fun requestNewUsers(amount: Int) {
        remoteDataSource.getUsers(amount).flowOn(Dispatchers.IO)
            .catch { e ->
                Log.e("getNewUsersFromApi", "Error: ${e.message}", e)
            }
            .collect {
                it.forEach { user ->
                    if (localDataSource.findDeletedUserById(user.uuid) == null) {
                        localDataSource.addUser(user)
                    }
                }
            }
    }

}