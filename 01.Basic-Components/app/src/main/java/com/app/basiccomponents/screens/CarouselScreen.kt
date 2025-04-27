package com.app.basiccomponents.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.carousel.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage

data class CarouselItem(
    val id: Int,
    val imageUrl: String,
    val contentDescription: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarouselScreen(navController: NavController, modifier: Modifier) {
    // Generate items with Picsum images
    val items = List(5) { index ->
        // Using Picsum with specific IDs for consistency and different image width/height
        CarouselItem(
            id = index,
            // Using seed parameter to get consistent images (200 + index)
            // Width: 800, Height: 500
            imageUrl = "https://picsum.photos/seed/${200 + index}/800/500",
            contentDescription = "Image ${index + 1}"
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
    ) {
        // Title for the first carousel
        Text(
            text = "Multi Browse Carousel",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        // First carousel - HorizontalMultiBrowseCarousel
        HorizontalMultiBrowseCarousel(
            state = rememberCarouselState { items.count() },
            modifier = Modifier
                .fillMaxWidth()
                .height(221.dp),
            preferredItemWidth = 186.dp,
            itemSpacing = 8.dp,
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) { i ->
            val item = items[i]
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.contentDescription,
                modifier = Modifier
                    .height(205.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Title for the second carousel
        Text(
            text = "Uncontained Carousel",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        // Second carousel - HorizontalUncontainedCarousel
        HorizontalUncontainedCarousel(
            state = rememberCarouselState { items.count() },
            modifier = Modifier
                .fillMaxWidth()
                .height(221.dp),
            itemWidth = 186.dp,
            itemSpacing = 8.dp,
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) { i ->
            val item = items[i]
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.contentDescription,
                modifier = Modifier
                    .height(205.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}