package com.app.basiccomponents.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import androidx.navigation.NavController

// Data class to represent a contact
data class Contact(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> CustomizableSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    searchResults: List<T>,
    onResultClick: (T) -> Unit,
    // Customization options
    placeholder: @Composable () -> Unit = { Text("Search") },
    leadingIcon: @Composable (() -> Unit)? = { Icon(Icons.Default.Search, contentDescription = "Search") },
    trailingIcon: @Composable (() -> Unit)? = null,
    supportingContent: (@Composable (T) -> Unit)? = null,
    leadingContent: (@Composable (T) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    // Track expanded state of search bar
    var expanded by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier
            .fillMaxSize()
            .semantics { isTraversalGroup = true }
    ) {
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics { traversalIndex = 0f },
            query = query,
            onQueryChange = onQueryChange,
            onSearch = {
                onSearch(query)
                expanded = false
            },
            active = expanded,
            onActiveChange = { expanded = it },
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon
        ) {
            // Show search results in a lazy column for better performance
            LazyColumn {
                items(count = searchResults.size) { index ->
                    val resultItem = searchResults[index]
                    ListItem(
                        headlineContent = { Text(resultItem.toString()) },
                        supportingContent = supportingContent?.let { { it(resultItem) } },
                        leadingContent = leadingContent?.let { { it(resultItem) } },
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                        modifier = Modifier
                            .clickable {
                                onResultClick(resultItem)
                                expanded = false
                            }
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchbarScreen(navController: NavController, modifier: Modifier) {
    // List of 20 sample contacts
    val allContacts = remember {
        listOf(
            Contact(1, "Alice Johnson", "alice.j@example.com", "555-123-4567"),
            Contact(2, "Bob Smith", "bob.smith@example.com", "555-234-5678"),
            Contact(3, "Carol Williams", "carol.w@example.com", "555-345-6789"),
            Contact(4, "David Brown", "david.b@example.com", "555-456-7890"),
            Contact(5, "Emma Davis", "emma.d@example.com", "555-567-8901"),
            Contact(6, "Frank Miller", "frank.m@example.com", "555-678-9012"),
            Contact(7, "Grace Wilson", "grace.w@example.com", "555-789-0123"),
            Contact(8, "Henry Moore", "henry.m@example.com", "555-890-1234"),
            Contact(9, "Isabella Taylor", "isabella.t@example.com", "555-901-2345"),
            Contact(10, "Jack Anderson", "jack.a@example.com", "555-012-3456"),
            Contact(11, "Kate Thomas", "kate.t@example.com", "555-123-7890"),
            Contact(12, "Leo Jackson", "leo.j@example.com", "555-234-8901"),
            Contact(13, "Mia White", "mia.w@example.com", "555-345-9012"),
            Contact(14, "Noah Harris", "noah.h@example.com", "555-456-0123"),
            Contact(15, "Olivia Martin", "olivia.m@example.com", "555-567-1234"),
            Contact(16, "Peter Thompson", "peter.t@example.com", "555-678-2345"),
            Contact(17, "Quinn Garcia", "quinn.g@example.com", "555-789-3456"),
            Contact(18, "Ryan Martinez", "ryan.m@example.com", "555-890-4567"),
            Contact(19, "Sophia Robinson", "sophia.r@example.com", "555-901-5678"),
            Contact(20, "Tyler Clark", "tyler.c@example.com", "555-012-6789")
        )
    }

    // State for search query
    var searchQuery by rememberSaveable { mutableStateOf("") }

    // Filtered contacts based on search query
    val filteredContacts = remember(searchQuery) {
        if (searchQuery.isEmpty()) {
            emptyList<Contact>()
        } else {
            allContacts.filter { contact ->
                contact.name.contains(searchQuery, ignoreCase = true) ||
                        contact.email.contains(searchQuery, ignoreCase = true) ||
                        contact.phone.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        CustomizableSearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            onSearch = { /* Search is already triggered by filtering */ },
            searchResults = filteredContacts,
            onResultClick = { contact ->
                Toast.makeText(context, "Selected: ${contact.name}", Toast.LENGTH_SHORT).show()
            },
            placeholder = { Text("Search contacts...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Contacts") },
            supportingContent = { contact ->
                Text(
                    text = contact.email,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            leadingContent = { contact ->
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Contact",
                    tint = Color(0xFF6200EE)
                )
            }
        )
    }
}