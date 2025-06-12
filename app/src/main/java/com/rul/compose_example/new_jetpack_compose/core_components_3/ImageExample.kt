package com.rul.compose_example.new_jetpack_compose.core_components_3

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rul.compose_example.R

@Composable
fun ImageExample() {

    val rainBowColor = listOf(
        Color.Blue,
        Color.Cyan,
        Color.Yellow,
        Color.Green,
        Color.Cyan,
        Color.Magenta,
    )
    val brush = remember {
        Brush.sweepGradient(
            colors = rainBowColor
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.natural_image),
            contentDescription = "natural image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(300.dp)
                //.size(300.dp) // Custom Size
                //.border(BorderStroke(width = 2.dp, brush))
                .clip(RoundedCornerShape(40.dp)), // Round Corner
            //.clip(CircleShape),// Circular Image
            alignment = Alignment.Center,
            colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply {
                //setToSaturation(0f)
            })
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun Preview_ImageExample() {
    ImageExample()
}