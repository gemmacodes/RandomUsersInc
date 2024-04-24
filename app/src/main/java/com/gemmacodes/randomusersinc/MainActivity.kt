package com.gemmacodes.randomusersinc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gemmacodes.randomusersinc.navigation.NavHost
import com.gemmacodes.randomusersinc.ui.SplashScreen
import com.gemmacodes.randomusersinc.ui.theme.RandomUsersIncTheme
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.core.annotation.KoinExperimentalAPI

class MainActivity : ComponentActivity() {
    @OptIn(KoinExperimentalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KoinAndroidContext() {
                RandomUsersIncTheme {
                    var showsSplashScreen by remember { mutableStateOf(true) }
                    AnimatedVisibility(
                        visible = showsSplashScreen,
                        enter = EnterTransition.None,
                        exit = fadeOut()
                    ) {
                        SplashScreen(onTimeout = { showsSplashScreen = false })
                    }
                    AnimatedVisibility(
                        visible = !showsSplashScreen,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        RandomUserApp()
                    }
                }
            }
        }
    }
}

@Composable
fun RandomUserApp() {
    val navController: NavHostController = rememberNavController()
    NavHost(navController = navController)
}

