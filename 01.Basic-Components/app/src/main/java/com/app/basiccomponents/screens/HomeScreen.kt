package com.app.basiccomponents.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),  // Adding padding for better appearance
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Basic Components",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Row with two cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FeatureCard(
                title = "Inputs",
                onClick = { navController.navigate("inputs") },
                modifier = Modifier.weight(1f)
            )

            FeatureCard(
                title = "Buttons",
                onClick = { navController.navigate("buttons") },
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FeatureCard(
                title = "Drawer",
                onClick = { navController.navigate("drawer") },
                modifier = Modifier.weight(1f)
            )

            FeatureCard(
                title = "Bottom Bar",
                onClick = { navController.navigate("bottombar") },
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FeatureCard(
                title = "Navigation Rail",
                onClick = { navController.navigate("navigationrail") },
                modifier = Modifier.weight(1f)
            )

            FeatureCard(
                title = "Expand Navigation",
                onClick = { navController.navigate("expandnavigation") },
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FeatureCard(
                title = "Bottom Sheet",
                onClick = { navController.navigate("bottomsheet") },
                modifier = Modifier.weight(1f)
            )

            FeatureCard(
                title = "Dialogue",
                onClick = { navController.navigate("dialogue") },
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FeatureCard(
                title = "Search Bar",
                onClick = { navController.navigate("searchbar") },
                modifier = Modifier.weight(1f)
            )

            FeatureCard(
                title = "Time Picker",
                onClick = { navController.navigate("timepicker") },
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FeatureCard(
                title = "Snack Bar",
                onClick = { navController.navigate("snackbar") },
                modifier = Modifier.weight(1f)
            )

            FeatureCard(
                title = "Slider",
                onClick = { navController.navigate("slider") },
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FeatureCard(
                title = "Pull To Refresh",
                onClick = { navController.navigate("pulltorefresh") },
                modifier = Modifier.weight(1f)
            )
            FeatureCard(
                title = "Progress Indicator",
                onClick = { navController.navigate("progressindicator") },
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FeatureCard(
                title = "Menus",
                onClick = { navController.navigate("menus") },
                modifier = Modifier.weight(1f)
            )
            FeatureCard(
                title = "Date Picker",
                onClick = { navController.navigate("datepicker") },
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FeatureCard(
                title = "Top Tab Bar",
                onClick = { navController.navigate("toptabbar") },
                modifier = Modifier.weight(1f)
            )
            FeatureCard(
                title = "Chips",
                onClick = { navController.navigate("chips") },
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FeatureCard(
                title = "Badges",
                onClick = { navController.navigate("badges") },
                modifier = Modifier.weight(1f)
            )
            FeatureCard(
                title = "Checkboxes",
                onClick = { navController.navigate("checkboxes") },
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FeatureCard(
                title = "Carousel",
                onClick = { navController.navigate("carousel") },
                modifier = Modifier.weight(1f)
            )
        }

        // You can easily add more Rows with two Cards if needed
    }
}

@Composable
fun FeatureCard(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(50.dp),  // Fixed height for uniformity
        elevation = CardDefaults.cardElevation(8.dp),
        shape = MaterialTheme.shapes.medium,
        onClick = onClick
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
