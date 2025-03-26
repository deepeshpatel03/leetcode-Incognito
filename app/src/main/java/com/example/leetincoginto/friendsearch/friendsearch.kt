package com.example.leetincoginto.friendsearch


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.ImeAction
import androidx.navigation.NavController
import com.example.leetincoginto.Apisetup.ApiResponse
import com.example.leetincoginto.Apisetup.search

@Composable
fun SearchScaffold(onSearch: (search) -> Unit, apiResponse: ApiResponse?,onADD:(String)->Unit) {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    var minRating by remember { mutableStateOf("10") }
    var maxRating by remember { mutableStateOf("4000") }
    var sortAscending by remember { mutableStateOf(true) }
    var showFilters by remember { mutableStateOf(false) }
    var limit by remember { mutableStateOf("10") }

    Column(modifier=Modifier.background(Color.Black).padding(16.dp)){
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text("Search") },
                singleLine = true,
                modifier = Modifier.weight(1f),
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.White),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search), // Enables search button
                keyboardActions = KeyboardActions(
                    onSearch = {
                        val min = minRating.toIntOrNull()
                        val max = maxRating.toIntOrNull()
                        val limits = limit.toIntOrNull()
                        if (min != null && max != null && limits != null) {
                            showFilters = false
                            onSearch(search(searchText.text, min, max, limits))
                        }
                    }
                )
            )
            IconButton(onClick = {
                val min = minRating.toIntOrNull()
                val max = maxRating.toIntOrNull()
                val limits=limit.toIntOrNull()
                if (min != null&&max !=null&&limits!=null) {
                    showFilters=false
                    onSearch(search(searchText.text,min,max,limits))
                }
            }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon", tint = Color.White)
            }
            IconButton(onClick = { showFilters = !showFilters }) {
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Filter Icon", tint = Color.White)
            }
        }
        if (showFilters) {
            Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedTextField(
                        value = minRating,
                        onValueChange = { minRating = it },
                        label = { Text("Min Rating") },
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.White)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = maxRating,
                        onValueChange = { maxRating = it },
                        label = { Text("Max Rating") },
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.White)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    OutlinedTextField(
                        value = limit,
                        onValueChange = { limit=it },
                        label = { Text("Limit") },
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.White)
                    )

                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .height(54.dp)
                            .fillMaxWidth()
                            .weight(1f)
                            .border(1.dp, Color.White, shape = MaterialTheme.shapes.small) // White border
                            .clickable { sortAscending = !sortAscending }
                        ,
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (sortAscending) "Ascending ▲" else "Descending ▼", // Ascending/Descending indicator
                            color = Color.White
                        )
                    }
                }
            }
        }

        Divider(color = Color.Gray, thickness = 1.dp)
        Column(modifier = Modifier .fillMaxSize()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                apiResponse?.objects?.let { users ->
                    val sortedUsers = if ( sortAscending) users.sortedBy { it.rating } else users.sortedByDescending { it.rating }
                    var icon=Icons.Default.Add
                    items(sortedUsers) { user ->
                        val borderColor = when {
                            user.rating < (1500  ) -> Color.Gray
                            user.rating >= 1500   &&  user.rating < (1700  ) -> Color.Green
                            user.rating >= 1700  && user.rating < (2000  ) -> Color.Blue
                            else -> Color.Red
                        }
                        OutlinedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .wrapContentHeight(),
                            colors = CardDefaults.outlinedCardColors(containerColor = Color.DarkGray),
                            border = BorderStroke(2.dp, borderColor)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Column(modifier = Modifier.weight(1f)) {
                                    Text("Name: ${user.name}", color = Color.White)
                                    Text("Rating: ${user.rating}", color = Color.White)
                                }

                                IconButton(
                                    onClick = { onADD(user.handle)
                                        icon=Icons.Default.CheckCircle
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Add User",
                                        tint = if(icon==Icons.Default.Add) Color.White else Color.Green
                                    )
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}



