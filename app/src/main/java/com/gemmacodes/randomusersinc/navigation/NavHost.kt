package com.gemmacodes.randomusersinc.navigation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gemmacodes.randomusersinc.ui.SplashScreen
import com.gemmacodes.randomusersinc.ui.SplashScreenDestination
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
        startDestination = SplashScreenDestination.route,
        modifier = modifier,
    ) {
        composable(
            route = SplashScreenDestination.route,
            exitTransition = { fadeOut(animationSpec = tween(300, easing = LinearEasing)) }
        ) {
            SplashScreen(
                navigateToList = { navController.navigate(UserListDestination.route) }
            )
        }
        composable(
            route = UserListDestination.route,
            enterTransition = { fadeIn(animationSpec = tween(300, easing = LinearEasing))
            },
        ) {
            UserListScreen(
                navigateToDetail = { userId -> navController.navigate("detail/$userId") }
            )
        }
        composable(
            route = "${UserDetailDestination.route}/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType }),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                )
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300, easing = LinearEasing))
            }) { backStackEntry ->
            backStackEntry.arguments?.getString("userId")?.let {
                UserDetailScreen(navigateBack = { navController.navigateUp() })
            }
        }
    }

}