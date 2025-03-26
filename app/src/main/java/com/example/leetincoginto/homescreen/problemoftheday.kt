package com.example.leetincoginto.homescreen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.leetincoginto.Apisetup.LeetCodeProblem
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.ArrowDropDown


import androidx.compose.material3.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.util.packInts
import com.example.leetincoginto.R

@Composable
fun LeetCodeProblemCard(problem: LeetCodeProblem) {
    val context = LocalContext.current
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable {
                problem.questionLink?.let {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                    context.startActivity(intent)
                }
            },
        colors = CardDefaults.cardColors(containerColor = Color.Black), // Black theme
        border = BorderStroke(2.dp, Color.White) // White border for contrast
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "POTD",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,// Slightly dimmed text
                color = Color(255, 218, 81, 255)
            )
            Text(
                text = problem.questionTitle ?: "No Title",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(255, 255, 255, 255), // White text for visibility
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = " ${problem.difficulty ?: "Unknown"}",
                fontSize = 14.sp,
                color = when (problem.difficulty) {
                    "Hard" -> Color.Red
                    "Medium" -> Color.Yellow
                    "Easy" -> Color.Green
                    else -> Color.Gray
                }
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.ThumbUp,
                    contentDescription = "Likes",
                    tint = Color.Green,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = " ${problem.likes ?: 0}",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Image(
                    painter = painterResource(R.drawable.dislike),
                    contentDescription = "Dislike",
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = " ${problem.dislikes ?: 0}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
            Text(
                text = "Tags: ${problem.topicTags?.joinToString { it.name } ?: "No Tags"}",
                fontSize = 14.sp,
                color = Color.LightGray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
