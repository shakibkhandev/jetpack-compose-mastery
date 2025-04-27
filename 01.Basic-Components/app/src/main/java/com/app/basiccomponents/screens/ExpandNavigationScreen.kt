package com.app.basiccomponents.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun ExpandNavigationScreen(navController: NavController,modifier: Modifier) {
    var index by remember { mutableStateOf(0) }

    PermanentNavigationDrawer(
        drawerContent = {
            PermanentDrawerSheet {
                // Navigation items
                NavigationDrawerItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Screen 1") },
                    label = { Text("Screen 1") },
                    selected = index == 0,  // Highlight this item if it's the current screen
                    onClick = {
                        index = 0 // Set the index to 0 for Screen 1
                    }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Screen 2") },
                    label = { Text("Screen 2") },
                    selected = index == 1,  // Highlight this item if it's the current screen
                    onClick = {
                        index = 1 // Set the index to 1 for Screen 2
                    }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Screen 3") },
                    label = { Text("Screen 3") },
                    selected = index == 2,  // Highlight this item if it's the current screen
                    onClick = {
                        index = 2 // Set the index to 2 for Screen 3
                    }
                )
            }
        },
        content = {
            // Conditional content based on the index value
            if (index == 0) {
                Text("This is Screen 1")
            } else if (index == 1) {
                Text("This is Screen 2")
            } else if (index == 2) {
                Text("This is Screen 3")
            }
        },
        modifier = modifier.fillMaxSize()
    )
}
