package com.gemmacodes.randomusersinc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gemmacodes.randomusersinc.navigation.RandomUserNavHost
import com.gemmacodes.randomusersinc.ui.theme.RandomUsersIncTheme
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

class MainActivity : ComponentActivity() {
    @OptIn(KoinExperimentalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KoinAndroidContext() {
                RandomUsersIncTheme {
                    RandomUserApp()
                }
            }
        }
    }
}


@Composable
fun RandomUserApp() {
    val viewModel: RandomUserViewModel = koinViewModel()
    val navController: NavHostController = rememberNavController()
    RandomUserNavHost(viewModel = viewModel, navController = navController)
}

