package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.VarietiesData
import com.example.data.VarietyItem
import com.example.ui.theme.*

@Composable
fun VarietiesScreen(
    viewModel: MelaViewModel,
    modifier: Modifier = Modifier
) {
    val searchQuery by viewModel.varietySearchQuery.collectAsState()
    val selectedCategory by viewModel.selectedVarietyCategory.collectAsState()
    val filteredVarieties by viewModel.filteredVarieties.collectAsState()
    val context = LocalContext.current

    var selectedItemForInquiry by remember { mutableStateOf<VarietyItem?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("varieties_screen")
    ) {
        // Top Search Bar
        Surface(
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 2.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { viewModel.onSearchQueryChanged(it) },
                    placeholder = { Text("Search 60+ items (e.g. Crockery, Bags, Clocks...)") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { viewModel.onSearchQueryChanged("") }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear search")
                            }
                        }
                    },
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("varieties_search_field")
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Category Chips Row
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 4.dp)
                ) {
                    items(VarietiesData.categories) { category ->
                        val isSelected = category == selectedCategory
                        FilterChip(
                            selected = isSelected,
                            onClick = { viewModel.onCategorySelected(category) },
                            label = {
                                Text(
                                    text = category,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MelaCrimson,
                                selectedLabelColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }
                }
            }
        }

        // Sale Rates Legend strip
        Surface(
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Items available in shop:",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "${filteredVarieties.size} Varieties Found",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MelaCrimson
                )
            }
        }

        // List of Varieties
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 12.dp, bottom = 88.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(filteredVarieties, key = { it.id }) { item ->
                VarietyCard(
                    item = item,
                    onInquireClick = {
                        selectedItemForInquiry = item
                    }
                )
            }

            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Storefront,
                            contentDescription = null,
                            tint = MelaCrimson,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Looking for something specific?",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "We have hundreds more everyday household items inside our shop at Loharan Mandi Bazaar Pasrur. WhatsApp Sheikh Danish or Sheikh Hamza to confirm stock before visiting!",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }
    }

    // Inquiry Dialog for Selected Variety
    selectedItemForInquiry?.let { item ->
        AlertDialog(
            onDismissRequest = { selectedItemForInquiry = null },
            icon = {
                Icon(
                    imageVector = Icons.Default.Chat,
                    contentDescription = null,
                    tint = WhatsAppGreen,
                    modifier = Modifier.size(28.dp)
                )
            },
            title = {
                Text(
                    text = item.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            },
            text = {
                Column {
                    Text(
                        text = "Category: ${item.category}",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Sale Rates: ${item.priceTier}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MelaCrimson
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = item.description,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "Ask stock availability on WhatsApp (No phone calls):",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            val msg = "Assalam-o-Alaikum Sheikh Danish, I want to inquire about availability of '${item.name}' at Karachi Sale Mela Pasrur."
                            val url = "https://api.whatsapp.com/send?phone=${MelaConstants.PHONE_DANISH}&text=${android.net.Uri.encode(msg)}"
                            context.startActivity(android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(url)))
                            selectedItemForInquiry = null
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("WhatsApp Sheikh Danish")
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedButton(
                        onClick = {
                            val msg = "Assalam-o-Alaikum Sheikh Hamza, I want to inquire about availability of '${item.name}' at Karachi Sale Mela Pasrur."
                            val url = "https://api.whatsapp.com/send?phone=${MelaConstants.PHONE_HAMZA}&text=${android.net.Uri.encode(msg)}"
                            context.startActivity(android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(url)))
                            selectedItemForInquiry = null
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("WhatsApp Sheikh Hamza")
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { selectedItemForInquiry = null }) {
                    Text("Close")
                }
            }
        )
    }
}

@Composable
fun VarietyCard(
    item: VarietyItem,
    onInquireClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onInquireClick)
            .testTag("variety_card_${item.id}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon / Avatar based on category
            Surface(
                shape = CircleShape,
                color = when (item.category) {
                    "Kitchen & Crockery" -> MelaCrimson.copy(alpha = 0.12f)
                    "Bags & Pouches" -> MelaGold.copy(alpha = 0.12f)
                    "Toys & Kids" -> Color(0xFFE91E63).copy(alpha = 0.12f)
                    "Home Decor & Clocks" -> Color(0xFF673AB7).copy(alpha = 0.12f)
                    "Plastics & Household" -> Color(0xFF009688).copy(alpha = 0.12f)
                    "Cosmetics & Perfumes" -> Color(0xFFE040FB).copy(alpha = 0.12f)
                    else -> Color(0xFF3F51B5).copy(alpha = 0.12f)
                },
                modifier = Modifier.size(46.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = when (item.category) {
                            "Kitchen & Crockery" -> Icons.Default.Restaurant
                            "Bags & Pouches" -> Icons.Default.ShoppingBag
                            "Toys & Kids" -> Icons.Default.Toys
                            "Home Decor & Clocks" -> Icons.Default.WatchLater
                            "Plastics & Household" -> Icons.Default.Home
                            "Cosmetics & Perfumes" -> Icons.Default.AutoAwesome
                            else -> Icons.Default.Category
                        },
                        contentDescription = null,
                        tint = when (item.category) {
                            "Kitchen & Crockery" -> MelaCrimson
                            "Bags & Pouches" -> MelaGold
                            "Toys & Kids" -> Color(0xFFE91E63)
                            "Home Decor & Clocks" -> Color(0xFF673AB7)
                            "Plastics & Household" -> Color(0xFF009688)
                            "Cosmetics & Perfumes" -> Color(0xFFE040FB)
                            else -> Color(0xFF3F51B5)
                        },
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false)
                    )

                    Surface(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = item.priceTier,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = item.description,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 16.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = item.category,
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable(onClick = onInquireClick)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Chat,
                            contentDescription = null,
                            tint = WhatsAppGreen,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "WhatsApp Inquiry",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = WhatsAppGreenDark
                        )
                    }
                }
            }
        }
    }
}
