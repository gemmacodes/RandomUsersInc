package com.gemmacodes.randomusersinc

import com.gemmacodes.randomusersinc.ui.viewmodels.UserListUIState
import com.gemmacodes.randomusersinc.ui.viewmodels.UserListViewModel
import com.gemmacodes.randomusersinc.usecase.DeleteUser
import com.gemmacodes.randomusersinc.usecase.ListUsers
import com.gemmacodes.randomusersinc.usecase.RequestNewUsers
import com.gemmacodes.randomusersinc.utils.FakeData.fakeUser
import com.gemmacodes.randomusersinc.utils.FakeData.fakeUser2
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class UserListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var userListViewModel: UserListViewModel
    private lateinit var listUsers: ListUsers
    private lateinit var requestNewUsers: RequestNewUsers
    private lateinit var deleteUser: DeleteUser

    @Before
    fun setup() {
        listUsers = mock(ListUsers::class.java)
        requestNewUsers = mock(RequestNewUsers::class.java)
        deleteUser = mock(DeleteUser::class.java)
        userListViewModel = UserListViewModel(listUsers, requestNewUsers, deleteUser)
    }

    @Test
    fun `WHEN onSearchTextChanged THEN updates searchText`() {
        val text = "Test"
        userListViewModel.onSearchTextChanged(text)
        assertEquals(text, userListViewModel.searchText.value)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `WHEN user list with users THEN should set UI state successfully`() = runTest {
        `when`(listUsers.listUsers("")).thenReturn(flowOf(listOf(fakeUser, fakeUser2)))
        `when`(listUsers.listUsers("test")).thenReturn(flowOf(listOf(fakeUser)))

        val uiState = mutableListOf<UserListUIState>()
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            userListViewModel.userListUiState.toList(uiState)
        }

        userListViewModel.onSearchTextChanged("")
        assertEquals(UserListUIState(listOf(fakeUser, fakeUser2)), uiState[1])

        userListViewModel.onSearchTextChanged("test")
        assertEquals(UserListUIState(listOf(fakeUser)), uiState[2])

        collectJob.cancel()
    }


    @Test
    fun `WHEN requestNewUsers THEN invokes requestNewUsers`() = runTest {
        val amount = 1
        userListViewModel.requestNewUsers(amount)
        verify(requestNewUsers).requestNewUsers(amount)
    }

    @Test
    fun `WHEN deleteUser THEN invokes deleteUser`() = runTest {
        val user = fakeUser
        userListViewModel.deleteUser(user)
        verify(deleteUser).deleteUser(user)
    }
}

