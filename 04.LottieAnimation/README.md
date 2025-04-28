# Lottie Animations in Jetpack Compose

This README provides a comprehensive guide on integrating and using Lottie animations in your Jetpack Compose Android application.

## Introduction

Lottie is an open-source animation library that renders Adobe After Effects animations in real-time, allowing developers to use beautiful animations without the overhead of traditional animation frameworks.

## Setup

### Step 1: Add Dependencies

Add the Lottie Compose dependency to your app-level `build.gradle` file:

```kotlin
dependencies {
    // Lottie for Compose
    implementation("com.airbnb.android:lottie-compose:6.1.0")
}
```

### Step 2: Add Animation Files

Place your `.json` Lottie animation files in the `res/raw` directory of your project. If the directory doesn't exist, create it:

1. Right-click on the `res` folder
2. Select `New > Android Resource Directory`
3. Set the directory name to `raw` and click `OK`
4. Place your Lottie JSON files in this directory

You can find free Lottie animations at:
- [LottieFiles](https://lottiefiles.com/)
- [Icons8](https://icons8.com/animated-icons)

## Basic Usage

### Simple Animation

```kotlin
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*

@Composable
fun BasicLottieAnimation() {
    // Load the animation
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.your_animation)
    )
    
    // Create the animation state
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever  // Loop infinitely
    )
    
    // Display the animation
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier.size(200.dp)
    )
}
```

### Animation with Controls

```kotlin
@Composable
fun LottieWithControls() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.your_animation)
    )
    val isPlaying = remember { mutableStateOf(false) }
    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = isPlaying.value,
        iterations = 1
    )
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(200.dp)
        )
        
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Button(onClick = { isPlaying.value = true }) {
                Text("Play")
            }
            
            Button(onClick = { isPlaying.value = false }) {
                Text("Pause")
            }
        }
    }
}
```

## Advanced Usage

### Loading Animation

```kotlin
@Composable
fun LoadingScreen(isLoading: Boolean, content: @Composable () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Main content
        content()
        
        // Loading overlay
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                val composition by rememberLottieComposition(
                    LottieCompositionSpec.RawRes(R.raw.loading_animation)
                )
                val progress by animateLottieCompositionAsState(
                    composition = composition,
                    iterations = LottieConstants.IterateForever
                )
                
                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = Modifier.size(200.dp)
                )
            }
        }
    }
}
```

### One-Time Animation

```kotlin
@Composable
fun OneTimeAnimation(onAnimationFinish: () -> Unit) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.success_animation)
    )
    
    // Track if the animation has played
    val isPlaying = remember { mutableStateOf(true) }
    
    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = isPlaying.value,
        iterations = 1,
        clipSpec = LottieClipSpec.Frame(0, 60),  // Optional: play specific frames
        speed = 1.5f  // Optional: control animation speed
    )
    
    // Call callback when animation finishes
    LaunchedEffect(progress) {
        if (progress == 1f && isPlaying.value) {
            isPlaying.value = false
            onAnimationFinish()
        }
    }
    
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier.size(200.dp)
    )
}
```

### Loading from URL

```kotlin
@Composable
fun LottieFromUrl(url: String) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.Url(url)
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier.size(200.dp)
    )
}
```

### Dynamic Properties

```kotlin
@Composable
fun DynamicLottieAnimation() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.color_animation)
    )
    val progress by animateLottieCompositionAsState(
        composition = composition
    )
    
    // Dynamically change the color property
    val dynamicProperties = rememberLottieDynamicProperties(
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            keyPath = arrayOf("**"),
            callback = { Color.Red.toArgb() }
        )
    )
    
    LottieAnimation(
        composition = composition,
        progress = { progress },
        dynamicProperties = dynamicProperties
    )
}
```

## Common Use Cases

### Splash Screen

```kotlin
@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.splash_animation)
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 1
    )
    
    // Navigate when the animation finishes
    LaunchedEffect(progress) {
        if (progress == 1f) {
            onSplashFinished()
        }
    }
    
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress }
        )
    }
}
```

### Error State

```kotlin
@Composable
fun ErrorState(message: String, onRetry: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(R.raw.error_animation)
        )
        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = 1
        )
        
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(180.dp)
        )
        
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}
```

## Troubleshooting

### Common Issues

1. **Animation Not Playing**: Make sure the JSON file is placed in the `res/raw` directory and has no uppercase characters in the filename.

2. **Poor Performance**: Optimize your Lottie animations by:
    - Reducing complexity in After Effects
    - Using simpler shapes
    - Avoiding masks and mattes when possible
    - Setting an appropriate size modifier

3. **Memory Issues**: For large animations, consider:
    - Using `rememberLottieComposition` with appropriate caching
    - Reducing animation complexity
    - Disposing of animations when not needed

4. **Animation Flickering**: This may occur if you're recreating the composition too frequently. Use `remember` to store compositions between recompositions.

## Resources

- [Official Lottie Documentation](https://airbnb.io/lottie/#/android)
- [Lottie Compose GitHub](https://github.com/airbnb/lottie-android)
- [LottieFiles](https://lottiefiles.com/) - Free Lottie animations
- [Lottie Editor](https://lottiefiles.com/editor) - Edit Lottie animations online

## License

Lottie is licensed under the Apache License 2.0. Make sure to check the license of any animations you use in your projects.