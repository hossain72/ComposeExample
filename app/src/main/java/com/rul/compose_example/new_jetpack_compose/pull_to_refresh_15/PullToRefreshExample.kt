package com.rul.compose_example.new_jetpack_compose.pull_to_refresh_15

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.*


@Composable
fun PullToRefreshExample() {
    var scope = rememberCoroutineScope()
    var isRefreshing by remember {
        mutableStateOf(false)
    }
    var items by remember {
        mutableStateOf(List(20) { "item $it" })
    }

    fun refreshItem() {
        scope.launch {
            isRefreshing = true
            delay(timeMillis = 2000)
            items = List(20) { "item #${(0..100).random()}" }
            isRefreshing = false
        }

    }

    PullToRefreshCustomIndicationSample(
        item = items,
        isRefresh = isRefreshing,
        onRefresh = { refreshItem() },
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefreshCustomIndicationSample(
    item: List<String>, isRefresh: Boolean, onRefresh: () -> Unit, modifier: Modifier = Modifier
) {
    val state = rememberPullToRefreshState()

    PullToRefreshBox(
        isRefreshing = isRefresh,
        onRefresh = onRefresh,
        state = state,
        modifier = modifier.fillMaxSize(),
        indicator = {
            MyCustomIndicator(state = state, isRefreshing = isRefresh)
        }) {
        LazyColumn(Modifier.fillMaxSize()) {
            items(item) {
                ListItem(
                    headlineContent = { Text(it) }
                )
            }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCustomIndicator(
    state: PullToRefreshState,
    isRefreshing: Boolean,
    modifier: Modifier = Modifier
) {
    // Parent Box to track full gesture area (don't apply background here)
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        // Actual indicator box with size and styling, applies the modifier here
        Box(
            modifier = modifier
                .pullToRefreshIndicator(
                    state = state,
                    isRefreshing = isRefreshing,
                    threshold = PullToRefreshDefaults.PositionalThreshold,
                    // Transparent so no full-width background
                    containerColor =PullToRefreshDefaults.containerColor,
                )
                .wrapContentSize() // Ensures indicator only takes required space
        ) {
            Crossfade(
                targetState = isRefreshing,
                animationSpec = tween(300)
            ) { refreshing ->
                if (refreshing) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Pull to Refresh",
                        tint = Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun Preview_PullToRefreshExample() {
    PullToRefreshExample()
}