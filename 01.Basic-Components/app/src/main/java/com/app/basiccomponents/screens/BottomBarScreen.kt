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

@Composable
fun BottomBarScreen(navController: NavController,modifier: Modifier) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Profile,
        BottomNavItem.Settings
    )

    // Track the selected index
    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index // Update the selected index when an item is clicked
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        // Render the appropriate screen based on selectedIndex
        when (selectedIndex) {
            0 -> HomeScreen(
                modifier = Modifier.padding(innerPadding),
                onReturn = { navController.popBackStack() } // Navigate to Profile on return button click
            ) // Home screen
            1 -> ProfileScreen(modifier = Modifier.padding(innerPadding)) // Profile screen
            2 -> SettingsScreen(modifier = Modifier.padding(innerPadding)) // Settings screen
        }
    }
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
