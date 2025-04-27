package com.app.basiccomponents.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.ui.semantics.Role

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ButtonsScreen(navController: NavController, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Column with buttons
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp), // Extra space to not overlap with FAB
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Elevated Button
            ElevatedButton(onClick = { /* TODO */ }) {
                Text("Elevated Button")
            }

            // Filled Button (Regular Button in Material 3)
            Button(onClick = { /* TODO */ }) {
                Text("Filled Button")
            }

            // Filled Tonal Button
            FilledTonalButton(onClick = { /* TODO */ }) {
                Text("Filled Tonal Button")
            }

            // Outlined Button
            OutlinedButton(onClick = { /* TODO */ }) {
                Text("Outlined Button")
            }

            // Text Button
            TextButton(onClick = { /* TODO */ }) {
                Text("Text Button")
            }

            // Icon Button
            IconButton(onClick = { /* TODO */ }) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Icon Button"
                )
            }

            // Single Choice Segmented Button
            SingleChoiceSegmentedButton()

            // Multi Choice Segmented Button
            MultiChoiceSegmentedButton()

            // Radio Buttons Section
            Text(
                text = "Radio Buttons",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )
            RadioButtonSingleSelection()

            // Section title for switches
            Text(
                text = "Switches",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )

            // Row with different types of switches
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    SwitchMinimalExample()
                    Text("Basic", style = MaterialTheme.typography.bodySmall)
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    SwitchWithIconExample()
                    Text("With Icon", style = MaterialTheme.typography.bodySmall)
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    SwitchWithCustomColors()
                    Text("Custom Colors", style = MaterialTheme.typography.bodySmall)
                }
            }

            Spacer(modifier = Modifier.weight(1f)) // Pushes the return button to the bottom

            // Return Button
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Return")
            }
        }

        // Floating Action Button at the bottom right
        FloatingActionButton(
            onClick = { /* TODO */ },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp) // Adjust the padding as needed
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "FAB"
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleChoiceSegmentedButton(modifier: Modifier = Modifier) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val options = listOf("Day", "Month", "Week")

    SingleChoiceSegmentedButtonRow(modifier = modifier) {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                onClick = { selectedIndex = index },
                selected = index == selectedIndex,
                label = { Text(label) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultiChoiceSegmentedButton(modifier: Modifier = Modifier) {
    val selectedOptions = remember {
        mutableStateListOf(false, false, false)
    }
    val options = listOf("Walk", "Ride", "Drive")

    MultiChoiceSegmentedButtonRow(modifier = modifier) {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                checked = selectedOptions[index],
                onCheckedChange = {
                    selectedOptions[index] = !selectedOptions[index]
                },
                icon = { SegmentedButtonDefaults.Icon(selectedOptions[index]) },
                label = {
                    when (label) {
                        "Walk" -> Icon(
                            imageVector = Icons.AutoMirrored.Filled.DirectionsWalk,
                            contentDescription = "Directions Walk"
                        )
                        "Ride" -> Icon(
                            imageVector = Icons.Default.DirectionsBus,
                            contentDescription = "Directions Bus"
                        )
                        "Drive" -> Icon(
                            imageVector = Icons.Default.DirectionsCar,
                            contentDescription = "Directions Car"
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun SwitchMinimalExample() {
    var checked by remember { mutableStateOf(true) }

    Switch(
        checked = checked,
        onCheckedChange = {
            checked = it
        }
    )
}

@Composable
fun SwitchWithIconExample() {
    var checked by remember { mutableStateOf(true) }

    Switch(
        checked = checked,
        onCheckedChange = {
            checked = it
        },
        thumbContent = if (checked) {
            {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    modifier = Modifier.size(SwitchDefaults.IconSize),
                )
            }
        } else {
            null
        }
    )
}

@Composable
fun SwitchWithCustomColors() {
    var checked by remember { mutableStateOf(true) }

    Switch(
        checked = checked,
        onCheckedChange = {
            checked = it
        },
        colors = SwitchDefaults.colors(
            checkedThumbColor = MaterialTheme.colorScheme.primary,
            checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
            uncheckedThumbColor = MaterialTheme.colorScheme.secondary,
            uncheckedTrackColor = MaterialTheme.colorScheme.secondaryContainer,
        )
    )
}

@Composable
fun RadioButtonSingleSelection(modifier: Modifier = Modifier) {
    val radioOptions = listOf("Calls", "Missed", "Friends")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    // Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
    Column(modifier.selectableGroup()) {
        radioOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = null // null recommended for accessibility with screen readers
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}