package com.gemmacodes.randomusersinc.data

import com.gemmacodes.randomusersinc.data.room.DeletedUser
import com.gemmacodes.randomusersinc.data.room.DeletedUserDao
import com.gemmacodes.randomusersinc.data.room.User
import com.gemmacodes.randomusersinc.data.room.UserDao
import com.gemmacodes.randomusersinc.data.room.UserDatabase
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun addUser(user: User)
    fun getUser(id: String): Flow<User>
    fun getAllUsers(): Flow<List<User>>
    fun getFilteredUsers(filter: String): Flow<List<User>>
    suspend fun deleteUser(user: User)
    suspend fun insertDeletedUserId(id: DeletedUser)
    suspend fun findDeletedUserById(id: String): DeletedUser?
}

class LocalUserRepository(
    database: UserDatabase,
) : LocalDataSource {

    private val userDao: UserDao = database.userDao()
    private val deletedUserDao: DeletedUserDao = database.deletedUserDao()

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

