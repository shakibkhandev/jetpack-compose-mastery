package com.app.basiccomponents.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun CheckBoxScreen(navController: NavController, modifier: Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Column {
            Text("Simple Checkbox Example")
            Spacer(modifier = Modifier.height(8.dp))
            CheckboxMinimalExample()
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column {
            Text("Parent-Child Checkbox Example")
            Spacer(modifier = Modifier.height(8.dp))
            CheckboxParentExample()
        }
    }
}

@Composable
fun CheckboxMinimalExample() {
    var checked by remember { mutableStateOf(true) }

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Minimal checkbox")
            Checkbox(
                checked = checked,
                onCheckedChange = { checked = it }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            if (checked) "Checkbox is checked" else "Checkbox is unchecked"
        )
    }
}

@Composable
fun CheckboxParentExample() {
    // Initialize states for the child checkboxes
    val childCheckedStates = remember { mutableStateListOf(false, false, false) }

    // Compute the parent state based on children's states
    val parentState = when {
        childCheckedStates.all { it } -> ToggleableState.On
        childCheckedStates.none { it } -> ToggleableState.Off
        else -> ToggleableState.Indeterminate
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Parent TriStateCheckbox
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Select all")
            TriStateCheckbox(
                state = parentState,
                onClick = {
                    // Determine new state based on current state
                    val newState = parentState != ToggleableState.On
                    childCheckedStates.forEachIndexed { index, _ ->
                        childCheckedStates[index] = newState
                    }
                }
            )
        }

        // Child Checkboxes
        childCheckedStates.forEachIndexed { index, checked ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Option ${index + 1}")
                Checkbox(
                    checked = checked,
                    onCheckedChange = { isChecked ->
                        // Update the individual child state
                        childCheckedStates[index] = isChecked
                    }
                )
            }
        }

        if (childCheckedStates.all { it }) {
            Spacer(modifier = Modifier.height(4.dp))
            Text("All options selected")
        }
    }
}