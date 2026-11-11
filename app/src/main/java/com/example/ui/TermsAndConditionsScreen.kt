package com.example.ui

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.viewmodel.KgmViewModel

data class TermSection(val title: String, val body: String)

fun parseTerms(content: String): List<TermSection> {
    val sections = mutableListOf<TermSection>()
    val lines = content.split("\n")
    var currentTitle = ""
    val currentBody = StringBuilder()
    
    for (line in lines) {
        val trimmed = line.trim()
        if (trimmed.isEmpty()) continue
        // Check if line starts with section pattern like "1. ", "12. ", etc.
        val isHeader = trimmed.any { it.isDigit() } && trimmed.contains(". ") && trimmed.substringBefore(". ").all { it.isDigit() }
        
        if (isHeader) {
            if (currentTitle.isNotEmpty()) {
                sections.add(TermSection(currentTitle, currentBody.toString().trim()))
                currentBody.clear()
            }
            currentTitle = trimmed
        } else {
            if (currentTitle.isEmpty()) {
                currentTitle = "General Information"
            }
            currentBody.append(line).append("\n")
        }
    }
    if (currentTitle.isNotEmpty()) {
        sections.add(TermSection(currentTitle, currentBody.toString().trim()))
    }
    return sections
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermsAndConditionsScreen(viewModel: KgmViewModel, readOnly: Boolean = true) {
    val currentTermsState by viewModel.currentTerms.collectAsStateWithLifecycle()
    var searchQuery by remember { mutableStateOf("") }
    
    val terms = currentTermsState ?: return Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }

    val sections = remember(terms.content) { parseTerms(terms.content) }
    
    // Track expanded state for each section index
    var expandedStates = remember(sections) { mutableStateMapOf<Int, Boolean>() }

    val filteredSections = remember(sections, searchQuery) {
        if (searchQuery.isBlank()) {
            sections
        } else {
            sections.filter { 
                it.title.contains(searchQuery, ignoreCase = true) || 
                it.body.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Terms & Conditions", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { viewModel.navigateBack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Header Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                                Color.Transparent
                            )
                        )
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer
                            )
                        ) {
                            Text(
                                text = "Version v${terms.version}",
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                        
                        Text(
                            text = "Effective: ${terms.effectiveDate}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "Please read KGM Travels rules and regulations carefully. Ensure full compliance to maintain secure school transit services.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    )
                }
            }

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .testTag("terms_search_input"),
                placeholder = { Text("Search transport rules, keywords...") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Filled.Clear, contentDescription = "Clear")
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Sections List
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                if (filteredSections.isEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 48.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = "No results",
                                tint = Color.Gray,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                "No sections matched your search query.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                items(filteredSections) { itemSection ->
                    val idx = sections.indexOf(itemSection)
                    val isExpanded = expandedStates[idx] ?: false

                    val sectionIcon = when {
                        itemSection.title.contains("Seat", ignoreCase = true) -> Icons.Filled.EventSeat
                        itemSection.title.contains("Payment", ignoreCase = true) || itemSection.title.contains("Fee", ignoreCase = true) -> Icons.Filled.Payments
                        itemSection.title.contains("Behavior", ignoreCase = true) || itemSection.title.contains("Discipline", ignoreCase = true) -> Icons.Filled.AccessibilityNew
                        itemSection.title.contains("Safety", ignoreCase = true) || itemSection.title.contains("Emergency", ignoreCase = true) -> Icons.Filled.Security
                        itemSection.title.contains("Cancellation", ignoreCase = true) -> Icons.Filled.Cancel
                        itemSection.title.contains("Notification", ignoreCase = true) -> Icons.Filled.NotificationsActive
                        else -> Icons.Filled.Gavel
                    }

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        border = BorderStroke(
                            width = 1.dp,
                            color = if (isExpanded) MaterialTheme.colorScheme.primary.copy(alpha = 0.5f) else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expandedStates[idx] = !isExpanded }
                            .testTag("terms_section_card_$idx")
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .background(
                                            color = if (isExpanded) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
                                            shape = RoundedCornerShape(8.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = sectionIcon,
                                        contentDescription = "Section Icon",
                                        tint = if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Text(
                                    text = itemSection.title,
                                    modifier = Modifier.weight(1f),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                )

                                Icon(
                                    imageVector = if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                                    contentDescription = "Toggle Section",
                                    tint = Color.Gray
                                )
                            }

                            AnimatedVisibility(
                                visible = isExpanded,
                                enter = expandVertically() + fadeIn(),
                                exit = shrinkVertically() + fadeOut()
                            ) {
                                Column {
                                    Spacer(modifier = Modifier.height(12.dp))
                                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(
                                        text = itemSection.body,
                                        style = MaterialTheme.typography.bodyMedium,
                                        lineHeight = 22.sp,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
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
