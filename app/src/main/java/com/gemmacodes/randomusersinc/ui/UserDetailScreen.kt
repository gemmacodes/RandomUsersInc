package com.gemmacodes.randomusersinc.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.gemmacodes.randomusersinc.R
import com.gemmacodes.randomusersinc.data.room.User
import com.gemmacodes.randomusersinc.navigation.NavigationDestination
import com.gemmacodes.randomusersinc.utils.toDate
import com.gemmacodes.randomusersinc.viewmodel.UserDetailViewModel
import org.koin.androidx.compose.koinViewModel

object UserDetailDestination : NavigationDestination {
    override val route = "detail/{userId}"
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun UserDetailScreen(
    navigateBack: () -> Boolean,
    modifier: Modifier = Modifier,
    viewModel: UserDetailViewModel = koinViewModel(),
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val userUiState by viewModel.userUiState.collectAsState()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            UserTopAppBar(
                title = "User details",
                hasBackNavigation = true,
                scrollBehavior = scrollBehavior,
                navigateUp = { navigateBack() },
            )
        },
    ) { innerPadding ->
        userUiState.user?.let {
            UserCard(
                userInfo = it,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@ExperimentalComposeUiApi
@Composable
fun UserCard(
    userInfo: User,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .padding(20.dp)
            .fillMaxSize(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
    ) {
        Column(
            modifier = modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            GlideImage(
                model = userInfo.pictureLarge,
                contentDescription = "user pic",
                modifier = Modifier
                    .padding(10.dp)
                    .size(200.dp)
                    .clip(CircleShape),
            )
            Text(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                text = "${userInfo.name} ${userInfo.surname}",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )

            DataRow(
                info = userInfo.email,
                image = R.drawable.ic_baseline_email_24
            )

            DataRow(
                info = userInfo.registeredDate.toDate(),
                image = R.drawable.ic_baseline_date_range_24
            )

            DataRow(
                info = "${userInfo.streetName} ${userInfo.streetNumber}, ${userInfo.city}, ${userInfo.state}",
                image = R.drawable.ic_baseline_home_24
            )

            DataRow(
                info = userInfo.phone,
                image = R.drawable.ic_baseline_phone_24,
            )
        }
    }
}

@Composable
private fun DataRow(
    image: Int,
    info: String
) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = "image",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(50.dp)
                .padding(10.dp)

        )
        Column(
            Modifier.padding(end = 10.dp),
        ) {
            Text(text = info)
        }

    }
}
