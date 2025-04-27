package com.app.basiccomponents

import BottomBarScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.app.basiccomponents.screens.BadgesScreen
import com.app.basiccomponents.screens.BottomSheetScreen
import com.app.basiccomponents.screens.ButtonsScreen
import com.app.basiccomponents.screens.CarouselScreen
import com.app.basiccomponents.screens.CheckBoxScreen
import com.app.basiccomponents.screens.ChipScreen
import com.app.basiccomponents.screens.DatePickersScreen
import com.app.basiccomponents.screens.DialogueScreen
import com.app.basiccomponents.screens.DrawerScreen
import com.app.basiccomponents.screens.ExpandNavigationScreen
import com.app.basiccomponents.screens.HomeScreen
import com.app.basiccomponents.screens.InputsScreen
import com.app.basiccomponents.screens.MenuScreen
import com.app.basiccomponents.screens.NavigationRailScreen
import com.app.basiccomponents.screens.ProgressIndicatorScreen
import com.app.basiccomponents.screens.SearchbarScreen
import com.app.basiccomponents.screens.SliderScreen
import com.app.basiccomponents.screens.SnackBarScreen
import com.app.basiccomponents.screens.TimePickerScreen
import com.app.basiccomponents.screens.PullToRefreshScreen
import com.app.basiccomponents.screens.TopTabBarScreen
import com.app.basiccomponents.ui.theme.BasicComponentsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BasicComponentsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavigationSystem(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun NavigationSystem(modifier : Modifier = Modifier){
    // Set up NavController
    val navController = rememberNavController()

    // Set up the navigation host
    NavHost(navController = navController, startDestination = "main") {
        navigation(
            startDestination = "home",
            route="main"
        ){
            composable("home") {
                HomeScreen(navController = navController, modifier=modifier)
            }
        }
        navigation(
            startDestination = "inputs",
            route="basic"
        ){
            composable("inputs") {
                InputsScreen(navController = navController, modifier = modifier)
            }
            composable("buttons") {
                ButtonsScreen(navController= navController, modifier= modifier)
            }
            composable("drawer") {
                DrawerScreen(navController= navController, modifier = modifier)
            }
            composable("bottombar") {
                BottomBarScreen(navController= navController, modifier = modifier)
            }
            composable("navigationrail") {
                NavigationRailScreen(navController= navController, modifier= modifier)
            }
            composable("expandnavigation") {
                ExpandNavigationScreen(navController= navController, modifier= modifier)
            }
            composable("bottomsheet") {
                BottomSheetScreen(navController= navController, modifier= modifier)
            }
            composable("dialogue"){
                DialogueScreen(navController= navController, modifier= modifier)
            }
            composable("searchbar"){
                SearchbarScreen(navController= navController, modifier= modifier)
            }
            composable("timepicker"){
                TimePickerScreen(navController= navController, modifier= modifier)
            }
            composable("snackbar"){
                SnackBarScreen(navController= navController, modifier= modifier)
            }
            composable("slider"){
                SliderScreen(navController= navController, modifier= modifier)
            }
            composable("pulltorefresh"){
                PullToRefreshScreen(navController= navController, modifier= modifier)
            }
            composable("progressindicator"){
                ProgressIndicatorScreen(navController= navController, modifier= modifier)
            }
            composable("menus"){
                MenuScreen(navController= navController, modifier= modifier)
            }
            composable("datepicker"){
                DatePickersScreen(navController= navController, modifier= modifier)
            }
            composable("toptabbar"){
                TopTabBarScreen(navController= navController, modifier= modifier)
            }
            composable("chips"){
                ChipScreen(navController= navController, modifier= modifier)
            }
            composable("checkboxes"){
                CheckBoxScreen(navController= navController, modifier= modifier)
            }
            composable("badges"){
                BadgesScreen(navController= navController, modifier= modifier)
            }
            composable("carousel"){
                CarouselScreen(navController= navController, modifier= modifier)
            }
        }

    }
}




//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    BasicComponentsTheme {
//
//    }
//}