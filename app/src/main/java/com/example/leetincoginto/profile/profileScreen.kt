package com.example.leetincoginto.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.shadow
import com.example.leetincoginto.Apisetup.LeetCodeProfile
import com.example.leetincoginto.Apisetup.Submission
import com.example.leetincoginto.Apisetup.SubmissionStat
import androidx.compose.ui.text.font.FontWeight


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeetCodeProfileScreen(profile: LeetCodeProfile?) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("👨‍💻 LeetCode Profile", color = Color.White) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFF121212)
                ),
                modifier = Modifier.shadow(4.dp)
            )
        },
        containerColor = Color.Black
    ) { paddingValues ->
        if (profile == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.Black)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No profile data available",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.Black)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF1E1E1E),
                    contentColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "📊 Profile Overview",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color(0xFFFFD700)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    InfoRow("Total Solved", profile.totalSolved?.toString() ?: "N/A")
                    InfoRow("Total Questions", profile.totalQuestions?.toString() ?: "N/A")
                    InfoRow("Ranking", profile.ranking?.toString() ?: "N/A")
                    InfoRow("Reputation", profile.reputation?.toString() ?: "N/A")
                    InfoRow("Contribution Points", profile.contributionPoint?.toString() ?: "N/A")

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF252525),
                    contentColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {

                        LeetCodeProgress(
                            profile.easySolved ?: 0, profile.totalEasy ?: 1,
                            profile.mediumSolved ?: 0, profile.totalMedium ?: 1,
                            profile.hardSolved ?: 0, profile.totalHard ?: 1
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            profile.totalSubmissions?.let { stats ->
                Text(
                    text = "📊 Submission Statistics",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFFFFA500)
                )

                Spacer(modifier = Modifier.height(8.dp))

                stats.forEach { stat ->
                    SubmissionStatCard(stat)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            profile.recentSubmissions?.let { submissions ->
                Text(
                    text = "📜 Recent Submissions",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFF03DAC6)
                )

                Spacer(modifier = Modifier.height(8.dp))

                submissions.forEach { submission ->
                    SubmissionCard(submission)
                }
            }
        }
    }
}

@Composable
fun SubmissionStatCard(stat: SubmissionStat) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF252525),
            contentColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Difficulty: ${stat.difficulty}", style = MaterialTheme.typography.titleMedium)
            InfoRow("Total Submissions", stat.submissions.toString())
            InfoRow("Accepted", stat.count.toString())
        }
    }
}

@Composable
fun SubmissionCard(submission: Submission) {
    val borderColor = if (submission.statusDisplay.lowercase() == "accepted") Color.Green else Color.Red

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .border(2.dp, borderColor, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF303030),
            contentColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "📌 ${submission.title}", style = MaterialTheme.typography.titleMedium)
            InfoRow("Status", submission.statusDisplay)
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Text(text = value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
    }
}


