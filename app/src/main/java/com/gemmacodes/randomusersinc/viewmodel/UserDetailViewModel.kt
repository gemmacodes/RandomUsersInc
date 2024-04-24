package com.gemmacodes.randomusersinc.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gemmacodes.randomusersinc.data.UserRepository
import com.gemmacodes.randomusersinc.data.room.User
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class UserDetailViewModel(
    userRepository: UserRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val userId = savedStateHandle.get<String>("userId")

    val userUiState: StateFlow<UserUIState> =
        userRepository.getUser(userId!!).map { UserUIState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = UserUIState(null),
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class UserUIState(val user: User?)