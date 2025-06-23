package com.rul.compose_example.new_jetpack_compose.search_bar_16

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults.InputField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchBarExample() {
    val textFieldState = remember { TextFieldState() }
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
        ImprovedSearchBar(
            textFieldState = textFieldState,
            onSearch = onSearch,
            searchResults = searchResults,
            isLoading = isLoading
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImprovedSearchBar(
    textFieldState: TextFieldState,
    onSearch: (String) -> Unit,
    searchResults: List<String>,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }

    // Trigger search when query changes and has 3+ characters
    LaunchedEffect(query) {
        if (query.length >= 3) {
            onSearch(query)
        } else if (query.isEmpty()) {
            // Clear results when query is empty
            onSearch("")
        }
    }

    Box(
        modifier = modifier
            .semantics { isTraversalGroup = true }
    ) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .semantics { isTraversalGroup = true },
            inputField = {
                InputField(
                    query = query,
                    onQueryChange = { newQuery ->
                        query = newQuery
                        textFieldState.edit {
                            replace(0, length, newQuery)
                        }
                        // Auto-expand when typing, collapse when empty
                        expanded = newQuery.isNotEmpty()
                    },
                    onSearch = {
                        if (query.length >= 3) {
                            onSearch(query)
                        }
                        expanded = false
                    },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
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
                                    textFieldState.edit {
                                        replace(0, length, "")
                                    }
                                    expanded = false
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear"
                                )
                            }
                        }
                    }
                )
            },
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 300.dp) // Limit maximum height to 300dp
                    .verticalScroll(rememberScrollState())
            ) {
                when {
                    query.isNotEmpty() && query.length < 3 -> {
                        ListItem(
                            headlineContent = {
                                Text("Type at least 3 characters to search...")
                            }
                        )
                    }
                    isLoading -> {
                        ListItem(
                            headlineContent = {
                                Text("Searching...")
                            }
                        )
                    }
                    searchResults.isEmpty() && query.length >= 3 -> {
                        ListItem(
                            headlineContent = {
                                Text("No results found for '$query'")
                            }
                        )
                    }
                    else -> {
                        searchResults.forEach { result ->
                            ListItem(
                                headlineContent = {
                                    Text(result)
                                },
                                modifier = Modifier
                                    .clickable {
                                        query = result
                                        textFieldState.edit {
                                            replace(0, length, result)
                                        }
                                        expanded = false
                                    }
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun Preview_SearchBarExample() {
    SearchBarExample()
}
