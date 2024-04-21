package com.gemmacodes.randomusersinc.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gemmacodes.randomusersinc.R
import com.gemmacodes.randomusersinc.RandomUserListState
import com.gemmacodes.randomusersinc.RandomUserViewModel
import com.gemmacodes.randomusersinc.data.room.RandomUser
import com.gemmacodes.randomusersinc.navigation.NavigationDestination

object RandomUserListDestination : NavigationDestination {
    override val route = "list"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RandomUserListScreen(
    navigateToUserDetail: (String) -> Unit,
    viewModel: RandomUserViewModel,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val state by viewModel.randomUserListUiState.collectAsState()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            RandomUserTopAppBar(
                title = "User directory",
                hasBackNavigation = false,
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        RandomUserList(
            state = state,
            navigateToUserDetail = navigateToUserDetail,
            contentPadding = innerPadding,
        )
    }
}

@Composable
private fun RandomUserList(
    state: RandomUserListState,
    navigateToUserDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    if (state.userList.isEmpty()) {
        Text(
            text = "Oops! No users available",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(contentPadding),
        )
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            items(state.userList) { user ->
                RandomUserCard(
                    modifier = Modifier
                        .padding(8.dp),
                    user = user,
                    navigateToUserDetail = navigateToUserDetail
                )
            }
        }
    }
}


@Composable
private fun RandomUserCard(
    user: RandomUser,
    navigateToUserDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
        modifier = modifier,
        //.padding(horizontal = 16.dp, vertical = 4.dp)
        //.semantics { selected = isSelected }
        //.clickable { navigateToDetail(email.id) },
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier
                    .size(75.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "content",
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${user.name} ${user.surname}",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = user.email,
                )
                Text(
                    text = user.phone,
                )
            }
            Column {
                IconButton(
                    onClick = { /*Click Implementation*/ },
                    modifier = Modifier
                        .clip(CircleShape)

                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "fav",
                    )
                }
                IconButton(
                    onClick = { navigateToUserDetail(user.id) },
                    modifier = Modifier
                        .clip(CircleShape)

                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "fav",
                    )
                }
            }
        }
    }
}
