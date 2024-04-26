package com.gemmacodes.randomusersinc.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gemmacodes.randomusersinc.ui.UserDetailDestination
import com.gemmacodes.randomusersinc.ui.UserDetailScreen
import com.gemmacodes.randomusersinc.ui.UserListDestination
import com.gemmacodes.randomusersinc.ui.UserListScreen

interface NavigationDestination {
    val route: String
}

@Composable
fun NavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = UserListDestination.route,
        modifier = modifier,
    ) {
        composable(
            route = UserListDestination.route,
        ) {
            UserListScreen(
                navigateToDetail = { userId -> navController.navigate("detail/$userId") }
            )
        }
        composable(
            route = "${UserDetailDestination.route}/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("userId")?.let {
                UserDetailScreen(navigateBack = { navController.navigateUp() })
            }
        }
    }

}
