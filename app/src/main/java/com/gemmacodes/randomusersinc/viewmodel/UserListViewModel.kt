package com.gemmacodes.randomusersinc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gemmacodes.randomusersinc.data.UserRepository
import com.gemmacodes.randomusersinc.data.room.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserListViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow()

    fun onSearchTextChanged(text: String) {
        _searchText.value = text
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _userListUiState: StateFlow<UserListUIState> = _searchText.flatMapLatest { text ->
        if(text == "") {
            userRepository.getAllUsers().map { user -> UserListUIState(user) }
        } else {
            userRepository.getFilteredUsers(text).map { user -> UserListUIState(user) }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = UserListUIState()
    )
    val userListUiState: StateFlow<UserListUIState> = _userListUiState

    fun getNewUsersFromApi(amount: Int = 1) {
        viewModelScope.launch {
            userRepository.getUsers(amount)
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    e.message
                }
                .collect {
                    it.forEach { user -> saveUser(user) }
                }
        }
    }

    private suspend fun saveUser(user: User) = userRepository.addUser(user)

    fun deleteUser(user: User) {
        viewModelScope.launch {
            userRepository.deleteUser(user)
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class UserListUIState(val userList: List<User> = listOf())