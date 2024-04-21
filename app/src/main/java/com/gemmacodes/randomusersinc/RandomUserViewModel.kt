package com.gemmacodes.randomusersinc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gemmacodes.randomusersinc.data.RandomUserRepository
import com.gemmacodes.randomusersinc.data.room.RandomUser
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class RandomUserViewModel(randomUserRepository: RandomUserRepository) : ViewModel() {

    val randomUserListUiState: StateFlow<RandomUserListState> =
        randomUserRepository.getRandomUserListStream().map { RandomUserListState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = RandomUserListState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class RandomUserListState(val userList: List<RandomUser> = listOf())