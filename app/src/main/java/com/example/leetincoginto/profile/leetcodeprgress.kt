package com.example.leetincoginto.profile

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowRowScopeInstance.align

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LeetCodeProgress(
    easySolved: Int, easyTotal: Int,
    mediumSolved: Int, mediumTotal: Int,
    hardSolved: Int, hardTotal: Int
) {
    val totalQuestions = easyTotal + mediumTotal + hardTotal
    val totalArc = 270f // Sweep angle for 270° cut from the bottom

    // Calculate segment proportions
    val easySegment = (easyTotal.toFloat() / totalQuestions) * totalArc
    val mediumSegment = (mediumTotal.toFloat() / totalQuestions) * totalArc
    val hardSegment = (hardTotal.toFloat() / totalQuestions) * totalArc

    // Solved angles within segments
    val easySolvedAngle = (easySolved.toFloat() / easyTotal) * easySegment
    val mediumSolvedAngle = (mediumSolved.toFloat() / mediumTotal) * mediumSegment
    val hardSolvedAngle = (hardSolved.toFloat() / hardTotal) * hardSegment

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(250.dp)
            .background(Color(0xFF252525))
    ) {
        Canvas(modifier = Modifier.fillMaxSize().align(Alignment.BottomEnd)) {
            var startAngle = 135f // Start from right corner (0°)

            // Easy background and progress
            drawArc(
                color = Color.LightGray,
                startAngle = startAngle,
                sweepAngle = easySegment,
                useCenter = false,
                style = Stroke(width = 30f, cap = StrokeCap.Round)
            )
            drawArc(
                color = Color(0xFF4CAF50), // Green
                startAngle = startAngle,
                sweepAngle = easySolvedAngle,
                useCenter = false,
                style = Stroke(width = 30f, cap = StrokeCap.Round)
            )
            startAngle += easySegment

            // Medium background and progress
            drawArc(
                color = Color.LightGray,
                startAngle = startAngle,
                sweepAngle = mediumSegment,
                useCenter = false,
                style = Stroke(width = 30f, cap = StrokeCap.Round)
            )
            drawArc(
                color = Color(0xFFFFA726), // Orange
                startAngle = startAngle,
                sweepAngle = mediumSolvedAngle,
                useCenter = false,
                style = Stroke(width = 30f, cap = StrokeCap.Round)
            )
            startAngle += mediumSegment

            // Hard background and progress
            drawArc(
                color = Color.LightGray,
                startAngle = startAngle,
                sweepAngle = hardSegment,
                useCenter = false,
                style = Stroke(width = 30f, cap = StrokeCap.Round)
            )
            drawArc(
                color = Color(0xFFE57373), // Red
                startAngle = startAngle,
                sweepAngle = hardSolvedAngle,
                useCenter = false,
                style = Stroke(width = 30f, cap = StrokeCap.Round)
            )
        }

        // Center Text for Progress
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "LeetCode Progress", fontSize = 18.sp, color = Color.Gray)
            Text(text = "Easy: $easySolved/$easyTotal", fontSize = 14.sp, color = Color(0xFF4CAF50))
            Text(text = "Medium: $mediumSolved/$mediumTotal", fontSize = 14.sp, color = Color(0xFFFFA726))
            Text(text = "Hard: $hardSolved/$hardTotal", fontSize = 14.sp, color = Color(0xFFE57373))
        }
    }
}

