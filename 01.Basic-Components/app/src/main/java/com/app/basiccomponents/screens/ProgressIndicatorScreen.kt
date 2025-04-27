package com.app.basiccomponents.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ProgressIndicatorScreen(navController: NavController, modifier: Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Progress Indicators",
            style = MaterialTheme.typography.headlineMedium
        )

        Divider()

        Text(
            text = "Linear Determinate Progress",
            style = MaterialTheme.typography.titleMedium
        )
        LinearDeterminateIndicator()

        Divider()

        Text(
            text = "Linear Indeterminate Progress",
            style = MaterialTheme.typography.titleMedium
        )
        LinearIndeterminateIndicator()

        Divider()

        Text(
            text = "Circular Progress Indicators",
            style = MaterialTheme.typography.titleMedium
        )
        CircularProgressIndicators()
    }
}

@Composable
fun LinearDeterminateIndicator() {
    var currentProgress by remember { mutableStateOf(0f) }
    var loading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope() // Create a coroutine scope

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(onClick = {
            loading = true
            scope.launch {
                loadProgress { progress ->
                    currentProgress = progress
                }
                loading = false // Reset loading when the coroutine finishes
            }
        }, enabled = !loading) {
            Text("Start loading")
        }

        if (loading) {
            LinearProgressIndicator(
                progress = { currentProgress },
                modifier = Modifier.fillMaxWidth(),
            )

            Text(
                text = "${(currentProgress * 100).toInt()}%",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun LinearIndeterminateIndicator() {
    var showProgress by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(onClick = { showProgress = !showProgress }) {
            Text(if (showProgress) "Hide" else "Show")
        }

        if (showProgress) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun CircularProgressIndicators() {
    var currentProgress by remember { mutableStateOf(0f) }
    var loading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Determinate")

            Button(onClick = {
                loading = true
                scope.launch {
                    loadProgress { progress ->
                        currentProgress = progress
                    }
                    loading = false
                }
            }, enabled = !loading) {
                Text("Start")
            }

            if (loading) {
                CircularProgressIndicator(
                    progress = { currentProgress }
                )
                Text("${(currentProgress * 100).toInt()}%")
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Indeterminate")

            var showIndeterminate by remember { mutableStateOf(false) }

            Button(onClick = { showIndeterminate = !showIndeterminate }) {
                Text(if (showIndeterminate) "Hide" else "Show")
            }

            if (showIndeterminate) {
                CircularProgressIndicator()
            }
        }
    }
}

/** Iterate the progress value */
suspend fun loadProgress(updateProgress: (Float) -> Unit) {
    for (i in 1..100) {
        updateProgress(i.toFloat() / 100)
        delay(100)
    }
}