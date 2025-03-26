package com.example.leetincoginto.homescreen
import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.border
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.leetincoginto.Apisetup.Contest

@SuppressLint("SimpleDateFormat")
fun parseDate(inputDate: String?): Long {
    if (inputDate.isNullOrEmpty()) return Long.MAX_VALUE
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        inputFormat.parse(inputDate)?.time ?: Long.MAX_VALUE
    } catch (e: Exception) {
        Long.MAX_VALUE
    }
}

@SuppressLint("SimpleDateFormat")
fun formatDateTime(inputDate: String?): String {
    if (inputDate.isNullOrEmpty()) return "N/A"
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMM yyyy ", Locale.getDefault())
        val date = inputFormat.parse(inputDate)
        outputFormat.format(date ?: return "Invalid Date")
    } catch (e: Exception) {
        "Invalid Date"
    }
}

@Composable
fun ContestCard(contests: List<Contest>) {
    if (contests.isEmpty()) {
        Text(text = "No contest data available", color = Color.White, fontSize = 16.sp)
        return
    }

    val sortedContests = contests.sortedBy { parseDate(it.start) } // Sort contests by start date

    Column(modifier = Modifier.padding(16.dp)) {
        sortedContests.forEach { contest ->
            contest?.let {
                val imageUrl = if (it.event?.startsWith("Biweekly") == true)
                    "https://leetcode.com/_next/static/images/biweekly-default-f5a8fc3be85b6c9175207fd8fd855d47.png"
                else
                    "https://leetcode.com/_next/static/images/weekly-default-553ede7bcc8e1b4a44c28a9e4a32068c.png"

                val uriHandler = LocalUriHandler.current

                Card(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth()
                        .border(2.dp, Color.White, RoundedCornerShape(12.dp)), // White outline
                    colors = CardDefaults.cardColors(containerColor = Color.Black) // Black theme
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp) // 80% of card height
                                .clip(RoundedCornerShape(8.dp))
                        ) {
                            AsyncImage(
                                model = imageUrl,
                                contentDescription = "Contest Image",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.BottomStart)
                                    .background(Color.Black.copy(alpha = 0.7f))
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = it.event ?: "No Event",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Start: ${formatDateTime(it.start)}",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ){
                            Button(
                                onClick = { it.href?.let { url -> uriHandler.openUri(url) } },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(
                                    0,
                                    200,
                                    83,
                                    255
                                )
                                )
                            ) {
                                Text(text = "Join Contest", color = Color.White)
                            }

                            OutlinedButton(
                                onClick = { it.href?.let { url -> uriHandler.openUri(it.href) } },
                                border = BorderStroke(1.dp, Color.White)
                            ) {
                                Text(text = "Register", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }

}