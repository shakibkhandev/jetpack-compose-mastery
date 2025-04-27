package com.app.basiccomponents.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DatePickersScreen(navController: NavController, modifier: Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            "Date Pickers",
            style = MaterialTheme.typography.headlineMedium
        )

        // First date picker example - Docked
        SectionTitle2("Docked Date Picker")
        DatePickerDocked()

        Spacer(modifier = Modifier.height(8.dp))

        // Second date picker example - Modal
        SectionTitle2("Modal Date Picker")
        DatePickerFieldToModal()

        Spacer(modifier = Modifier.height(8.dp))

        // Third date picker example - Modal with Input Mode
        SectionTitle2("Modal Date Picker with Input Mode")
        DatePickerWithInputMode()

        Spacer(modifier = Modifier.height(8.dp))

        // Fourth date picker example - Date Range Picker
        SectionTitle2("Date Range Picker")
        DateRangePickerExample()
    }
}

@Composable
fun SectionTitle2(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDocked() {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedDate,
            onValueChange = { },
            label = { Text("DOB") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = !showDatePicker }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        )

        if (showDatePicker) {
            Popup(
                onDismissRequest = { showDatePicker = false },
                alignment = Alignment.TopStart
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 64.dp)
                        .shadow(elevation = 4.dp)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp)
                ) {
                    DatePicker(
                        state = datePickerState,
                        showModeToggle = false
                    )
                }
            }
        }
    }
}

@Composable
fun DatePickerFieldToModal(modifier: Modifier = Modifier) {
    var selectedDate by remember { mutableStateOf<Long?>(null) }
    var showModal by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = selectedDate?.let { convertMillisToDate(it) } ?: "",
            onValueChange = { },
            label = { Text("DOB") },
            placeholder = { Text("MM/DD/YYYY") },
            trailingIcon = {
                Icon(Icons.Default.DateRange, contentDescription = "Select date")
            },
            modifier = modifier
                .fillMaxWidth()
                .pointerInput(selectedDate) {
                    awaitEachGesture {
                        // Modifier.clickable doesn't work for text fields, so we use Modifier.pointerInput
                        // in the Initial pass to observe events before the text field consumes them
                        // in the Main pass.
                        awaitFirstDown(pass = PointerEventPass.Initial)
                        val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                        if (upEvent != null) {
                            showModal = true
                        }
                    }
                }
        )

        if (selectedDate != null) {
            val date = Date(selectedDate!!)
            val formattedDate = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(date)
            Text("Selected date: $formattedDate")
        } else {
            Text("No date selected")
        }

        if (showModal) {
            DatePickerModal(
                onDateSelected = { selectedDate = it },
                onDismiss = { showModal = false }
            )
        }
    }
}

@Composable
fun DatePickerWithInputMode() {
    var selectedDate by remember { mutableStateOf<Long?>(null) }
    var showModal by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = selectedDate?.let { convertMillisToDate(it) } ?: "",
            onValueChange = { },
            label = { Text("Date") },
            placeholder = { Text("MM/DD/YYYY") },
            trailingIcon = {
                IconButton(onClick = { showModal = true }) {
                    Icon(Icons.Default.DateRange, contentDescription = "Select date")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        if (selectedDate != null) {
            val date = Date(selectedDate!!)
            val formattedDate = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(date)
            Text("Selected date: $formattedDate")
        } else {
            Text("No date selected")
        }

        if (showModal) {
            DatePickerModalInput(
                onDateSelected = { selectedDate = it },
                onDismiss = { showModal = false }
            )
        }
    }
}

@Composable
fun DateRangePickerExample() {
    var dateRange by remember { mutableStateOf<Pair<Long?, Long?>>(Pair(null, null)) }
    var showModal by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = formatDateRange(dateRange),
            onValueChange = { },
            label = { Text("Date Range") },
            placeholder = { Text("Select date range") },
            trailingIcon = {
                IconButton(onClick = { showModal = true }) {
                    Icon(Icons.Default.DateRange, contentDescription = "Select date range")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        if (dateRange.first != null || dateRange.second != null) {
            Text("Selected range: ${formatDateRange(dateRange)}")
        } else {
            Text("No date range selected")
        }

        if (showModal) {
            DateRangePickerModal(
                onDateRangeSelected = { dateRange = it },
                onDismiss = { showModal = false }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModalInput(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerModal(
    onDateRangeSelected: (Pair<Long?, Long?>) -> Unit,
    onDismiss: () -> Unit
) {
    val dateRangePickerState = rememberDateRangePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDateRangeSelected(
                        Pair(
                            dateRangePickerState.selectedStartDateMillis,
                            dateRangePickerState.selectedEndDateMillis
                        )
                    )
                    onDismiss()
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DateRangePicker(
            state = dateRangePickerState,
            title = {
                Text(
                    text = "Select date range"
                )
            },
            showModeToggle = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(16.dp)
        )
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

fun formatDateRange(dateRange: Pair<Long?, Long?>): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    val startDate = dateRange.first?.let { formatter.format(Date(it)) } ?: ""
    val endDate = dateRange.second?.let { formatter.format(Date(it)) } ?: ""

    return when {
        startDate.isNotEmpty() && endDate.isNotEmpty() -> "$startDate - $endDate"
        startDate.isNotEmpty() -> "From $startDate"
        endDate.isNotEmpty() -> "Until $endDate"
        else -> ""
    }
}