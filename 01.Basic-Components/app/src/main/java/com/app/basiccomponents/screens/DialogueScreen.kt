package com.app.basiccomponents.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun DialogueScreen(navController: NavController, modifier: Modifier = Modifier) {
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Define the icon for the dialog
    val icon: ImageVector = Icons.Default.Warning

    // Button to trigger dialog
    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { showDialog = true }, modifier = Modifier.padding(16.dp)) {
            Text("Show Alert Dialog")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Return button to pop back stack
        Button(onClick = {
            navController.popBackStack() // This will pop the current screen from the stack
        }) {
            Text("Return")
        }
    }

    // Conditionally display the dialog
    if (showDialog) {
        AlertDialogExample(
            onDismissRequest = {
                showDialog = false
            },
            onConfirmation = {
                showDialog = false
                // Action to perform on confirmation
                Toast.makeText(context, "Confirmed", Toast.LENGTH_SHORT).show()
            },
            dialogTitle = "Example Alert Dialog",
            dialogText = "Do you want to confirm this action?",
            icon = icon
        )
    }
}

@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewDialogueScreen() {
    // Create a dummy NavController for preview purposes
    DialogueScreen(navController = rememberNavController())
}
