package com.example.leetincoginto.profile

import android.content.Intent
import android.net.Uri
import com.example.leetincoginto.Apisetup.LeetCodeUserProfile


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.leetincoginto.homescreen.BottomNavigationBar
import com.google.firebase.auth.FirebaseAuth


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenProfile(navController: NavController, user: LeetCodeUserProfile) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color(0xFF1E1D1D)),
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween, // Ensures title and button are on opposite sides
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "LeetCode Dashboard",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Button(
                            onClick = { FirebaseAuth.getInstance().signOut()
                                navController.navigate("welcome")},
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ) {
                            Text("Logout", color = Color.White)
                        }
                    }
                }
            )
        },
        bottomBar = { BottomNavigationBar(navController) },
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
            LeetCodeProfileCard { navController.navigate("userprofile") }
            LeetCodeContestCard { navController.navigate("usercontest") }
        }
    }
}


@Composable
fun LeetCodeUserProfileScreen(user: LeetCodeUserProfile) {
    val context = LocalContext.current
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2C2C2C)),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(user.name ?: "User", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White,
                    modifier = Modifier.clickable {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://leetcode.com/${user.username}"))
                    context.startActivity(intent)
                })
                Text("Ranking: ${user.ranking}", fontSize = 16.sp, color = Color.Gray)
                user.company?.let { Text("🏢 Company: $it", fontSize = 14.sp, color = Color.LightGray) }
                user.school?.let { Text("🎓 School: $it", fontSize = 14.sp, color = Color.LightGray) }
                user.country?.let { Text("🌍 Country: $it", fontSize = 14.sp, color = Color.LightGray) }
                user.gitHub?.let {
                    Text(
                        "🐙 GitHub: $it",
                        fontSize = 14.sp,
                        color = Color.Blue,
                        modifier = Modifier.clickable {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                            context.startActivity(intent)
                        }
                    )
                }
                user.linkedIN?.let {
                    Text(
                        "🔗 LinkedIn: $it",
                        fontSize = 14.sp,
                        color = Color.Blue,
                        modifier = Modifier.clickable {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                            context.startActivity(intent)
                        }
                    )
                }
                user.twitter?.let {
                    Text(
                        "🐦 Twitter: $it",
                        fontSize = 14.sp,
                        color = Color.Blue,
                        modifier = Modifier.clickable {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                            context.startActivity(intent)
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            user.avatar?.let {
                NetworkImage(it)
            }
        }
        }
    }

@Composable
fun LeetCodeProfileCard(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF303030)),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("📊 LeetCode Profile", style = MaterialTheme.typography.headlineSmall, color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            Text("View your solved problems, submissions, and ranking.", color = Color.LightGray)
        }
    }
}

@Composable
fun LeetCodeContestCard(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF303030)),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("🏆 LeetCode Contests", style = MaterialTheme.typography.headlineSmall, color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            Text("View your contest history and rankings.", color = Color.LightGray)
        }
    }
}

@Composable
fun NetworkImage(url: String) {
    val painter = rememberAsyncImagePainter(url)
    Image(
        painter = painter,
        contentDescription = "Avatar",
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .background(Color(0xFFFFD700))
            .padding(4.dp),
        contentScale = ContentScale.Crop
    )
}
