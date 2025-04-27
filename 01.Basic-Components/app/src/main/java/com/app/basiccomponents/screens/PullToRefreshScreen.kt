package com.app.basiccomponents.screens

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.pullToRefreshIndicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

// Constants used in indicators
private const val CROSSFADE_DURATION_MILLIS = 300
private val SPINNER_SIZE = 24.dp

data class RefreshableItem(
    val id: Int,
    val title: String,
    val description: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

@Composable
fun PullToRefreshScreen(navController: NavController, modifier: Modifier = Modifier) {
    val coroutineScope = rememberCoroutineScope()

    // State for the screen
    var isRefreshing by remember { mutableStateOf(false) }
    val items = remember {
        mutableStateListOf<RefreshableItem>().apply {
            addAll(generateSampleItems(20))
        }
    }
    var lastRefreshTime by remember { mutableStateOf<Long?>(null) }

    // Function to refresh data with more dynamic content
    val onRefresh = {
        coroutineScope.launch {
            isRefreshing = true
            delay(1500) // Simulate network delay

            // Generate new items with current timestamp
            val newItems = generateSampleItems(20)
            items.clear()
            items.addAll(newItems)

            lastRefreshTime = System.currentTimeMillis()
            isRefreshing = false
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        // Enhanced Header with last refresh time
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = "Pull To Refresh Examples",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Spacer(modifier = Modifier.height(4.dp))

                lastRefreshTime?.let {
                    val dateFormat = SimpleDateFormat("MMM dd, yyyy HH:mm:ss", Locale.getDefault())
                    Text(
                        text = "Last updated: ${dateFormat.format(Date(it))}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                    )
                }
            }
        }

        // Tabs for different examples
        var selectedTabIndex by remember { mutableStateOf(0) }

        TabRow(selectedTabIndex = selectedTabIndex) {
            Tab(
                selected = selectedTabIndex == 0,
                onClick = { selectedTabIndex = 0 },
                text = { Text("Cards") }
            )
            Tab(
                selected = selectedTabIndex == 1,
                onClick = { selectedTabIndex = 1 },
                text = { Text("Custom Style") }
            )
            Tab(
                selected = selectedTabIndex == 2,
                onClick = { selectedTabIndex = 2 },
                text = { Text("Animated") }
            )
        }

        // Content based on selected tab
        when (selectedTabIndex) {
            0 -> PullToRefreshCardsSample(
                items = items,
                isRefreshing = isRefreshing,
                onRefresh = { onRefresh },
                modifier = Modifier.weight(1f)
            )
            1 -> PullToRefreshCustomStyleSample(
                items = items,
                isRefreshing = isRefreshing,
                onRefresh = { onRefresh },
                modifier = Modifier.weight(1f)
            )
            2 -> PullToRefreshAnimatedSample(
                items = items,
                isRefreshing = isRefreshing,
                onRefresh = { onRefresh },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefreshCardsSample(
    items: List<RefreshableItem>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.titleMedium
                        )

                        if (item.description.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = item.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                        Text(
                            text = dateFormat.format(Date(item.timestamp)),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            modifier = Modifier.align(Alignment.End)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefreshCustomStyleSample(
    items: List<RefreshableItem>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state = rememberPullToRefreshState()
    val containerColor = MaterialTheme.colorScheme.tertiary
    val contentColor = MaterialTheme.colorScheme.onTertiary

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = modifier,
        state = state,
        indicator = {
            PullToRefreshDefaults.Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = isRefreshing,
                containerColor = containerColor,
                state = state
            )
        },
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(items) { item ->
                ListItem(
                    headlineContent = { Text(text = item.title) },
                    supportingContent = item.description.takeIf { it.isNotEmpty() }?.let {
                        { Text(text = it) }
                    },
                    leadingContent = {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = item.id.toString(),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                )
                Divider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefreshAnimatedSample(
    items: List<RefreshableItem>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state = rememberPullToRefreshState()

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = modifier,
        state = state,
        indicator = {
            AnimatedRefreshIndicator(
                state = state,
                isRefreshing = isRefreshing,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text(
                    "Pull down to refresh the content",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                )
            }

            items(items) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Colored circle with item number
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(
                                    generateColorForId(item.id)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "#${item.id}",
                                color = Color.White,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.titleMedium
                            )

                            if (item.description.isNotEmpty()) {
                                Text(
                                    text = item.description,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                )
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                            Text(
                                text = dateFormat.format(Date(item.timestamp)),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimatedRefreshIndicator(
    state: PullToRefreshState,
    isRefreshing: Boolean,
    modifier: Modifier = Modifier,
) {
    val rotation by animateFloatAsState(
        targetValue = if (isRefreshing) 360f else 0f,
        label = "rotation"
    )

    // Color animation based on pull distance
    val distanceFraction = state.distanceFraction.coerceIn(0f, 1f)
    val color by animateColorAsState(
        targetValue = if (isRefreshing)
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.primary.copy(alpha = distanceFraction),
        label = "color"
    )

    Box(
        modifier = modifier.pullToRefreshIndicator(
            state = state,
            isRefreshing = isRefreshing,
        ),
        contentAlignment = Alignment.Center
    ) {
        Crossfade(
            targetState = isRefreshing,
            animationSpec = tween(durationMillis = CROSSFADE_DURATION_MILLIS),
            modifier = Modifier.align(Alignment.Center)
        ) { refreshing ->
            if (refreshing) {
                Icon(
                    imageVector = Icons.Outlined.Refresh,
                    contentDescription = "Refreshing",
                    tint = color,
                    modifier = Modifier
                        .size(SPINNER_SIZE)
                        .rotate(rotation)
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.ArrowDownward,
                    contentDescription = "Pull to refresh",
                    tint = color,
                    modifier = Modifier
                        .size(SPINNER_SIZE)
                        .graphicsLayer {
                            val progress = distanceFraction
                            this.scaleX = 0.5f + (progress * 0.5f)
                            this.scaleY = 0.5f + (progress * 0.5f)
                            this.alpha = progress
                        }
                )
            }
        }
    }
}

// Helper function to generate sample items
private fun generateSampleItems(count: Int): List<RefreshableItem> {
    val currentTime = System.currentTimeMillis()
    val topics = listOf("News", "Weather", "Sports", "Technology", "Health", "Entertainment")

    return List(count) { index ->
        val topic = topics[index % topics.size]
        val id = index + 1

        RefreshableItem(
            id = id,
            title = "$topic Update #$id",
            description = "Latest ${topic.lowercase()} information refreshed just now.",
            timestamp = currentTime - (Random.nextInt(0, 60) * 1000) // Random time within the last minute
        )
    }
}

// Helper function to generate colors based on item ID
private fun generateColorForId(id: Int): Color {
    val hue = (id * 37) % 360
    return Color.hsv(hue.toFloat(), 0.7f, 0.8f)
}