package com.rul.compose_example.new_jetpack_compose.snackbar_19

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun SnackBarExample() {
    var scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            Box(modifier = Modifier.fillMaxSize()) {
                SnackbarHost(
                    hostState = snackBarHostState,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {
                    Text(text = "Show Snackbar")
                },
                icon = {
                    Icon(Icons.Filled.Image, contentDescription = null)
                },
                onClick = {
                    scope.launch {
                        val result = snackBarHostState.showSnackbar(
                            message = "Snack Bar",
                            actionLabel = "Action",
                            duration = SnackbarDuration.Indefinite
                        )
                        when (result) {
                            SnackbarResult.ActionPerformed -> {

                            }

                            SnackbarResult.Dismissed -> {

                            }
                        }
                    }
                }
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Text(text = "Tab the fab tyo show the Snack Bar")
            }
        }
    )

}

@Preview
@Composable
fun Preview_SnackBarExample() {
    SnackBarExample()
}
