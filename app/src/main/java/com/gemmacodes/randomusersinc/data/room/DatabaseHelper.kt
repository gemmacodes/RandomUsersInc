package com.gemmacodes.randomusersinc.data.room

import kotlinx.coroutines.flow.Flow


interface DatabaseHelper {
    suspend fun addUser(user: User)
    fun getUser(id: String): Flow<User>
    fun getAllUsers(): Flow<List<User>>
    suspend fun deleteUser(user: User)
}

class DatabaseHelperImpl(
    private val userDao: UserDao,
) : DatabaseHelper {

    override suspend fun addUser(user: User) = userDao.addUser(user)

    override fun getUser(id: String): Flow<User> = userDao.getUser(id)

    override fun getAllUsers(): Flow<List<User>> = userDao.getAllUsers()

    override suspend fun deleteUser(user: User) = userDao.deleteUser(user)

}
