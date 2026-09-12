package com.example.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.data.VarietiesData
import com.example.data.VarietyItem
import com.example.ui.theme.*

@Composable
fun SearchScreen(
    viewModel: MelaViewModel,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }
    val context = LocalContext.current
    val customPictures by viewModel.customItemPictures.collectAsState()

    val quickFilters = listOf("All", "Jewellers", "Cosmatics", "Toys", "Plastic & Crockery", "Steel Items", "Rs. 120", "Rs. 300", "Rs. 600", "Rs. 1,200")

    val searchResults = remember(searchQuery, selectedFilter) {
        VarietiesData.allVarieties.filter { item ->
            val matchesFilter = when (selectedFilter) {
                "All" -> true
                "Jewellers" -> item.category.contains("Jewel", ignoreCase = true)
                "Cosmatics" -> item.category.contains("Cosmetic", ignoreCase = true) || item.category.contains("Perfume", ignoreCase = true)
                "Toys" -> item.category.contains("Toy", ignoreCase = true)
                "Plastic & Crockery" -> item.category.contains("Plastic", ignoreCase = true) || item.category.contains("Crockery", ignoreCase = true)
                "Steel Items" -> item.category.contains("Crockery", ignoreCase = true) || item.name.contains("Steel", ignoreCase = true)
                else -> item.priceTier.contains(selectedFilter, ignoreCase = true)
            }
            val matchesQuery = searchQuery.isBlank() ||
                    item.name.contains(searchQuery, ignoreCase = true) ||
                    item.category.contains(searchQuery, ignoreCase = true) ||
                    item.description.contains(searchQuery, ignoreCase = true) ||
                    item.priceTier.contains(searchQuery, ignoreCase = true)
            matchesFilter && matchesQuery
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MelaBackgroundLight)
            .testTag("search_screen")
    ) {
        // Search Header Card
        Surface(
            color = MelaTopBarGold,
            shadowElevation = 4.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp)
            ) {
                Text(
                    text = "SEARCH PRODUCTS & VARIETIES",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF211400)
                )
                Text(
                    text = "Search across 60+ varieties, categories, and fixed rate deals",
                    fontSize = 11.sp,
                    color = Color(0xFF4A3B2C)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Search Bar Pill
                Surface(
                    shape = RoundedCornerShape(30.dp),
                    color = Color.White,
                    shadowElevation = 3.dp,
                    border = androidx.compose.foundation.BorderStroke(1.dp, MelaGoldBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Search for products...", color = Color.Gray, fontSize = 14.sp) },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                        if (searchQuery.isNotBlank()) {
                            IconButton(
                                onClick = { searchQuery = "" },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear", tint = Color.Gray, modifier = Modifier.size(16.dp))
                            }
                        }
                        Surface(
                            shape = CircleShape,
                            color = MelaCrimson,
                            modifier = Modifier.size(34.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Submit Search",
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Quick Filters Row
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(quickFilters) { filter ->
                val isSelected = selectedFilter == filter
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedFilter = filter },
                    label = { Text(filter, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MelaBurgundy,
                        selectedLabelColor = MelaGoldLight,
                        containerColor = Color.White,
                        labelColor = Color(0xFF211400)
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        borderColor = if (isSelected) MelaBurgundy else MelaGoldBorder,
                        enabled = true,
                        selected = isSelected
                    )
                )
            }
        }

        // Search Results List
        if (searchResults.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.SearchOff,
                        contentDescription = null,
                        tint = MelaBurgundy.copy(alpha = 0.5f),
                        modifier = Modifier.size(56.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "No varieties found for \"$searchQuery\"",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF211400),
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Try searching for Jewellery, Crockery, Plastic, Toys, or Rs. 300",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        } else {
            Text(
                text = "Found ${searchResults.size} Varieties",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MelaBurgundy,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )

            LazyColumn(
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(searchResults, key = { it.id }) { item ->
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MelaGoldBorder.copy(alpha = 0.6f)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Thumbnail
                            val customPic = customPictures[item.id]
                            val imageModel = if (!customPic.isNullOrBlank()) {
                                resolveVarietyImageModel(customPic, item.category)
                            } else {
                                resolveVarietyImageModel(null, item.category)
                            }

                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.size(68.dp)
                            ) {
                                AsyncImage(
                                    model = imageModel,
                                    contentDescription = item.name,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = item.name,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = Color(0xFF211400),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Surface(
                                        color = when (item.priceTier) {
                                            "Rs. 120" -> Rate120
                                            "Rs. 300" -> Rate300
                                            "Rs. 600" -> Rate600
                                            else -> Rate1200
                                        },
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            text = item.priceTier,
                                            color = Color.White,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = item.category,
                                    fontSize = 11.sp,
                                    color = MelaGoldPrimary,
                                    fontWeight = FontWeight.Medium
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = item.description,
                                    fontSize = 11.sp,
                                    color = Color(0xFF554440),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            // WhatsApp Inquiry button
                            IconButton(
                                onClick = {
                                    val msg = "Assalam-o-Alaikum Sheikh Danish, I saw *${item.name}* (${item.priceTier}) on the Karachi Sale Mela app and want to check stock."
                                    val url = "https://api.whatsapp.com/send?phone=${MelaConstants.PHONE_DANISH}&text=${Uri.encode(msg)}"
                                    try {
                                        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                                    } catch (_: Exception) {}
                                },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Chat,
                                    contentDescription = "WhatsApp Inquiry",
                                    tint = WhatsAppGreen,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
