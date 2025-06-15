package com.rul.compose_example.new_jetpack_compose.core_components_3

import android.graphics.drawable.Icon
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rul.compose_example.R

@Composable
fun ButtonExample() {

    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //Filled Button
            Button(
                onClick = {
                    Toast.makeText(context, "Filled Button is clicked", Toast.LENGTH_SHORT).show()
                }) {
                Text("Filled Button")
            }
            Spacer(modifier = Modifier.height(16.dp))

            //Tonal Button
            FilledTonalButton(onClick = {
                Toast.makeText(context, "Tonal Button is clicked", Toast.LENGTH_SHORT).show()
            }) {
                Text("Tonal Button")
            }
            Spacer(modifier = Modifier.height(16.dp))

            //Outline Button
            OutlinedButton(onClick = {
                Toast.makeText(context, "Outline Button is clicked", Toast.LENGTH_SHORT).show()
            }) {
                Text("Outline Button")
            }
            Spacer(modifier = Modifier.height(16.dp))

            //Elevated Button
            ElevatedButton(onClick = {
                Toast.makeText(context, "Elevated Button is clicked", Toast.LENGTH_SHORT).show()
            }) {
                Text("Elevated Button")
            }
            Spacer(modifier = Modifier.height(16.dp))

            //Text Button
            TextButton(onClick = {
                Toast.makeText(context, "Text Button is clicked", Toast.LENGTH_SHORT).show()
            }) { Text("Text Button") }
            Spacer(modifier = Modifier.height(16.dp))

            //Custom Elevated Button
            CustomButton(onClick = {
                Toast.makeText(context, "Custom elevated Button is clicked", Toast.LENGTH_SHORT)
                    .show()
            }, text = "Custom Elevated Button")
            Spacer(modifier = Modifier.height(16.dp))

            //Icon Button
            IconButton(onClick = {
                Toast.makeText(context, "Icon Button is clicked", Toast.LENGTH_SHORT).show()
            }) {
                Icon(
                    painter = painterResource(R.drawable.outline_settings_24),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            //Filled Icon Button
            FilledIconButton(onClick = {
                Toast.makeText(context, "Icon Button is clicked", Toast.LENGTH_SHORT).show()
            }) {
                Icon(
                    painter = painterResource(R.drawable.outline_settings_24),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            //Filled Tonal Icon Button
            FilledTonalIconButton(onClick = {
                Toast.makeText(context, "Icon Button is clicked", Toast.LENGTH_SHORT).show()
            }) {
                Icon(
                    painter = painterResource(R.drawable.outline_settings_24),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            //Filled Tonal Icon Button
            FloatingActionButton(onClick = {
                Toast.makeText(context, "Icon Button is clicked", Toast.LENGTH_SHORT).show()
            }) {
                Icon(
                    painter = painterResource(R.drawable.outline_settings_24),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            //Extended Floating Action Button
            ExtendedFloatingActionButton(
                onClick = {
                    Toast.makeText(
                        context,
                        "Extended Floating Action Button is clicked",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.outline_settings_24),
                        contentDescription = null
                    )
                },
                text = { Text("Extended FAB") },
                modifier = Modifier
                    .padding(16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewButtonExample() {
    ButtonExample()
}
