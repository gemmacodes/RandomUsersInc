package com.gemmacodes.randomusersinc

import androidx.lifecycle.SavedStateHandle
import com.gemmacodes.randomusersinc.data.LocalDataSource
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
    private lateinit var localDataSource: LocalDataSource


    @Before
    fun setUp() {
        localDataSource = mock(LocalDataSource::class.java)
        requestUserDetail = RequestUserDetail(localDataSource)
    }

    @Test
    fun `GIVEN userId WHEN detail requested THEN userUIState updated`() = runTest {
        `when`(localDataSource.getUser(fakeUser.uuid)).thenReturn(flowOf(fakeUser))

        savedStateHandle = SavedStateHandle().apply { set("userId", fakeUser.uuid) }
        userDetailViewModel = UserDetailViewModel(requestUserDetail, savedStateHandle)

        val uiState = mutableListOf<UserUIState>()
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            userDetailViewModel.userUiState.toList(uiState)
        }

        assertEquals(UserUIState(fakeUser), uiState[1])

        collectJob.cancel()
    }

    @Test(expected = NullPointerException::class)
    fun `GIVEN null userId WHEN detail requested THEN exception thrown`() {
        savedStateHandle = mock(SavedStateHandle::class.java)

        `when`(savedStateHandle.get<String>("userId")).thenReturn(null)
        `when`(requestUserDetail.requestUserDetail(null)).thenThrow(NullPointerException())

        UserDetailViewModel(requestUserDetail, savedStateHandle)
    }
}
