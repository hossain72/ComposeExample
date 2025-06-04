package com.rul.compose_example.new_jetpack_compose.fundamantal_concept_2

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rul.compose_example.R


@Composable
fun Greeting() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.app_name),
            color = colorResource(R.color.primary),
            fontSize = 20.sp
        )
        Image(
            painter = painterResource(R.drawable.bmi_normal),
            modifier = Modifier.size(width = 200.dp, height = 200.dp),
            contentDescription = "image"
        )
    }
}


@Preview(showSystemUi = true)
@Composable
fun GreetingPreview() {
    Greeting()
}
