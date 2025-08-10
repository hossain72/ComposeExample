import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.text.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties


/**
 * A reusable dropdown text field with search functionality
 * Similar to Flutter's dropdown_search package
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> DropdownSearchField(
    items: List<T>,
    selectedItem: T? = null,
    onItemSelected: (T) -> Unit,
    onCloseBtnClicked: () -> Unit = {},
    modifier: Modifier = Modifier,
    placeholder: String = "Select an item",
    searchEnabled: Boolean = true,
    searchPlaceholder: String = "Search...",
    itemToString: (T) -> String = { it.toString() },
    enabled: Boolean = true,
    isError: Boolean = false,
    errorMessage: String? = null,
    maxDropdownHeight: androidx.compose.ui.unit.Dp = 200.dp,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors()
) {
    var expanded by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    // Update text field when selected item changes
    LaunchedEffect(selectedItem) {
        val newText = selectedItem?.let(itemToString) ?: ""
        textFieldValue = TextFieldValue(newText)
        searchText = ""
    }

    // Filter items based on search text
    val filteredItems = remember(items, searchText) {
        if (searchEnabled && searchText.isNotEmpty()) {
            items.filter {
                itemToString(it).contains(searchText, ignoreCase = true)
            }
        } else {
            items
        }
    }

    // Close dropdown function
    val closeDropdown = {
        expanded = false
        focusManager.clearFocus()
        keyboardController?.hide()
        searchText = ""
        // Reset to display selected item text
        textFieldValue = TextFieldValue(selectedItem?.let(itemToString) ?: "")
    }

    // Use ExposedDropdownMenuBox for proper overlay behavior
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { newExpanded ->
            if (searchEnabled) {
                expanded = newExpanded
                if (!newExpanded) {
                    closeDropdown()
                }
            } else {
                // For non-searchable dropdowns, handle click to expand
                expanded = newExpanded
                if (!newExpanded) {
                    closeDropdown()
                }
            }
        },
        modifier = modifier
    ) {
        // Main TextField
        Column {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 0.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = MaterialTheme.shapes.small
            ) {
                OutlinedTextField(
                    value = textFieldValue,
                    onValueChange = { newValue ->
                        if (searchEnabled) {
                            textFieldValue = newValue
                            if (!expanded) {
                                expanded = true
                            }
                            searchText = newValue.text
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(
                            MenuAnchorType.PrimaryEditable,
                            enabled
                        ) // This is important for positioning
                        .focusRequester(focusRequester)
                        .clickable(
                            enabled = !searchEnabled,
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            if (!searchEnabled) {
                                expanded = !expanded
                            }
                        }
                        .onFocusChanged { focusState ->
                            if (searchEnabled && focusState.isFocused && !expanded) {
                                expanded = true
                                // Clear text for search when focused
                                searchText = ""
                                textFieldValue = TextFieldValue("")
                            }
                        },
                    placeholder = {
                        Text(
                            if (expanded && searchEnabled && textFieldValue.text.isEmpty())
                                searchPlaceholder
                            else
                                placeholder
                        )
                    },
                    trailingIcon = {
                        Row {
                            if (expanded && searchEnabled && textFieldValue.text.isNotEmpty()) {
                                IconButton(
                                    onClick = {
                                        onCloseBtnClicked()
                                        searchText = ""
                                        textFieldValue = TextFieldValue("")
                                    }
                                ) {
                                    Icon(
                                        Icons.Default.Clear,
                                        contentDescription = "Clear search"
                                    )
                                }
                            }

                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        }
                    },
                    enabled = enabled,
                    readOnly = false,
                    isError = false,
                    colors = colors,
                    keyboardOptions = if (searchEnabled) {
                        KeyboardOptions(imeAction = ImeAction.Done)
                    } else {
                        KeyboardOptions.Default
                    },
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        }
                    ),
                    singleLine = true
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            if (isError && !errorMessage.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 0.dp)
                )
            }
        }

        // Dropdown Menu - This will now overlay properly
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { closeDropdown() },
            modifier = Modifier.heightIn(max = maxDropdownHeight)
        ) {
            if (filteredItems.isEmpty()) {
                // No items found
                DropdownMenuItem(
                    text = {
                        Text(
                            "No items found",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    onClick = { },
                    enabled = false
                )
            } else {
                // Show filtered items
                filteredItems.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = itemToString(item),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        onClick = {
                            onItemSelected(item)
                            // Set the selected item text and close dropdown
                            textFieldValue = TextFieldValue(itemToString(item))
                            closeDropdown()
                        }
                    )
                }
            }
        }
    }
}

// Data class for demonstration
data class Country(
    val name: String,
    val code: String
)

// Usage Example
@Composable
fun DropdownSearchExample() {
    val countries = listOf(
        Country("United States", "US"),
        Country("Canada", "CA"),
        Country("United Kingdom", "UK"),
        Country("Germany", "DE"),
        Country("France", "FR"),
        Country("Japan", "JP"),
        Country("Australia", "AU"),
        Country("Brazil", "BR"),
        Country("India", "IN"),
        Country("China", "CN")
    )

    var selectedCountry by remember { mutableStateOf<Country?>(null) }
    var selectedSimpleItem by remember { mutableStateOf<String?>(null) }

    val simpleItems = listOf(
        "Apple", "Banana", "Cherry", "Date", "Elderberry",
        "Fig", "Grape", "Honeydew", "Kiwi", "Lemon"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "Dropdown Search Examples",
            style = MaterialTheme.typography.headlineMedium
        )

        // Example 1: With search enabled (default)
        DropdownSearchField(
            items = countries,
            selectedItem = selectedCountry,
            onItemSelected = { selectedCountry = it },
            onCloseBtnClicked = {
                selectedCountry = null
            },
            placeholder = "Choose a country",
            searchEnabled = true,
            itemToString = { "${it.name} (${it.code})" },
            modifier = Modifier.fillMaxWidth(),
            isError = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                errorBorderColor = Color.Transparent,
                cursorColor = Color(0xFF2196F3), // primary color equivalent
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
        )

        // Example 2: Simple string list with search disabled
        DropdownSearchField(
            items = simpleItems,
            selectedItem = selectedSimpleItem,
            onItemSelected = { selectedSimpleItem = it },
            placeholder = "Choose a fruit",
            searchEnabled = false,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                errorBorderColor = Color.Transparent,
                cursorColor = Color(0xFF2196F3), // primary color equivalent
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
        )

        // Example 3: With custom styling and error state
        DropdownSearchField(
            items = simpleItems,
            selectedItem = selectedSimpleItem,
            onItemSelected = { selectedSimpleItem = it },
            placeholder = "This field is required",
            searchEnabled = true,
            isError = true,
            errorMessage = if (selectedSimpleItem == null) "Please select an item" else null,
            maxDropdownHeight = 150.dp,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                errorBorderColor = Color.Transparent,
                cursorColor = Color(0xFF2196F3), // primary color equivalent
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
        )

        // Display selected values
        if (selectedCountry != null) {
            Text("Selected Country: ${selectedCountry!!.name}")
        }

        if (selectedSimpleItem != null) {
            Text("Selected Fruit: $selectedSimpleItem")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun Preview_DropdownSearchExample() {
    DropdownSearchExample()
}