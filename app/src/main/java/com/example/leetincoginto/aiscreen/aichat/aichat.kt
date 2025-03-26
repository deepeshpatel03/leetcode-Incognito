package com.example.leetincoginto.aiscreen.aichat

import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.leetincoginto.Apisetup.Message

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(messages: List<Message>, onSendMessage: (String) -> Unit, loading: Boolean) {
    var userMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chatbot") },
                modifier = Modifier.fillMaxWidth() // ✅ Ensures full width
            )
        },
        bottomBar = {
            OutlinedTextField(
                value = userMessage,
                onValueChange = { userMessage = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                placeholder = { Text("Type your message...") },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
                singleLine = true,
                trailingIcon = {
                    IconButton(
                        onClick = {
                            if (userMessage.isNotBlank() && !loading) {
                                onSendMessage(userMessage)
                                userMessage = ""
                            }
                        },
                        enabled = !loading
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Send"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // ✅ Prevents overlap with TopAppBar
                 // ✅ Prevents UI shift when keyboard appears
        ) {
            LazyColumn(

                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,

                reverseLayout = true
            ) {
                if (loading) {
                    item {
                        ChatBubble(Message("ai", "typing..."))
                    }
                }
                items(messages.reversed()) { message ->
                    ChatBubble(message)
                }
            }
        }
    }
}
@Composable
fun ChatBubble(message: Message) {
    val isUser = message.role == "user"
    val backgroundColor = if (isUser) Color(0xFF007AFF) else Color(0xFFE0E0E0) // Blue for user, gray for AI
    val textColor = if (isUser) Color.White else Color.Black


    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = backgroundColor),
            modifier = Modifier
                .padding(4.dp)
                .widthIn(max = 300.dp)
        ) {
            Text(
                text = message.content,
                color = textColor,
                modifier = Modifier.padding(12.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}