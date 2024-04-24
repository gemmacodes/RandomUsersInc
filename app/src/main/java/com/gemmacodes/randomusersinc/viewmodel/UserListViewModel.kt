package com.gemmacodes.randomusersinc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gemmacodes.randomusersinc.data.api.ApiHelper
import com.gemmacodes.randomusersinc.data.room.DatabaseHelper
import com.gemmacodes.randomusersinc.data.room.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserListViewModel(
    private val apiHelper: ApiHelper,
    private val dbHelper: DatabaseHelper
) : ViewModel() {

    val userListUiState: StateFlow<UserListUIState> =
        dbHelper.getAllUsers().map { UserListUIState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = UserListUIState()
            )


    fun getNewUsersFromApi(amount: Int = 1) {
        viewModelScope.launch {
            apiHelper.getUsers(amount)
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    e.message
                }
                .collect {
                    it.forEach { user -> saveUser(user) }
                }
        }
    }

    private suspend fun saveUser(user: User) = dbHelper.addUser(user)

    fun deleteUser(user: User) {
        viewModelScope.launch {
            dbHelper.deleteUser(user)
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class UserListUIState(val userList: List<User> = listOf())