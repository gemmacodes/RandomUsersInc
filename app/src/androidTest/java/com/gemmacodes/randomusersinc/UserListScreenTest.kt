package com.gemmacodes.randomusersinc

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gemmacodes.randomusersinc.data.LocalDataSource
import com.gemmacodes.randomusersinc.data.RemoteDataSource
import com.gemmacodes.randomusersinc.ui.UserListScreen
import com.gemmacodes.randomusersinc.ui.UserListTestTags
import com.gemmacodes.randomusersinc.ui.viewmodels.UserListViewModel
import com.gemmacodes.randomusersinc.usecase.DeleteUser
import com.gemmacodes.randomusersinc.usecase.ListUsers
import com.gemmacodes.randomusersinc.usecase.RequestNewUsers
import com.gemmacodes.randomusersinc.utils.FakeData.fakeUser
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`


@RunWith(AndroidJUnit4::class)
class UserListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var userListViewModel: UserListViewModel
    private lateinit var localDataSource: LocalDataSource
    private lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setup() {
        localDataSource = mock(LocalDataSource::class.java)
        remoteDataSource = mock(RemoteDataSource::class.java)
        userListViewModel = UserListViewModel(
            ListUsers(localDataSource),
            RequestNewUsers(remoteDataSource, localDataSource),
            DeleteUser(localDataSource)
        )
        /*        composeTestRule.setContent {
                    UserListScreen(navigateToDetail = {}, viewModel = userListViewModel)
                }*/
    }

    @Test
    fun WHEN_emptyUserListScreen_THEN_ContentDisplayed() {
        with(composeTestRule) {
            setContent {
                UserListScreen(navigateToDetail = {})
            }

            onNodeWithTag(UserListTestTags.TOP_BAR).assertIsDisplayed()
            onNodeWithTag(UserListTestTags.SEARCHBAR_CONTAINER).assertIsDisplayed()
            onNodeWithTag(UserListTestTags.EMPTY_TEXT).assertIsDisplayed()
            onNodeWithTag(UserListTestTags.BUTTON).assertIsDisplayed()

            onNodeWithTag(UserListTestTags.CARD).assertDoesNotExist()
            onNodeWithTag(UserListTestTags.PICTURE).assertDoesNotExist()
            onNodeWithTag(UserListTestTags.NAME).assertDoesNotExist()
            onNodeWithTag(UserListTestTags.EMAIL).assertDoesNotExist()
            onNodeWithTag(UserListTestTags.PHONE).assertDoesNotExist()
            onNodeWithTag(UserListTestTags.INFO_ICON).assertDoesNotExist()
            onNodeWithTag(UserListTestTags.DELETE_ICON).assertDoesNotExist()
        }
    }

    @Test
    fun WHEN_userListScreen_THEN_ContentDisplayed() {
        `when`(localDataSource.getAllUsers()).thenReturn(MutableStateFlow(listOf(fakeUser)))

        with(composeTestRule) {
            setContent {
                UserListScreen(navigateToDetail = {}, viewModel = userListViewModel)
            }

            onNodeWithTag(UserListTestTags.TOP_BAR).assertIsDisplayed()
            onNodeWithTag(UserListTestTags.SEARCHBAR_CONTAINER).assertIsDisplayed()
            onNodeWithTag(UserListTestTags.CARD).assertIsDisplayed()
            onNodeWithTag(UserListTestTags.PICTURE).assertIsDisplayed()
            onNodeWithTag(UserListTestTags.NAME).assertIsDisplayed()
            onNodeWithTag(UserListTestTags.EMAIL).assertIsDisplayed()
            onNodeWithTag(UserListTestTags.PHONE).assertIsDisplayed()
            onNodeWithTag(UserListTestTags.INFO_ICON).assertIsDisplayed()
            onNodeWithTag(UserListTestTags.DELETE_ICON).assertIsDisplayed()
            onNodeWithTag(UserListTestTags.BUTTON).assertIsDisplayed()
            onNodeWithTag(UserListTestTags.EMPTY_TEXT).assertDoesNotExist()
        }

    }

}





