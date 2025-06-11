package com.rul.compose_example.new_jetpack_compose.core_components_3

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rul.compose_example.R

@Composable
fun CustomButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = Color.White,
    elevation: Dp = 6.dp,
    cornerRadius: Dp = 12.dp,
    border: BorderStroke = BorderStroke(width = 1.dp, color = Color.Transparent),
    padding: PaddingValues = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
) {
    Card(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(padding),
        shape = RoundedCornerShape(cornerRadius),
        elevation = CardDefaults.cardElevation(
            defaultElevation = elevation
        ),
        border = border,
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
    ) {
        Text(
            modifier = Modifier.padding(padding),
            text = text,
            color = contentColor,
            style = MaterialTheme.typography.labelLarge
        )
    }
}
