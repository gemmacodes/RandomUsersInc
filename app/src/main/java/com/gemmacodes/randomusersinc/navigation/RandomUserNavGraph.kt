package com.gemmacodes.randomusersinc.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gemmacodes.randomusersinc.RandomUserDetailDestination
import com.gemmacodes.randomusersinc.RandomUserDetailScreen
import com.gemmacodes.randomusersinc.RandomUserListDestination
import com.gemmacodes.randomusersinc.RandomUserListScreen

@Composable
fun RandomUserNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = RandomUserListDestination.route,
        modifier = modifier
    ) {
        composable(route = RandomUserListDestination.route) {
            RandomUserListScreen(
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
                navigateBack = { navController.navigateUp() }
            )
        }
    }
}
