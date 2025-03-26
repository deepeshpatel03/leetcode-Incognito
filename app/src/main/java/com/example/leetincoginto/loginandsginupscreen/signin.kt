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
import androidx.compose.foundation.layout.size
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
import coil.compose.rememberAsyncImagePainter

import com.example.leetincoginto.R
import com.example.leetincoginto.googleauth.AuthRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController,authRepository: AuthRepository) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF4B4A4A))
            .padding(40.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.Start
    ) {
        var email by remember { mutableStateOf("") }
        var pass by remember { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(false) }
        var message by remember { mutableStateOf("") }
        if(message.isNotEmpty()){
            Text(message,color=Color.Red)
        }
        Column{
        IconButton(onClick = {navController.popBackStack() }) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.back_button)
            )
        }
    Spacer(modifier=Modifier.padding(8.dp))
        Text(
            modifier = Modifier.width(200.dp),
            text = stringResource(R.string.welcome_back),
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            color = Color(255, 125, 0)
        )}
  Column {

      TextField(
          value = email,
          onValueChange = { newText ->
              email = newText
          },
          label = {
              Text(
                  text = stringResource(R.string.email_address),
                  color = Color(255, 125, 0)
              )
          },
          placeholder = {
              Text(
                  text = stringResource(R.string.sample_email),
                  color = Color(255, 125, 0)
              )
          },
          colors = TextFieldDefaults.textFieldColors(
              containerColor = Color.Transparent
          ),
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
          singleLine = true,
      )

      Spacer(modifier=Modifier.padding(8.dp))

      TextField(
          value = pass,
          onValueChange = { newText ->
              pass = newText
          },
          label = {
              Text(
                  text = "Password",
                  color = Color(255, 125, 0)
              )
          },
          placeholder = {
              Text(
                  text = stringResource(R.string.enter_your_password),
                  color = Color(255, 125, 0)
              )
          },
          colors = TextFieldDefaults.textFieldColors(
              containerColor = Color.Transparent
          ),
          singleLine = true,
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
          visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
          trailingIcon = {
              IconButton(onClick = { passwordVisible = !passwordVisible }) {
                  Image(
                      painter = rememberAsyncImagePainter(model = if (passwordVisible) R.drawable.eyeopen else R.drawable.closeeye),
                      contentDescription = if (passwordVisible) "Hide password" else "Show password",
                      modifier = Modifier.size(24.dp)
                  )
              }
          },
      )

  }
      val coroutineScope = rememberCoroutineScope()

        OutlinedButton(
            onClick = {
                if (email.isNotEmpty() && pass.isNotEmpty()) {
                    coroutineScope.launch {
                        val success = authRepository.signInWithEmail(email, pass)
                        if (success) {
                            message="successful"
//                            nav() // Navigate on success
                            navController.navigate("adduser")
                        } else {
                            message = "Incorrect credentials. Please try again."
                        }
                    }
                } else {
                    message = "Fields cannot be empty!"
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(top = 5.dp)
        ) {
            Text(text = stringResource(R.string.sign_in), fontWeight = FontWeight.Bold,
                color = Color(255, 125, 0))
        }

    }

}