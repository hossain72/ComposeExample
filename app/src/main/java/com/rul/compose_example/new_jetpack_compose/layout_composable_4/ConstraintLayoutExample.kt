package com.rul.compose_example.new_jetpack_compose.layout_composable_4

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet

@Composable
fun ConstraintLayoutExample() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(color = Color.LightGray)
        ) {
            val startGuideline = createGuidelineFromStart(0.1f)
            // Create guideline from the end of the parent at 10% the width of the Composable
            val endGuideline = createGuidelineFromEnd(0.1f)
            //  Create guideline from 16 dp from the top of the parent
            val topGuideline = createGuidelineFromTop(16.dp)
            //  Create guideline from 16 dp from the bottom of the parent
            val bottomGuideline = createGuidelineFromBottom(16.dp)

            val (text1, text2, text3) = createRefs()

            Text("Bottom Left Text", modifier = Modifier.constrainAs(text1) {
                bottom.linkTo(parent.bottom, margin = 8.dp)
                start.linkTo(parent.start, margin = 8.dp)
            })

            Text("Center Text", modifier = Modifier.constrainAs(text2) {
                top.linkTo(parent.top, margin = 0.dp)
                bottom.linkTo(parent.bottom, margin = 0.dp)
                start.linkTo(parent.start, margin = 0.dp)
                end.linkTo(parent.end)
            })

            Text("Top Right Text", modifier = Modifier.constrainAs(text3) {
                end.linkTo(parent.end, margin = 8.dp)
                top.linkTo(parent.top, margin = 8.dp)
            })

        }

    }
}


@Preview(showSystemUi = true)
@Composable
fun Preview_ConstraintLayoutExample() {
    ConstraintLayoutExample()
}