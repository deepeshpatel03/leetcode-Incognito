package com.example.leetincoginto.loginandsginupscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.material3.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.leetincoginto.R

@Composable
fun WelcomeScreen(onSignInClick: () -> Unit, onSignUpClick: () -> Unit ) {
    Box( modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF4B4A4A))) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Welcome and Intro Texts
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(R.string.welcome),
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(255, 125, 0)
                )

                Text(
                    text = stringResource(R.string.intro_message),
                    fontSize = 20.sp,
                    modifier = Modifier.alpha(0.3f),
                    color = Color.White
                )
            }

            // Display Image
            Image(
                painter = painterResource(R.drawable.leetbg), // Replace with your actual logo resource name
                contentDescription = stringResource(R.string.welcome_screen_image),
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()
            )

            // Buttons for Sign In and Sign Up
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedButton(
                    onClick = { onSignInClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(bottom = 5.dp),
                     
                ) {
                    Text(text = stringResource(R.string.go_to_sign_in),
                            color = Color(255, 125, 0))
                }

                OutlinedButton(
                    onClick = { onSignUpClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(top = 5.dp)
                ) {
                    Text(
                        text = stringResource(R.string.no_account_yet_msg),
                        color = Color(255, 125, 0)
                    )
                }
            }
        }
    }
}

