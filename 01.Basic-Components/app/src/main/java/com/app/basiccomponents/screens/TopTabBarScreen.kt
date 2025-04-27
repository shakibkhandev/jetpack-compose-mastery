package com.app.basiccomponents.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun TopTabBarScreen(navController: NavController, modifier: Modifier) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    val tabs = listOf(
        TabItem("Home", Icons.Default.Home),
        TabItem("Favorites", Icons.Default.Favorite),
        TabItem("Settings", Icons.Default.Settings)
    )

    Column(modifier = modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxWidth()
        ) {
            tabs.forEachIndexed { index, tabItem ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    icon = {
                        Icon(
                            imageVector = tabItem.icon,
                            contentDescription = tabItem.title
                        )
                    },
                    text = {
                        Text(
                            text = tabItem.title,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                )
            }
        }

        // Content for the selected tab
        when (selectedTabIndex) {
            0 -> TabContent("Home Tab Content")
            1 -> TabContent("Favorites Tab Content")
            2 -> TabContent("Settings Tab Content")
        }
    }
}

@Composable
fun TabContent(content: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = content,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

data class TabItem(
    val title: String,
    val icon: ImageVector
)