package com.example.leetincoginto.homescreen

import LoadingScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home

import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.leetincoginto.Apisetup.ApiResponse
import com.example.leetincoginto.Apisetup.ContestResponse
import com.example.leetincoginto.Apisetup.LeetCodeProblem
import com.example.leetincoginto.Apisetup.search
import com.example.leetincoginto.aiscreen.aichat.ChatScreen
import com.example.leetincoginto.friendsearch.SearchScaffold
import com.example.leetincoginto.viewModel.LeetCodeViewModel

@Composable
fun ProfileScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Profile Screen", fontSize = 24.sp)
    }
}
@Composable
fun AIScreen(viewModel: LeetCodeViewModel) {
    val chat by viewModel.isLoading.collectAsState()
    val messages by viewModel.messages.collectAsState()
    if(messages.size==0){
        LoadingScreen("Starting")
    }else{
        ChatScreen(messages,{viewModel.generateStory(it)},chat)
    }




   
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, upcomingContests: ContestResponse,leetCodeProblem:LeetCodeProblem ) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "LeetCode Incognito",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black, // Dark theme for the app bar
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
        ) {
//             Display LeetCode Problem Card
            item {
                LeetCodeProblemCard(leetCodeProblem)
            }

            // Add Upcoming Contests Text
            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Upcoming Contests",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp)) // Adds space before the divider
                    Divider(color = Color.Gray, thickness = 1.dp) // Divider below the text
                }
            }

            // Use LazyColumn to render Contest Cards efficiently
            item {
                ContestCard(upcomingContests.objects!!) // Passing listOf(contest) to match function signature
            }
        }
    }
}



sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Home : BottomNavItem("home", Icons.Filled.Home, "Home")
    object Friends : BottomNavItem("friends", Icons.Default.Search, "Friends")
    object AI : BottomNavItem("ai", Icons.Filled.Star, "AI") // Added AI Icon
    object Profile : BottomNavItem("profile", Icons.Filled.Person, "Profile")
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Friends,
        BottomNavItem.AI,  // New AI Tab
        BottomNavItem.Profile
    )

    NavigationBar(containerColor = Color.Black) { // Dark Mode Navigation Bar
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

        items.forEach { item ->
            val isSelected = currentRoute == item.route
            NavigationBarItem(
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = item.label,
                        tint = if (isSelected) Color.White else Color.Gray // Highlight active icon
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        color = if (isSelected) Color.White else Color.Gray // Highlight active label
                    )
                },
                selected = isSelected,
                onClick = {
                    if(isSelected==false){
                    navController.navigate(item.route) {
                        launchSingleTop = true // Prevents duplicate entries
                        restoreState = true // Restores the previous state instead of recomposing
                    }}
                }
            )
        }
    }
}

