package com.rul.compose_example.new_jetpack_compose.check_box_7

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed

@Composable
fun CheckBoxExample() {

    val childCheckedStatus = remember {
        mutableStateListOf(false, false, false)
    }

    val parentState = when {
        childCheckedStatus.all { it } -> ToggleableState.On
        childCheckedStatus.none() -> ToggleableState.Off
        else -> ToggleableState.Indeterminate
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Select All")
            TriStateCheckbox(state = parentState, onClick = {
                var newState = parentState != ToggleableState.On
                childCheckedStatus.fastForEachIndexed { index, _ ->
                    childCheckedStatus[index] = newState
                }
            })
        }
        childCheckedStatus.fastForEachIndexed { index, status ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Option ${index + 1}")
                Checkbox(
                    checked = status,
                    onCheckedChange = {
                        childCheckedStatus[index] = it
                    }
                )
            }
        }
        if (childCheckedStatus.all { it }) {
            Text("All Option Selected")
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun Preview_CheckBoxExample() {
    CheckBoxExample()
}