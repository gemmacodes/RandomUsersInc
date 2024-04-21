package com.gemmacodes.randomusersinc.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.gemmacodes.randomusersinc.RandomUserViewModel
import com.gemmacodes.randomusersinc.navigation.NavigationDestination

object RandomUserDetailDestination : NavigationDestination {
    override val route = "detail"
    const val userIdArg = "userId"
    val routeWithArgs = "$route/{$userIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RandomUserDetailScreen(
    navigateBack: () -> Boolean,
    viewModel: RandomUserViewModel,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            RandomUserTopAppBar(
                title = "User details",
                hasBackNavigation = false,
                scrollBehavior = scrollBehavior,
            )
        },
    ) {
        it
    }
}