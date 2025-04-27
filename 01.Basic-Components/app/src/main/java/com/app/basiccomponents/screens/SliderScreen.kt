package com.app.basiccomponents.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.graphics.Color

@Composable
fun SliderScreen(navController: NavController, modifier: Modifier) {
    Column(
        modifier = modifier.padding(20.dp),
    ) {
        Text(text = "Minimal Slider", style = MaterialTheme.typography.titleMedium)
        SliderMinimalExample()
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        Text(text = "Advanced Slider", style = MaterialTheme.typography.titleMedium)
        SliderAdvancedExample()
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        Text(text = "Range Slider", style = MaterialTheme.typography.titleMedium)
        RangeSliderExample()
    }
}

@Preview
@Composable
fun SliderMinimalExample() {
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    Column {
        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFF6C4AB6), // purple
                activeTrackColor = Color(0xFF6C4AB6), // purple
                inactiveTrackColor = Color(0xFFE6E0F8) // light purple/gray
            ),
        )
        Text(text = sliderPosition.toString())
    }
}

@Preview
@Composable
fun SliderAdvancedExample() {
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    Column {
        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.secondary,
                activeTrackColor = MaterialTheme.colorScheme.secondary,
                inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            steps = 3,
            valueRange = 0f..50f
        )
        Text(text = sliderPosition.toString())
    }
}

@Preview
@Composable
fun RangeSliderExample() {
    var sliderPosition by remember { mutableStateOf(0f..100f) }
    Column {
        RangeSlider(
            value = sliderPosition,
            steps = 5,
            onValueChange = { range -> sliderPosition = range },
            valueRange = 0f..100f,
            onValueChangeFinished = {
                // launch some business logic update with the state you hold
                // viewModel.updateSelectedSliderValue(sliderPosition)
            },
        )
        Text(text = sliderPosition.toString())
    }
}