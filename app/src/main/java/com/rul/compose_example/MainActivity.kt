package com.rul.compose_example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.rul.compose_example.new_jetpack_compose.menu_11.DropDownMenuExample
//import com.rul.compose_example.new_jetpack_compose.navigation_drawer_13.NavigationDrawerExample
//import com.rul.compose_example.new_jetpack_compose.scaffold_12.ScaffoldExample
import com.rul.compose_example.ui.theme.Compose_exampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Compose_exampleTheme {
                //Greeting()
                //SimpleText()
                //TextFieldExample()
                //UserInteractionExample()
                //ButtonExample()
                //ImageExample()
                //ConstraintLayoutExample()
                //BottomSheetExample()
                //CheckBoxExample()
                //InputChipExample()
                //AlertDialogExample()
                //DropDownMenuExample()
                //ScaffoldExample()
                //NavigationDrawerExample()
            }
        }
    }
}

