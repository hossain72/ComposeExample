package com.rul.compose_example.new_jetpack_compose.scaffold_12

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldExample() {
    var presses by remember { mutableStateOf(0) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Scaffold Example") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6200EE), // Your custom color
                    titleContentColor = Color.White
                )
                /* colors = TopAppBarColors(
                     containerColor = MaterialTheme.colorScheme.primaryContainer,
                     scrolledContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                     navigationIconContentColor = MaterialTheme.colorScheme.primaryContainer,
                     titleContentColor = MaterialTheme.colorScheme.primary,
                     actionIconContentColor = MaterialTheme.colorScheme.surface
                 )*/
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                Text("Bottom Bar", modifier = Modifier.padding(16.dp))
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { presses++ },
                modifier = Modifier.padding(horizontal = 0.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        },
        contentWindowInsets = WindowInsets(0)
    ) { innerPadding ->

        // The important part: apply innerPadding
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)  // 👈 this ensures content is below TopAppBar and above BottomAppBar
                .background(Color.Gray.copy(alpha = 0.4f))
                .padding(16.dp),        // additional normal padding if you want
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("I've been clicked $presses times")
            Button(onClick = { /* Handle click */ }) {
                Text("Click Me")
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun Preview_ScaffoldExample() {
    ScaffoldExample()
}