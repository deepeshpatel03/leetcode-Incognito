package com.example.leetincoginto.loginandsginupscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.material3.R.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle


import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

import com.example.leetincoginto.R
import com.example.leetincoginto.googleauth.AuthRepository
import com.example.leetincoginto.viewModel.LeetCodeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun addhandle(navController: NavController,viewModel: LeetCodeViewModel) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF4B4A4A))
            .padding(40.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.Start
    ) {

        var email by remember { mutableStateOf("") }
        var message by remember { mutableStateOf("") }
        if (message.isNotEmpty()) {
            Text(message, color = Color.Red)
        }

        Column {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.back_button)
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                modifier = Modifier.width(200.dp),
                text = "Add Leetcode Handle",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color(255, 125, 0)
            )
        }
        Column {

            TextField(
                value = email,
                onValueChange = { newText ->
                    email = newText
                },
                label = {
                    Text(
                        text = "Enter leetcode Handel",
                        color = Color(255, 125, 0)
                    )
                },
                placeholder = {
                    Text(
                        text = "xyz",
                        color = Color(255, 125, 0)
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
            )

        }
        val coroutineScope = rememberCoroutineScope()
        var isProcessing by remember { mutableStateOf(false) } // Prevent multiple clicks

        OutlinedButton(
            onClick = {
                if (email.isNotEmpty() && !isProcessing) { // Prevent multiple clicks
                    isProcessing = true  // Disable further clicks temporarily
                    message = "Fetching data..."

                    coroutineScope.launch {
                        viewModel.fetchAccountDetails(email, 0, 4000, 10)

                        // Wait for response to be updated
                        while (viewModel.apiResponse.value == null) {
                            delay(1000)  // Wait a short time before checking again
                        }

                        val response = viewModel.apiResponse.value
                        if (response?.objects?.size == 1) {
                            viewModel.addUser("deepesh", email)
                            navController.navigate("home")  // Navigate after successful validation
                        } else {
                            message = "Invalid handle!"
                        }
                        isProcessing = false // Re-enable button
                    }
                } else {
                    message = "Fields cannot be empty!"
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(top = 5.dp),
            enabled = !isProcessing // Disable button while processing
        ) {
            Text(
                text = if (isProcessing) "Processing..." else "Add Account",
                fontWeight = FontWeight.Bold,
                color = Color(255, 125, 0)
            )

        }
    }
}