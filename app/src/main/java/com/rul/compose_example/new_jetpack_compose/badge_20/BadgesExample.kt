package com.rul.compose_example.new_jetpack_compose.badge_20

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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

@Composable
fun BadgesExample() {
    var itemCount by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        //verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        BadgedBox(
            badge = {
                if (itemCount > 0) {
                    Badge(contentColor = Color.White, containerColor = Color.Red) {
                        Text(text = "$itemCount")
                    }
                }
            }
        ) {
            Icon(Icons.Default.ShoppingCart, contentDescription = null)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            itemCount++
        }) {
            Text("Add Item")
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun Preview_BadgesExample() {
    BadgesExample()
}