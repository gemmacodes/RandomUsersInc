package com.gemmacodes.randomusersinc.data

import android.util.Log
import com.gemmacodes.randomusersinc.data.api.RandomUserRetrofit
import com.gemmacodes.randomusersinc.data.room.DeletedUser
import com.gemmacodes.randomusersinc.data.room.DeletedUserDao
import com.gemmacodes.randomusersinc.data.room.User
import com.gemmacodes.randomusersinc.data.room.UserDao
import com.gemmacodes.randomusersinc.data.room.UserDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepository(
    retrofit: RandomUserRetrofit,
    database: UserDatabase,
) : LocalDataSource, RemoteDataSource {

    private val userDao: UserDao = database.userDao()
    private val deletedUserDao: DeletedUserDao = database.deletedUserDao()
    private val apiService = retrofit.service

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

    override suspend fun addUser(user: User) = userDao.addUser(user)

    override fun getUser(id: String): Flow<User> = userDao.getUser(id)

    override fun getAllUsers(): Flow<List<User>> = userDao.getAllUsers()

    override fun getFilteredUsers(filter: String): Flow<List<User>> =
        userDao.getFilteredUsers(filter)

    override suspend fun deleteUser(user: User) = userDao.deleteUser(user)

    override suspend fun insertDeletedUserId(id: DeletedUser) =
        deletedUserDao.insertDeletedUserId(id)

    override suspend fun findDeletedUserById(id: String): DeletedUser? =
        deletedUserDao.findDeletedUserById(id)

}

interface RemoteDataSource {
    fun getUsers(amount: Int): Flow<List<User>>
}

interface LocalDataSource {
    suspend fun addUser(user: User)
    fun getUser(id: String): Flow<User>
    fun getAllUsers(): Flow<List<User>>
    fun getFilteredUsers(filter: String): Flow<List<User>>
    suspend fun deleteUser(user: User)
    suspend fun insertDeletedUserId(id: DeletedUser)
    suspend fun findDeletedUserById(id: String): DeletedUser?
}