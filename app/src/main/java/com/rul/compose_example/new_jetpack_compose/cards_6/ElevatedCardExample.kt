package com.rul.compose_example.new_jetpack_compose.cards_6

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ElevatedCardExample() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        ElevatedCard(
            modifier = Modifier
                .size(height = 150.dp, width = 300.dp),
            elevation = CardDefaults.cardElevation(
                10.dp
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            ) {
                Text("This is the elevated card")
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun Preview_ElevatedCardExample() {
    ElevatedCardExample()
}