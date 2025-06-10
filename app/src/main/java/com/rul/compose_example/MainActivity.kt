package com.rul.compose_example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.rul.compose_example.new_jetpack_compose.core_components_3.UserInteraction
import com.rul.compose_example.ui.theme.Compose_exampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Compose_exampleTheme {
                // Greeting()
                //SimpleText()
                //TextFieldExample()
                UserInteraction()
            }
        }
    }
}

