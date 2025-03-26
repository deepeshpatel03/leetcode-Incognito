package com.example.leetincoginto.profile

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.leetincoginto.Apisetup.ContestParticipation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import com.example.leetincoginto.Apisetup.ContestData
import java.util.concurrent.TimeUnit


@Composable
fun ContestCard(contest: ContestParticipation) {
    val adjustedRanking = if (contest.ranking % 25 == 0) contest.ranking / 25 else (contest.ranking / 25) + 1
    val contestUrl = "https://leetcode.com/contest/${contest.contest.title.lowercase().replace(" ", "-")}/ranking/$adjustedRanking/?region=global_v2"
    val isTrendingUp = contest.trendDirection.equals("UP", ignoreCase = true)
    val trendIcon = if (isTrendingUp) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown
    val trendColor = if (isTrendingUp) Color.Green else Color.Red
    val context = LocalContext.current
    val formattedTime = String.format("%02d:%02d:%02d",
        TimeUnit.SECONDS.toHours(contest.finishTimeInSeconds.toLong()),
        TimeUnit.SECONDS.toMinutes(contest.finishTimeInSeconds.toLong()) % 60,
        contest.finishTimeInSeconds % 60)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .border(2.dp, trendColor, RoundedCornerShape(12.dp))
            .clickable {

                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(contestUrl))
                context.startActivity(intent)
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "🏆 ${contest.contest.title} ",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    InfoRow("Rating", contest.rating.toString())
                    InfoRow("Ranking", contest.ranking.toString())
                    InfoRow("Problems Solved", "${contest.problemsSolved}/${contest.totalProblems}")
                    InfoRow("Finish Time", "$formattedTime")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    imageVector = trendIcon,
                    contentDescription = "Trend Icon",
                    tint = trendColor,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeetCodeContestScreen(contestData: ContestData?) {
    val rating =  (contestData?.contestRating ?: 0).toInt() // Default to 0 if null
    val borderColor = when {
        rating < (1500  ) -> Color.Gray
        rating >= 1500   &&  rating < (1700  ) -> Color.Green
         rating >= 1700  &&  rating < (2000  ) -> Color.Blue
        else -> Color.Red
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🏆 LeetCode Contests", color = Color.White) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor =  Color.Black// Custom blue accent
                ),
                modifier = Modifier.shadow(4.dp)
            )
        },
        containerColor = Color.Black // Full dark theme
    ) { paddingValues ->
        if (contestData == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No contest data available",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Red // Error in dark mode
                )
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .background(Color.Black), // Force dark background
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 🎯 Contest Stats Card
            Card(
                modifier = Modifier.fillMaxWidth()  ,
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(6.dp),
                colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
                border = BorderStroke(2.dp, borderColor)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "📊 LeetCode Contest Stats",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color(0xFFFFA116) // Light blue accent
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    InfoRow("Contests Attended", contestData.contestAttend.toString())
                    InfoRow("Current Rating", rating.toString())
                    InfoRow("Global Ranking", contestData.contestGlobalRanking.toString())
                    InfoRow("Total Participants", contestData.totalParticipants.toString())
                    InfoRow("Top Percentage", "${contestData.contestTopPercentage}%")

                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "📜 Contest Participation History",
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xFFFFA116) // Light blue accent
            )

            Spacer(modifier = Modifier.height(8.dp))

            contestData.contestParticipation?.reversed()?.forEach { contest ->
                ContestCard(contest)
            }
        }
    }
}
