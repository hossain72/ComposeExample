package com.rul.compose_example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.rul.compose_example.new_jetpack_compose.menu_11.DropDownMenuExample
import com.rul.compose_example.new_jetpack_compose.navigation_drawer_13.NavigationDrawerExample
import com.rul.compose_example.new_jetpack_compose.progress_indicator_14.ProgressIndicatorExample
//import com.rul.compose_example.new_jetpack_compose.pull_to_refresh_15.PullToRefreshExample
import com.rul.compose_example.new_jetpack_compose.scaffold_12.ScaffoldExample
//import com.rul.compose_example.new_jetpack_compose.search_bar_16.DropdownSearchBarExample
//import com.rul.compose_example.new_jetpack_compose.search_bar_16.SearchBarExample
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
                //ProgressIndicatorExample()
                //PullToRefreshExample()
                //SearchBarExample()
                //DropdownSearchBarExample()
            }
        }
    }
}

