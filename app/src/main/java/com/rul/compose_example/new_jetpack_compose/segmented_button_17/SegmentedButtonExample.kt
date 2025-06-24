package com.rul.compose_example.new_jetpack_compose.segmented_button_17


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsBike
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material3.Icon
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SegmentedButtonExample() {

    var selectedOptions = remember {
        mutableStateListOf(false, false, false)
    }

    var options = listOf("Walk", "Ride", "Drive")


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        MultiChoiceSegmentedButtonRow {
            options.forEachIndexed { index, label ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = options.size
                    ),
                    checked = selectedOptions[index],
                    onCheckedChange = {
                        selectedOptions[index] = !selectedOptions[index]
                    },
                    icon = {
                        SegmentedButtonDefaults.Icon(selectedOptions[index])
                    },
                    label = {
                        when (label) {
                            "Walk" -> Icon(
                                imageVector = Icons.AutoMirrored.Filled.DirectionsWalk,
                                contentDescription = null
                            )

                            "Ride" -> Icon(
                                imageVector = Icons.AutoMirrored.Filled.DirectionsBike,
                                contentDescription = null
                            )

                            "Drive" -> Icon(
                                imageVector = Icons.Default.DirectionsCar,
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun Preview_SegmentedButtonExample() {
    SegmentedButtonExample()
}