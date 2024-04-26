package com.gemmacodes.randomusersinc

import androidx.lifecycle.SavedStateHandle
import com.gemmacodes.randomusersinc.ui.viewmodels.UserDetailViewModel
import com.gemmacodes.randomusersinc.ui.viewmodels.UserUIState
import com.gemmacodes.randomusersinc.usecase.RequestUserDetail
import com.gemmacodes.randomusersinc.utils.FakeData.fakeUser
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`


@ExperimentalCoroutinesApi
class UserDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var userDetailViewModel: UserDetailViewModel
    private lateinit var requestUserDetail: RequestUserDetail
    private lateinit var savedStateHandle: SavedStateHandle

    @Before
    fun setUp() {
        requestUserDetail = mock(RequestUserDetail::class.java)
        savedStateHandle = mock(SavedStateHandle::class.java)
        userDetailViewModel = UserDetailViewModel(requestUserDetail, savedStateHandle)
    }

    //TODO: Fix me
    @Test
    fun `GIVEN userId WHEN detail requested THEN userUIState updated`() = runTest {
        `when`(savedStateHandle.get<String>("userId")).thenReturn(fakeUser.uuid)
        `when`(requestUserDetail.requestUserDetail(fakeUser.uuid)).thenReturn(flowOf(fakeUser))


        val uiState = mutableListOf<UserUIState>()
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            userDetailViewModel.userUiState.toList(uiState)
        }

        userDetailViewModel = UserDetailViewModel(requestUserDetail, savedStateHandle)

        assertEquals(UserUIState(fakeUser), uiState[0])

        collectJob.cancel()
    }

    @Test(expected = NullPointerException::class)
    fun `GIVEN null userId WHEN detail requested THEN exception thrown`() {
        `when`(savedStateHandle.get<String>("userId")).thenReturn(null)
        `when`(requestUserDetail.requestUserDetail(null)).thenThrow(NullPointerException())

        UserDetailViewModel(requestUserDetail, savedStateHandle)
    }
}
