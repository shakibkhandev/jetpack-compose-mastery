package com.app.basiccomponents.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationRailScreen(navController: NavController, modifier: Modifier) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Profile,
        BottomNavItem.Settings
    )

    // Track the selected index
    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        // Using NavigationRail instead of drawerContent
        topBar = { // Optional, if you want a top bar (or header) above the content
            TopAppBar(
                title = { Text("Navigation Rail") }
            )
        },
        content = { innerPadding ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                // Navigation Rail on the left side
                NavigationRail {
                    items.forEachIndexed { index, item ->
                        NavigationRailItem(
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) },
                            selected = selectedIndex == index,
                            onClick = {
                                selectedIndex = index // Update the selected index when an item is clicked
                            }
                        )
                    }
                }

                // Content area (right side of the screen)
                Box(modifier = Modifier.weight(1f)) {
                    when (selectedIndex) {
                        0 -> HomeScreen(
                            modifier = Modifier.padding(16.dp),
                            onReturn = { navController.popBackStack() } // Simulating popBackStack on return
                        ) // Home screen
                        1 -> ProfileScreen(modifier = Modifier.padding(16.dp)) // Profile screen
                        2 -> SettingsScreen(modifier = Modifier.padding(16.dp)) // Settings screen
                    }
                }
            }
        }
    )
}

// Define Bottom Navigation Items
sealed class BottomNavItem(val route: String, val icon: androidx.compose.ui.graphics.vector.ImageVector, val label: String) {
    object Home : BottomNavItem("home", Icons.Filled.Home, "Home")
    object Profile : BottomNavItem("profile", Icons.Filled.Person, "Profile")
    object Settings : BottomNavItem("settings", Icons.Filled.Settings, "Settings")
}

// Home Screen with Return button
@Composable
fun HomeScreen(modifier: Modifier, onReturn: () -> Unit) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Home Screen",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Return Button to simulate popBackStack behavior
            Button(onClick = { onReturn() }) {
                Text("Return")
            }
        }
    }
}

// Profile Screen
@Composable
fun ProfileScreen(modifier: Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Text(
            text = "Profile Screen",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp)
        )
    }
}

// Settings Screen
@Composable
fun SettingsScreen(modifier: Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Text(
            text = "Settings Screen",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp)
        )
    }
}
