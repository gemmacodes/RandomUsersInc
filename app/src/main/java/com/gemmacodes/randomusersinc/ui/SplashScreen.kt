package com.gemmacodes.randomusersinc.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gemmacodes.randomusersinc.R
import com.gemmacodes.randomusersinc.navigation.NavigationDestination
import kotlinx.coroutines.delay

object SplashScreenDestination : NavigationDestination {
    override val route = "splash"
}

@Composable
fun SplashScreen(
    navigateToList: () -> Unit,
    modifier: Modifier = Modifier,
) {

    LaunchedEffect(Unit) {
        delay(2000)
        navigateToList()
    }

    Column(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.padding(bottom = 20.dp),
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = "App logo - directory",
        )
        Text(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(),
            fontSize = 50.sp,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayMedium,
            text = "Random User\nInc.",
        )
    }
}