package com.gemmacodes.randomusersinc

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavType
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gemmacodes.randomusersinc.data.LocalDataSource
import com.gemmacodes.randomusersinc.data.RemoteDataSource
import com.gemmacodes.randomusersinc.ui.UserDetailDestination
import com.gemmacodes.randomusersinc.ui.UserDetailScreen
import com.gemmacodes.randomusersinc.ui.UserDetailScreenTestTags
import com.gemmacodes.randomusersinc.ui.UserListDestination
import com.gemmacodes.randomusersinc.ui.UserListScreen
import com.gemmacodes.randomusersinc.ui.UserListTestTags
import com.gemmacodes.randomusersinc.ui.viewmodels.UserDetailViewModel
import com.gemmacodes.randomusersinc.ui.viewmodels.UserListViewModel
import com.gemmacodes.randomusersinc.usecase.DeleteUser
import com.gemmacodes.randomusersinc.usecase.ListUsers
import com.gemmacodes.randomusersinc.usecase.RequestNewUsers
import com.gemmacodes.randomusersinc.usecase.RequestUserDetail
import com.gemmacodes.randomusersinc.utils.FakeData.fakeUser
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`


@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()


    private lateinit var navController: TestNavHostController

    private lateinit var userListViewModel: UserListViewModel
    private lateinit var userDetailViewModel: UserDetailViewModel
    private lateinit var localDataSource: LocalDataSource
    private lateinit var remoteDataSource: RemoteDataSource
    private lateinit var savedStateHandle: SavedStateHandle

    @Before
    fun setup() {
        localDataSource = mock(LocalDataSource::class.java)
        remoteDataSource = mock(RemoteDataSource::class.java)
    }


    @OptIn(ExperimentalTestApi::class)
    @Test
    fun userListScreen_ClickOnUserItem_NavigatesToDetailScreen() {
        `when`(remoteDataSource.getUsers(1)).thenReturn(flowOf(listOf(fakeUser)))
        `when`(localDataSource.getUser(fakeUser.uuid)).thenReturn(flowOf(fakeUser))
        `when`(localDataSource.getAllUsers()).thenReturn(flowOf(listOf(fakeUser)))
        `when`(localDataSource.getFilteredUsers(anyString())).thenReturn(flowOf(listOf(fakeUser)))

        savedStateHandle = SavedStateHandle().apply { set("userId", fakeUser.uuid) }
        userListViewModel = UserListViewModel(
            ListUsers(localDataSource),
            RequestNewUsers(remoteDataSource, localDataSource),
            DeleteUser(localDataSource)
        )
        userDetailViewModel = UserDetailViewModel(
            RequestUserDetail(localDataSource),
            savedStateHandle
        )

        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            TestNavHost(navController = navController)
        }

        composeTestRule.waitUntilExactlyOneExists(hasTestTag(UserListTestTags.INFO_ICON))

        composeTestRule.onNodeWithTag(UserListTestTags.INFO_ICON).performClick()

        composeTestRule.waitUntilExactlyOneExists(hasTestTag(UserDetailScreenTestTags.TOP_BAR))

        assertTrue(navController.currentBackStackEntry?.destination?.route == "detail/{userId}")
    }


    @Composable
    fun TestNavHost(
        navController: TestNavHostController,
        modifier: Modifier = Modifier,
    ) {
        androidx.navigation.compose.NavHost(
            navController = navController,
            startDestination = UserListDestination.route,
            modifier = modifier,
        ) {
            composable(
                route = UserListDestination.route,
            ) {
                UserListScreen(
                    navigateToDetail = { userId -> navController.navigate("${UserDetailDestination.route}/{$userId}") },
                    viewModel = userListViewModel,
                )
            }
            composable(
                route = "${UserDetailDestination.route}/{userId}",
                arguments = listOf(navArgument("userId") { type = NavType.StringType })
            ) { backStackEntry ->
                backStackEntry.arguments?.getString("userId")?.let {
                    UserDetailScreen(
                        navigateBack = { navController.navigateUp() },
                        viewModel = userDetailViewModel,
                    )
                }
            }
        }

    }
}