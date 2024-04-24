package com.gemmacodes.randomusersinc.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.gemmacodes.randomusersinc.data.room.User
import com.gemmacodes.randomusersinc.navigation.NavigationDestination
import com.gemmacodes.randomusersinc.viewmodel.UserListUIState
import com.gemmacodes.randomusersinc.viewmodel.UserListViewModel
import org.koin.androidx.compose.koinViewModel


object UserListDestination : NavigationDestination {
    override val route = "list"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
    navigation: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: UserListViewModel = koinViewModel(),
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            UserTopAppBar(
                title = "User directory",
                hasBackNavigation = false,
                scrollBehavior = scrollBehavior,
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
        ) {
            SearchBar(viewModel = viewModel)
            Directory(
                viewModel = viewModel,
                navController = navigation,
            )
        }

    }
}

@Composable
private fun Directory(
    navController: NavHostController,
    viewModel: UserListViewModel,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.userListUiState.collectAsStateWithLifecycle()

    if (state.userList.isEmpty()) {
        EmptyList(
            modifier = modifier
                .padding(20.dp)
                .fillMaxSize(),
            viewModel = viewModel,
        )
    } else {
        UserList(
            modifier = modifier
                .padding(20.dp)
                .fillMaxSize(),
            state = state,
            viewModel = viewModel,
            navController = navController,
        )
    }
}

@Composable
private fun UserList(
    modifier: Modifier,
    state: UserListUIState,
    navController: NavHostController,
    viewModel: UserListViewModel,
) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(state.userList) { user ->
            RandomUserCard(
                modifier = Modifier
                    .padding(vertical = 8.dp),
                user = user,
                navigateToUserDetail = {
                    navController.navigate("detail/${user.uuid}")
                },
                onDelete = { viewModel.deleteUser(user) })
        }
        item {
            LoadingButton(
                text = "LOAD MORE USERS",
                viewModel = viewModel,
            )
        }
    }
}

@Composable
private fun EmptyList(
    modifier: Modifier,
    viewModel: UserListViewModel,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Oops! No users available",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 10.dp),
        )
        LoadingButton(
            text = "LOAD USERS",
            viewModel = viewModel,
        )
    }
}

@Composable
fun SearchBar(
    viewModel: UserListViewModel,
    modifier: Modifier = Modifier,
) {
    val searchText by viewModel.searchText.collectAsState("")

    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.inversePrimary
    ) {
        Row(Modifier.padding(all = 15.dp)) {
            Icon(
                modifier = Modifier.size(24.dp, 24.dp),
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search"
            )
            Spacer(Modifier.width(8.dp))
            Box(
                Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                BasicTextField(
                    value = searchText,
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyLarge,
                    onValueChange = { viewModel.onSearchTextChanged(it) },
                    cursorBrush = SolidColor(LocalContentColor.current),
                    decorationBox = { innerTextField ->
                        Box {
                            innerTextField()
                            if (searchText.isEmpty()) {
                                Text("Search by name, surname or email")
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun LoadingButton(
    text: String,
    viewModel: UserListViewModel,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = { viewModel.getNewUsersFromApi() },
        shape = CutCornerShape(topStart = 8.dp, bottomEnd = 8.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp),
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(text)
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun RandomUserCard(
    user: User,
    navigateToUserDetail: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 20.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${user.name} ${user.surname}",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(text = user.email)
                Text(text = user.phone)
                ActionRow(
                    navigateToUserDetail = navigateToUserDetail,
                    onDelete = onDelete,
                )
            }
            GlideImage(
                model = user.pictureMedium,
                contentDescription = "user pic",
                modifier = Modifier
                    .size(75.dp)
                    .clip(CircleShape),
            )
        }
    }
}

@Composable
private fun ActionRow(navigateToUserDetail: () -> Unit, onDelete: () -> Unit) {
    Row(
        modifier = Modifier.padding(top = 10.dp),
    ) {
        IconButton(
            onClick = { navigateToUserDetail() },
            modifier = Modifier
                .clip(CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "info",
            )
        }
        IconButton(
            onClick = { onDelete() },
            modifier = Modifier
                .clip(CircleShape)

        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "delete",
            )
        }
    }
}
