# Retrofit with Jetpack Compose - JSONPlaceholder API Integration

This README provides a step-by-step guide for integrating a REST API (JSONPlaceholder) with a Jetpack Compose Android application using Retrofit.

## Prerequisites

- Android Studio (Arctic Fox or newer)
- Basic knowledge of Kotlin and Jetpack Compose
- Internet connection for testing API calls

## Project Setup

### Step 1: Add Required Dependencies

Add the following dependencies to your app-level `build.gradle` file:

```gradle
dependencies {
    // Retrofit for API requests
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    
    // Compose dependencies (typically already in your project)
    implementation("androidx.compose.material3:material3:1.1.2")
    implementation("androidx.compose.runtime:runtime:1.5.0")
    implementation("androidx.activity:activity-compose:1.7.2")
}
```

### Step 2: Add Internet Permission

Add the internet permission to your `AndroidManifest.xml` file:

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="your.package.name">

    <uses-permission android:name="android.permission.INTERNET" />
    
    <application
        ...
    </application>
</manifest>
```

### Step 3: Create Data Model

Create a data class to represent a post from JSONPlaceholder:

```kotlin
data class Post(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)
```

### Step 4: Set Up Retrofit Interface

Create an interface for the API endpoints:

```kotlin
interface JsonPlaceholderApi {
    @GET("posts")
    suspend fun getPosts(): List<Post>
}
```

### Step 5: Create Retrofit Instance

Set up a singleton Retrofit instance:

```kotlin
object RetrofitInstance {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    
    val api: JsonPlaceholderApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JsonPlaceholderApi::class.java)
    }
}
```

### Step 6: Create ViewModel

Create a ViewModel to handle API calls and state:

```kotlin
class PostsViewModel : ViewModel() {
    var posts by mutableStateOf<List<Post>>(emptyList())
    var isLoading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)
    
    init {
        fetchPosts()
    }
    
    fun fetchPosts() {
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                posts = RetrofitInstance.api.getPosts()
            } catch (e: Exception) {
                error = "Failed to fetch posts: ${e.message}"
            }
            isLoading = false
        }
    }
}
```

### Step 7: Create UI Components

Create Composable functions to display posts:

```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostsScreen(viewModel: PostsViewModel = viewModel()) {
    val posts = viewModel.posts
    val isLoading = viewModel.isLoading
    val error = viewModel.error
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("JSONPlaceholder Posts") }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(androidx.compose.ui.Alignment.Center)
                    )
                }
                error != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text("Error: $error", color = MaterialTheme.colorScheme.error)
                        Button(
                            onClick = { viewModel.fetchPosts() },
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Text("Retry")
                        }
                    }
                }
                else -> {
                    LazyColumn {
                        items(posts) { post ->
                            PostItem(post)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PostItem(post: Post) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = post.title,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = post.body,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Post ID: ${post.id}, User ID: ${post.userId}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
```

### Step 8: Update MainActivity

Modify your MainActivity to use the PostsScreen:

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YourAppTheme {
                PostsScreen()
            }
        }
    }
}
```

## Troubleshooting Common Issues

### 1. App Crashes Due to Network on Main Thread

If the app crashes with a `NetworkOnMainThreadException`, make sure:
- You're using `viewModelScope.launch` for API calls
- You've properly set up Retrofit with coroutines

### 2. App Crashes with HTTP-related Errors

For HTTP errors:
- Check that your API URL is correct
- Verify your data model matches the JSON structure
- Add logging interceptor to see detailed API errors:
  ```gradle
  implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
  ```
  
  ```kotlin
  val client = OkHttpClient.Builder()
      .addInterceptor(HttpLoggingInterceptor().apply { 
          level = HttpLoggingInterceptor.Level.BODY 
      })
      .build()
      
  Retrofit.Builder()
      .client(client)
      // other configuration
  ```

### 3. Network Security Issues (Android 9+)

For Android 9+ you might need to configure network security:

Create `res/xml/network_security_config.xml`:
```xml
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <domain-config cleartextTrafficPermitted="true">
        <domain includeSubdomains="true">jsonplaceholder.typicode.com</domain>
    </domain-config>
</network-security-config>
```

Update your AndroidManifest.xml:
```xml
<application
    android:networkSecurityConfig="@xml/network_security_config"
    ... >
```

## Additional Features

After completing the basic integration, you can extend your app with:

1. Implementing different API endpoints (GET, POST, PUT, DELETE)
2. Adding pull-to-refresh functionality
3. Implementing pagination
4. Adding search functionality
5. Implementing offline caching with Room database

## Resources

- [Retrofit Documentation](https://square.github.io/retrofit/)
- [JSONPlaceholder API](https://jsonplaceholder.typicode.com/)
- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)