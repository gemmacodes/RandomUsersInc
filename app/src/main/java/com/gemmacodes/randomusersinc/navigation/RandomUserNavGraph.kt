package com.gemmacodes.randomusersinc.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gemmacodes.randomusersinc.ui.RandomUserDetailDestination
import com.gemmacodes.randomusersinc.ui.RandomUserDetailScreen
import com.gemmacodes.randomusersinc.ui.RandomUserListDestination
import com.gemmacodes.randomusersinc.ui.RandomUserListScreen
import com.gemmacodes.randomusersinc.RandomUserViewModel

@Composable
fun RandomUserNavHost(
    navController: NavHostController,
    viewModel: RandomUserViewModel,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = RandomUserListDestination.route,
        modifier = modifier,
    ) {
        composable(route = RandomUserListDestination.route) {
            RandomUserListScreen(
                viewModel = viewModel,
                navigateToUserDetail = { navController.navigate("${RandomUserDetailDestination.route}/${it}") }
            )
        }
        composable(
            route = RandomUserDetailDestination.routeWithArgs,
            arguments = listOf(navArgument(RandomUserDetailDestination.userIdArg) {
                type = NavType.StringType
            })
        ) {
            RandomUserDetailScreen(
                viewModel = viewModel,
                navigateBack = { navController.navigateUp() }
            )
        }
    }
}
