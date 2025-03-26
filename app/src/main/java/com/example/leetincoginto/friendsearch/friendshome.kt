package com.example.leetincoginto.friendsearch

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.leetincoginto.Apisetup.search
import com.example.leetincoginto.homescreen.BottomNavigationBar
import com.example.leetincoginto.viewModel.LeetCodeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendsScreen(navController: NavController, viewModel: LeetCodeViewModel, todo:(search)->Unit ) {

    val friendsList by viewModel.friendsList.collectAsState()
     viewModel.getFriends(viewModel.userId.toString())

    Scaffold(
        topBar = {

                TopAppBar(
                    title = { Text("Friends", color = Color.White) },
                    actions = {
                        IconButton(onClick = { navController.navigate("searchfriend") }) {  // Toggle `show`
                            Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.DarkGray)
                )

        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
           Column(modifier=Modifier.padding(innerPadding)) {
               FriendsScreenOne(friendsList,
                   { viewModel.removeFriend(viewModel.userId.toString(), it) },
                   { viewModel.fectchthreefriend(it)
                   navController.navigate("profilefriend")})
           }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendsScreenOne(friends:List<String>,onDelete:(String)->Unit,todo:(String)->Unit) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()

        ) {
            items(friends) { friend ->
                FriendItem(friend,  onDelete = {onDelete (it)}, { todo(friend) })
            }
        }

}

