package com.gemmacodes.randomusersinc.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onTimeout: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val currentOnTimeout by rememberUpdatedState(onTimeout)

    LaunchedEffect(Unit) {
        delay(2000)
        currentOnTimeout()
    }

    Box(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            fontSize = 50.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.ExtraBold,
            text = "Random User Inc.",
        )
    }
}