package com.example.leetincoginto.friendsearch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.leetincoginto.Apisetup.LeetCodeUserProfile
import com.example.leetincoginto.homescreen.HomeScreen
import com.example.leetincoginto.profile.HomeScreenProfile
import com.example.leetincoginto.profile.LeetCodeContestCard
import com.example.leetincoginto.profile.LeetCodeProfileCard
import com.example.leetincoginto.profile.LeetCodeUserProfileScreen
import com.example.leetincoginto.viewModel.LeetCodeViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)

fun HomeScreenProfilefriend(navController: NavController, user: LeetCodeUserProfile) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("LeetCode Dashboard", color = Color.White, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color(0xFF1E1E1E))
            )
        },
        containerColor = Color.Black
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            user?.let { LeetCodeUserProfileScreen(it) }
            LeetCodeProfileCard { navController.navigate("userprofilefriend") }
            LeetCodeContestCard { navController.navigate("usercontestfriend") }
        }
    }
}