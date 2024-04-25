package com.gemmacodes.randomusersinc.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gemmacodes.randomusersinc.data.room.User
import com.gemmacodes.randomusersinc.usecase.DeleteUser
import com.gemmacodes.randomusersinc.usecase.ListUsers
import com.gemmacodes.randomusersinc.usecase.RequestNewUsers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserListViewModel(
    private val listUsers: ListUsers,
    private val requestNewUsers: RequestNewUsers,
    private val deleteUser: DeleteUser,
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow()

    fun onSearchTextChanged(text: String) {
        _searchText.value = text
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _userListUiState: StateFlow<UserListUIState> = _searchText.flatMapLatest { text ->
        listUsers.listUsers(text).map { user -> UserListUIState(user) }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = UserListUIState()
    )
    val userListUiState: StateFlow<UserListUIState> = _userListUiState

    fun requestNewUsers(amount: Int = 1) {
        viewModelScope.launch {
            requestNewUsers.requestNewUsers(amount)
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            deleteUser.deleteUser(user)
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class UserListUIState(val userList: List<User> = listOf())