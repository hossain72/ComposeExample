package com.rul.compose_example.new_jetpack_compose.search_bar_16

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.zIndex

@Composable
fun DropdownSearchBarExample() {
    var searchResults by remember { mutableStateOf(listOf<String>()) }
    var isLoading by remember { mutableStateOf(false) }

    // Sample data to search from
    val allItems = remember {
        listOf(
            "Anowar Ahmed",
            "Anowar Khan",
            "John Anowar",
            "Anowar Hossain",
            "Ahmed Rahman",
            "Khan Saiful",
            "Anowar Islam",
            "Rafiq Ahmed",
            "Saiful Islam",
            "Rahman Khan",
            "Anowar Ali",
            "Ali Hassan",
            "Hassan Ahmed",
            "Mohammad Anowar",
            "Karim Rahman"
        )
    }

    val onSearch: (String) -> Unit = { query ->
        if (query.length >= 3) {
            isLoading = true
            // Filter items that contain the search query (case insensitive)
            searchResults = allItems.filter { item ->
                item.lowercase().contains(query.lowercase())
            }
            isLoading = false
        } else {
            searchResults = emptyList()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        DropdownSearchBar(
            onSearch = onSearch,
            searchResults = searchResults,
            isLoading = isLoading
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DropdownSearchBar(
    onSearch: (String) -> Unit,
    searchResults: List<String>,
    isLoading: Boolean = false,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    var query by remember { mutableStateOf("") }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    var showContextMenu by remember { mutableStateOf(false) }
    var selectedResult by remember { mutableStateOf<String?>(null) }
    var contextMenuPosition by remember { mutableStateOf<Int?>(null) }
    var isItemSelected by remember { mutableStateOf(false) } // Flag to prevent reopening after selection
    val focusManager = LocalFocusManager.current

    // Trigger search when query changes and has 3+ characters
    LaunchedEffect(query) {
        if (!isItemSelected) { // Only search if not from item selection
            if (query.length >= 3) {
                onSearch(query)
                isDropdownExpanded = true
            } else if (query.isEmpty()) {
                onSearch("")
                isDropdownExpanded = false
            }
        }
        isItemSelected = false // Reset the flag
    }

    Box(
        modifier = modifier
            .width(500.dp)
    ) {
        Column {
            // Search TextField
            Spacer(modifier = Modifier.height(50.dp))
            OutlinedTextField(
                value = query,
                onValueChange = { newQuery ->
                    query = newQuery
                    isDropdownExpanded = newQuery.isNotEmpty() && newQuery.length >= 3
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused && query.isNotEmpty() && query.length >= 3) {
                            isDropdownExpanded = true
                        }
                    },
                placeholder = {
                    Text("Search (min 3 characters)")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                query = ""
                                isDropdownExpanded = false
                                // Reset context menu state
                                showContextMenu = false
                                contextMenuPosition = null
                                selectedResult = null
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear"
                            )
                        }
                    } else {
                        Icon(
                            imageVector = if (isDropdownExpanded) Icons.Default.ArrowDropDown else Icons.Default.ArrowDropDown,
                            contentDescription = if (isDropdownExpanded) "Collapse" else "Expand"
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        if (query.length >= 3) {
                            onSearch(query)
                        }
                        focusManager.clearFocus()
                        isDropdownExpanded = false
                    }
                ),
                singleLine = true,
                shape = RoundedCornerShape(8.dp)
            )
        }

        // Dropdown Results
        if (isDropdownExpanded) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = 105.dp) // Position below the text field
                    .heightIn(max = 300.dp)
                    .zIndex(1f),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    when {
                        query.isNotEmpty() && query.length < 3 -> {
                            ListItem(
                                headlineContent = {
                                    Text(
                                        "Type at least 3 characters to search...",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            )
                        }

                        isLoading -> {
                            ListItem(
                                headlineContent = {
                                    Text(
                                        "Searching...",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            )
                        }

                        searchResults.isEmpty() && query.length >= 3 -> {
                            ListItem(
                                headlineContent = {
                                    Text(
                                        "No results found for '$query'",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            )
                        }

                        else -> {
                            searchResults.forEachIndexed { index, result ->
                                Box {
                                    ListItem(
                                        headlineContent = {
                                            Text(
                                                result,
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        },
                                        trailingContent = {
                                            IconButton(
                                                onClick = {
                                                    selectedResult = result
                                                    contextMenuPosition = index
                                                    showContextMenu = true
                                                }
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.MoreVert,
                                                    contentDescription = "More options"
                                                )
                                            }
                                        },
                                        modifier = Modifier
                                            .combinedClickable(
                                                onClick = {
                                                    // Set flag to prevent LaunchedEffect from reopening dropdown
                                                    isItemSelected = true

                                                    // Set the selected result as the search query
                                                    query = result

                                                    // Close dropdown and clear focus
                                                    isDropdownExpanded = false
                                                    focusManager.clearFocus()

                                                    // Reset all context menu states
                                                    showContextMenu = false
                                                    contextMenuPosition = null
                                                    selectedResult = null
                                                },
                                                onLongClick = {
                                                    selectedResult = result
                                                    contextMenuPosition = index
                                                    showContextMenu = true
                                                }
                                            )
                                            .fillMaxWidth()
                                    )

                                    // Context Menu
                                    DropdownMenu(
                                        expanded = showContextMenu && contextMenuPosition == index,
                                        onDismissRequest = {
                                            showContextMenu = false
                                            contextMenuPosition = null
                                            selectedResult = null
                                        },
                                        properties = PopupProperties(focusable = true)
                                    ) {
                                        DropdownMenuItem(
                                            text = { Text("Copy") },
                                            onClick = {
                                                // Handle copy action
                                                println("Copy clicked for: $selectedResult")
                                                showContextMenu = false
                                                contextMenuPosition = null
                                                selectedResult = null
                                            },
                                            leadingIcon = {
                                                Icon(
                                                    Icons.Default.Add,
                                                    contentDescription = "Copy"
                                                )
                                            }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("Share") },
                                            onClick = {
                                                // Handle share action
                                                println("Share clicked for: $selectedResult")
                                                showContextMenu = false
                                                contextMenuPosition = null
                                                selectedResult = null
                                            },
                                            leadingIcon = {
                                                Icon(
                                                    Icons.Default.Share,
                                                    contentDescription = "Share"
                                                )
                                            }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("Add to Favorites") },
                                            onClick = {
                                                // Handle add to favorites action
                                                println("Add to Favorites clicked for: $selectedResult")
                                                showContextMenu = false
                                                contextMenuPosition = null
                                                selectedResult = null
                                            },
                                            leadingIcon = {
                                                Icon(
                                                    Icons.Default.Favorite,
                                                    contentDescription = "Add to Favorites"
                                                )
                                            }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("Delete") },
                                            onClick = {
                                                // Handle delete action
                                                println("Delete clicked for: $selectedResult")
                                                showContextMenu = false
                                                contextMenuPosition = null
                                                selectedResult = null
                                            },
                                            leadingIcon = {
                                                Icon(
                                                    Icons.Default.Delete,
                                                    contentDescription = "Delete"
                                                )
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun Preview_DropdownSearchBarExample() {
    DropdownSearchBarExample()
}


/*

//This code is search with add selected item text in search list
package com.rul.compose_example.new_jetpack_compose.search_bar_16

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.zIndex

@Composable
fun DropdownSearchBarExample() {
    var searchResults by remember { mutableStateOf(listOf<String>()) }
    var isLoading by remember { mutableStateOf(false) }

    val onSearch: (String) -> Unit = { query ->
        if (query.length >= 3) {
            isLoading = true
            // Simulate search delay with more results to test scrolling
            searchResults = listOf(
                "Result 1 for '$query'",
                "Result 2 for '$query'",
                "Result 3 for '$query'",
                "Result 4 for '$query'",
                "Result 5 for '$query'",
                "Advanced result for '$query'",
                "Premium result for '$query'",
                "Extended result for '$query'",
                "Additional result for '$query'",
                "Extra result for '$query'",
                "More results for '$query'",
                "Final result for '$query'"
            )
            isLoading = false
        } else {
            searchResults = emptyList()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        DropdownSearchBar(
            onSearch = onSearch,
            searchResults = searchResults,
            isLoading = isLoading
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DropdownSearchBar(
    onSearch: (String) -> Unit,
    searchResults: List<String>,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
) {
    var query by remember { mutableStateOf("") }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    var showContextMenu by remember { mutableStateOf(false) }
    var selectedResult by remember { mutableStateOf<String?>(null) }
    var contextMenuPosition by remember { mutableStateOf<Int?>(null) }
    var skipLaunchedEffect by remember { mutableStateOf(false) } // Flag to skip LaunchedEffect
    val focusManager = LocalFocusManager.current

    // Trigger search when query changes and has 3+ characters
    LaunchedEffect(query) {
        if (!skipLaunchedEffect) { // Only run if not skipping
            if (query.length >= 3) {
                onSearch(query)
                isDropdownExpanded = true
            } else if (query.isEmpty()) {
                onSearch("")
                isDropdownExpanded = false
            }
        }
        skipLaunchedEffect = false // Reset the flag
    }

    Box(
        modifier = modifier
            .width(500.dp)
    ) {
        Column {
            // Search TextField
            Spacer(modifier = Modifier.height(50.dp))
            OutlinedTextField(
                value = query,
                onValueChange = { newQuery ->
                    query = newQuery
                    if (newQuery.isNotEmpty() && newQuery.length >= 3) {
                        isDropdownExpanded = true
                    } else {
                        isDropdownExpanded = false
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused && query.isNotEmpty() && query.length >= 3) {
                            isDropdownExpanded = true
                        }
                    },
                placeholder = {
                    Text("Search (min 3 characters)")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                query = ""
                                isDropdownExpanded = false
                                // Reset context menu state
                                showContextMenu = false
                                contextMenuPosition = null
                                selectedResult = null
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear"
                            )
                        }
                    } else {
                        Icon(
                            imageVector = if (isDropdownExpanded) Icons.Default.ArrowDropDown else Icons.Default.ArrowDropDown,
                            contentDescription = if (isDropdownExpanded) "Collapse" else "Expand"
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        if (query.length >= 3) {
                            onSearch(query)
                        }
                        focusManager.clearFocus()
                        isDropdownExpanded = false
                    }
                ),
                singleLine = true,
                shape = RoundedCornerShape(8.dp)
            )
        }

        // Dropdown Results
        if (isDropdownExpanded) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = 105.dp) // Position below the text field
                    .heightIn(max = 300.dp)
                    .zIndex(1f),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    when {
                        query.isNotEmpty() && query.length < 3 -> {
                            ListItem(
                                headlineContent = {
                                    Text(
                                        "Type at least 3 characters to search...",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            )
                        }

                        isLoading -> {
                            ListItem(
                                headlineContent = {
                                    Text(
                                        "Searching...",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            )
                        }

                        searchResults.isEmpty() && query.length >= 3 -> {
                            ListItem(
                                headlineContent = {
                                    Text(
                                        "No results found for '$query'",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            )
                        }

                        else -> {
                            searchResults.forEachIndexed { index, result ->
                                Box {
                                    ListItem(
                                        headlineContent = {
                                            Text(
                                                result,
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        },
                                        trailingContent = {
                                            IconButton(
                                                onClick = {
                                                    selectedResult = result
                                                    contextMenuPosition = index
                                                    showContextMenu = true
                                                }
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.MoreVert,
                                                    contentDescription = "More options"
                                                )
                                            }
                                        },
                                        modifier = Modifier
                                            .combinedClickable(
                                                onClick = {
                                                    // Set flag to skip LaunchedEffect when setting query
                                                    skipLaunchedEffect = true
                                                    query = result

                                                    // Close dropdown and clear focus
                                                    isDropdownExpanded = false
                                                    focusManager.clearFocus()

                                                    // Reset all context menu states
                                                    showContextMenu = false
                                                    contextMenuPosition = null
                                                    selectedResult = null
                                                },
                                                onLongClick = {
                                                    selectedResult = result
                                                    contextMenuPosition = index
                                                    showContextMenu = true
                                                }
                                            )
                                            .fillMaxWidth()
                                    )

                                    // Context Menu
                                    DropdownMenu(
                                        expanded = showContextMenu && contextMenuPosition == index,
                                        onDismissRequest = {
                                            showContextMenu = false
                                            contextMenuPosition = null
                                            selectedResult = null
                                        },
                                        properties = PopupProperties(focusable = true)
                                    ) {
                                        DropdownMenuItem(
                                            text = { Text("Copy") },
                                            onClick = {
                                                // Handle copy action
                                                println("Copy clicked for: $selectedResult")
                                                showContextMenu = false
                                                contextMenuPosition = null
                                                selectedResult = null
                                            },
                                            leadingIcon = {
                                                Icon(
                                                    Icons.Default.Add,
                                                    contentDescription = "Copy"
                                                )
                                            }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("Share") },
                                            onClick = {
                                                // Handle share action
                                                println("Share clicked for: $selectedResult")
                                                showContextMenu = false
                                                contextMenuPosition = null
                                                selectedResult = null
                                            },
                                            leadingIcon = {
                                                Icon(
                                                    Icons.Default.Share,
                                                    contentDescription = "Share"
                                                )
                                            }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("Add to Favorites") },
                                            onClick = {
                                                // Handle add to favorites action
                                                println("Add to Favorites clicked for: $selectedResult")
                                                showContextMenu = false
                                                contextMenuPosition = null
                                                selectedResult = null
                                            },
                                            leadingIcon = {
                                                Icon(
                                                    Icons.Default.Favorite,
                                                    contentDescription = "Add to Favorites"
                                                )
                                            }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("Delete") },
                                            onClick = {
                                                // Handle delete action
                                                println("Delete clicked for: $selectedResult")
                                                showContextMenu = false
                                                contextMenuPosition = null
                                                selectedResult = null
                                            },
                                            leadingIcon = {
                                                Icon(
                                                    Icons.Default.Delete,
                                                    contentDescription = "Delete"
                                                )
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun Preview_DropdownSearchBarExample() {
    DropdownSearchBarExample()
}*/
