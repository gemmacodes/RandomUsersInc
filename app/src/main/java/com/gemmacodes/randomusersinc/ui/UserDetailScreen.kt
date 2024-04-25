package com.gemmacodes.randomusersinc.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.gemmacodes.randomusersinc.data.room.User
import com.gemmacodes.randomusersinc.navigation.NavigationDestination
import com.gemmacodes.randomusersinc.utils.FakeData.fakeUser
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
                modifier = Modifier.testTag(UserDetailScreenTestTags.TOP_BAR),
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
            .testTag(UserDetailScreenTestTags.CARD)
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
                    .testTag(UserDetailScreenTestTags.PICTURE)
                    .padding(10.dp)
                    .size(200.dp)
                    .clip(CircleShape),
            )
            Text(
                modifier = Modifier
                    .testTag(UserDetailScreenTestTags.NAME)
                    .padding(5.dp)
                    .fillMaxWidth(),
                text = "${userInfo.name} ${userInfo.surname}",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier
                    .testTag(UserDetailScreenTestTags.GENDER)
                    .padding(10.dp)
                    .fillMaxWidth(),
                text = "(${userInfo.gender})",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )

            DataRow(
                modifier = Modifier.testTag(UserDetailScreenTestTags.EMAIL),
                info = userInfo.email,
                contentDescription = "e-mail",
                icon = Icons.Outlined.MailOutline,
            )

            DataRow(
                modifier = Modifier.testTag(UserDetailScreenTestTags.REGISTERED_DATE),
                info = userInfo.registeredDate.toDate(),
                contentDescription = "Registration date",
                icon = Icons.Outlined.DateRange
            )

            DataRow(
                modifier = Modifier.testTag(UserDetailScreenTestTags.LOCATION),
                info = "${userInfo.streetName} ${userInfo.streetNumber}, ${userInfo.city}, ${userInfo.state}",
                contentDescription = "Location",
                icon = Icons.Outlined.Place
            )

            DataRow(
                modifier = Modifier.testTag(UserDetailScreenTestTags.PHONE),
                info = userInfo.phone,
                contentDescription = "Phone",
                icon = Icons.Outlined.Phone,
            )
        }
    }
}

@Composable
private fun DataRow(
    icon: ImageVector,
    info: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    Row(
        modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(48.dp, 48.dp)
                .padding(10.dp),
            imageVector = icon,
            contentDescription = contentDescription,
        )
        Column(
            Modifier.padding(end = 10.dp),
        ) {
            Text(text = info)
        }

    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
private fun DetailCardPreview() {
    UserCard(userInfo = fakeUser)
}


object UserDetailScreenTestTags {
    const val TOP_BAR = "DetailList::Header"
    const val CARD = "DetailList::Container"
    const val PICTURE = "DetailList::Picture"
    const val NAME = "DetailList::Name"
    const val EMAIL = "DetailList::Email"
    const val PHONE = "DetailList::Phone"
    const val REGISTERED_DATE = "DetailList::RegisteredDate"
    const val LOCATION = "DetailList::Location"
    const val GENDER = "DetailList::Gender"
}