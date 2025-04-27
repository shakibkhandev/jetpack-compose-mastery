package com.app.basiccomponents.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun InputsScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    // State variables
    val nameState = remember { mutableStateOf("") }
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val numberState = remember { mutableStateOf("") }
    val customPasswordInput = remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            // Section: Name Inputs
            SectionTitle(title = "Name Inputs")
            StandardTextFields(nameState, label = "Name")
            BasicStyledTextField(nameState)
            OutlinedStyledTextField(nameState, label = "Name")

            // Section: Email Inputs
            SectionTitle(title = "Email Inputs")
            StandardTextFields(emailState, label = "Email", keyboardType = KeyboardType.Email)
            OutlinedStyledTextField(emailState, label = "Email", keyboardType = KeyboardType.Email)

            // Section: Password Inputs
            SectionTitle(title = "Password Inputs")
            StandardTextFields(passwordState, label = "Password", keyboardType = KeyboardType.Password)
            OutlinedStyledTextField(passwordState, label = "Password", keyboardType = KeyboardType.Password)

            // Section: Number Inputs
            SectionTitle(title = "Number Inputs")
            StandardTextFields(numberState, label = "Number", keyboardType = KeyboardType.Number)
            OutlinedStyledTextField(numberState, label = "Number", keyboardType = KeyboardType.Number)

            // Section: Custom Password Field
            SectionTitle(title = "Custom Styled Password")
            CustomTextField(
                textInput = customPasswordInput.value,
                onChangeText = { customPasswordInput.value = it },
                placeholder = "Enter Your Password",
                leftIcon = Icons.Default.Key,
                rightIcon = Icons.Default.RemoveRedEye,
                rightIconClicked = { /* No Action for Now */ },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                )
            )
        }

        // Return Button at Bottom
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ) {
            Text(text = "Return")
        }
    }
}

//----------------------
// Reusable Components
//----------------------

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun StandardTextFields(
    state: MutableState<String>,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    TextField(
        value = state.value,
        onValueChange = { state.value = it },
        label = { Text(text = "Enter Your $label") },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun OutlinedStyledTextField(
    state: MutableState<String>,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = state.value,
        onValueChange = { state.value = it },
        label = { Text(text = "Enter Your $label") },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun BasicStyledTextField(state: MutableState<String>) {
    BasicTextField(
        value = state.value,
        onValueChange = { state.value = it },
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp))
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
            .padding(12.dp),
        decorationBox = { innerTextField ->
            Box {
                if (state.value.isEmpty()) {
                    Text(
                        text = "Enter Your Name",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 14.sp
                    )
                }
                innerTextField()
            }
        },
        singleLine = true
    )
}

@Composable
fun CustomTextField(
    textInput: String,
    onChangeText: (String) -> Unit,
    placeholder: String,
    leftIcon: ImageVector,
    rightIcon: ImageVector,
    rightIconClicked: () -> Unit,
    keyboardOptions: KeyboardOptions
) {
    BasicTextField(
        value = textInput,
        onValueChange = onChangeText,
        keyboardOptions = keyboardOptions,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp
        ),
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp))
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = leftIcon,
                    contentDescription = "Left Icon",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(modifier = Modifier.weight(1f)) {
                    if (textInput.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 14.sp
                        )
                    }
                    innerTextField()
                }
                if (textInput.isNotEmpty()) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = rightIcon,
                        contentDescription = "Clear Icon",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { rightIconClicked() }
                    )
                }
            }
        }
    )
}
