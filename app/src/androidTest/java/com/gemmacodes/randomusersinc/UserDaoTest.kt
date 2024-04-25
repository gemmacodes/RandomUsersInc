package com.gemmacodes.randomusersinc

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gemmacodes.randomusersinc.data.room.DeletedUser
import com.gemmacodes.randomusersinc.data.room.DeletedUserDao
import com.gemmacodes.randomusersinc.data.room.User
import com.gemmacodes.randomusersinc.data.room.UserDao
import com.gemmacodes.randomusersinc.data.room.UserDatabase
import com.gemmacodes.randomusersinc.utils.FakeData.fakeUser
import com.gemmacodes.randomusersinc.utils.FakeData.fakeUser2
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class UserDaoTest {

    private lateinit var userDao: UserDao
    private lateinit var deletedUserDao: DeletedUserDao
    private lateinit var userDatabase: UserDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        userDatabase = Room.inMemoryDatabaseBuilder(context, UserDatabase::class.java)
            .allowMainThreadQueries() //just for testing
            .build()
        userDao = userDatabase.userDao()
        deletedUserDao = userDatabase.deletedUserDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        userDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun WHEN_addUser_THEN_insertsUserIntoDB() = runBlocking {
        addOneUserToDb(fakeUser)
        val allItems = getUsersFromDb().last()
        assertEquals(allItems, listOf(fakeUser))
    }

    @Test
    @Throws(Exception::class)
    fun WHEN_addTwoUsers_THEN_insertsUsersIntoDB() = runBlocking {
        addOneUserToDb(fakeUser)
        addOneUserToDb(fakeUser2)

        val allItems = getUsersFromDb().last()

        assertEquals(allItems, listOf(fakeUser, fakeUser2))
    }

    @Test
    @Throws(Exception::class)
    fun WHEN_deleteUser_THEN_deletesUserFromDB() = runBlocking {
        addOneUserToDb(fakeUser)
        deleteUserToDb(fakeUser)

        val allItems = getUsersFromDb().last()
        val deletedUser = deletedUserDao.findDeletedUserById(fakeUser.uuid)

        assertEquals(allItems, emptyList<User>())
        assertEquals(deletedUser, DeletedUser(fakeUser.uuid))
    }

    @Test
    @Throws(Exception::class)
    fun GIVEN_deletedUser_WHEN_addingSameUser_THEN_UserNotAddedToDB() = runBlocking {
        addOneUserToDb(fakeUser)
        deleteUserToDb(fakeUser)
        addOneUserToDb(fakeUser)

        val allItems = getUsersFromDb().last()
        val deletedUser = findDeletedUser(fakeUser.uuid)

        assertEquals(allItems, emptyList<User>())
        assertEquals(deletedUser, DeletedUser(fakeUser.uuid))
    }

    @Test
    @Throws(Exception::class)
    fun GIVEN_user_WHEN_sameUserAdded_THEN_UserNotAddedTwiceToDB() = runBlocking {
        addOneUserToDb(fakeUser)
        addOneUserToDb(fakeUser)

        val allItems = getUsersFromDb().last()

        assertEquals(allItems, listOf(fakeUser))
    }

    private suspend fun addOneUserToDb(user:User) = userDao.addUser(user)
    private suspend fun deleteUserToDb(user:User) {
        userDao.deleteUser(user)
        deletedUserDao.insertDeletedUserId(DeletedUser(user.uuid))
    }
    private suspend fun findDeletedUser(id:String) = deletedUserDao.findDeletedUserById(id)
    private fun getUsersFromDb() = userDao.getAllUsers()

}