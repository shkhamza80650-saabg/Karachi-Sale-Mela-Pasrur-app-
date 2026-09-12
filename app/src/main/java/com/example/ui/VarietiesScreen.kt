package com.example.ui

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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

fun categoryToDefaultDrawable(category: String): Int {
    val catLower = category.lowercase()
    return when {
        catLower.contains("cosmetic") || catLower.contains("perfume") -> R.drawable.img_cosmetics_items
        catLower.contains("hosiery") || catLower.contains("textile") || catLower.contains("linen") -> R.drawable.img_hosiery_items
        catLower.contains("plastic") || catLower.contains("household") -> R.drawable.img_plastic_items
        catLower.contains("bag") || catLower.contains("pouch") -> R.drawable.img_bags_items
        catLower.contains("toy") || catLower.contains("kid") -> R.drawable.img_toys_items
        catLower.contains("decor") || catLower.contains("clock") -> R.drawable.img_clocks_decor
        catLower.contains("crockery") || catLower.contains("kitchen") -> R.drawable.img_crockery_items
        catLower.contains("jewel") -> R.drawable.img_jewellery_collection
        else -> R.drawable.img_crockery_items
    }
}

fun resolveVarietyImageModel(imageNameOrUri: String?, category: String): Any {
    if (imageNameOrUri.isNullOrBlank()) {
        return categoryToDefaultDrawable(category)
    }
    if (imageNameOrUri.startsWith("content://") || imageNameOrUri.startsWith("file://")) {
        return Uri.parse(imageNameOrUri)
    }
    return when (imageNameOrUri) {
        "img_plastic_items" -> R.drawable.img_plastic_items
        "img_hosiery_items" -> R.drawable.img_hosiery_items
        "img_cosmetics_items" -> R.drawable.img_cosmetics_items
        "img_crockery_items" -> R.drawable.img_crockery_items
        "img_jewellery_collection" -> R.drawable.img_jewellery_collection
        "img_bags_items" -> R.drawable.img_bags_items
        "img_toys_items" -> R.drawable.img_toys_items
        "img_clocks_decor" -> R.drawable.img_clocks_decor
        "img_shop_front" -> R.drawable.img_shop_front
        "img_proprietor_counter" -> R.drawable.img_proprietor_counter
        "img_sale_banner" -> R.drawable.img_sale_banner
        "img_shop_bazaar_view" -> R.drawable.img_shop_bazaar_view
        "img_shop_interior" -> R.drawable.img_shop_interior
        "img_shop_poster" -> R.drawable.img_shop_poster
        "img_shop_signboard" -> R.drawable.img_shop_signboard
        else -> categoryToDefaultDrawable(category)
    }
}

@Composable
fun VarietiesScreen(
    viewModel: MelaViewModel,
    modifier: Modifier = Modifier
) {
    val searchQuery by viewModel.varietySearchQuery.collectAsState()
    val selectedCategory by viewModel.selectedVarietyCategory.collectAsState()
    val filteredVarieties by viewModel.filteredVarieties.collectAsState()
    val customPictures by viewModel.customItemPictures.collectAsState()
    val context = LocalContext.current

    var selectedItemForInquiry by remember { mutableStateOf<VarietyItem?>(null) }
    var itemForPhotoUpload by remember { mutableStateOf<VarietyItem?>(null) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null && itemForPhotoUpload != null) {
            val item = itemForPhotoUpload!!
            viewModel.updateItemPicture(item.id, uri.toString())
            Toast.makeText(context, "تصویر کامیابی سے اپ لوڈ ہو گئی • Picture uploaded for ${item.name}!", Toast.LENGTH_SHORT).show()
            itemForPhotoUpload = null
        }
    }

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
                    placeholder = { Text("Search 60+ items (e.g. Jewellery, Crockery...)") },
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
                    text = "Items with Real Photos:",
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
                val currentImage = customPictures[item.id] ?: item.defaultImageResName
                VarietyCard(
                    item = item,
                    imageModel = resolveVarietyImageModel(currentImage, item.category),
                    hasCustomPhoto = customPictures.containsKey(item.id),
                    onInquireClick = {
                        selectedItemForInquiry = item
                    },
                    onUploadPhotoClick = {
                        itemForPhotoUpload = item
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

    // Photo Upload Dialog for Selected Item
    itemForPhotoUpload?.let { item ->
        AlertDialog(
            onDismissRequest = { itemForPhotoUpload = null },
            icon = {
                Icon(
                    imageVector = Icons.Default.AddPhotoAlternate,
                    contentDescription = null,
                    tint = MelaCrimson,
                    modifier = Modifier.size(28.dp)
                )
            },
            title = {
                Text(
                    text = "Upload Picture for Item",
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = item.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Upload a real photo from your phone or choose a shop photo preset:",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Button(
                        onClick = {
                            photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MelaCrimson),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.PhotoCamera, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Pick Photo from Gallery")
                    }

                    Text("Shop Collection Presets:", fontSize = 12.sp, fontWeight = FontWeight.Bold)

                    val presets = listOf(
                        Pair("🪣 Plastic Crockery & Items (پلاسٹک کے برتن)", "img_plastic_items"),
                        Pair("🧦 Hosiery & Cotton Wear (ہوزری اور جرابیں)", "img_hosiery_items"),
                        Pair("💄 Cosmetics & Fragrances (کاسمیٹکس اور پرفیومز)", "img_cosmetics_items"),
                        Pair("🍽️ Kitchen & Crockery (کروکری اور ڈنر سیٹ)", "img_crockery_items"),
                        Pair("💎 Jewellery & Accessories (زیورات اور جیولری)", "img_jewellery_collection"),
                        Pair("👜 Handbags & Pouches (بیگز اور پرس)", "img_bags_items"),
                        Pair("🧸 Toys & Teddy Bears (کھلونے اور تحائف)", "img_toys_items"),
                        Pair("🕰️ Wall Clocks & Decor (وال کلاک اور ڈیکوریشن)", "img_clocks_decor")
                    )

                    presets.forEach { (label, key) ->
                        OutlinedButton(
                            onClick = {
                                viewModel.updateItemPicture(item.id, key)
                                Toast.makeText(context, "تصویر کامیابی سے تبدیل ہو گئی • Photo updated for ${item.name}!", Toast.LENGTH_SHORT).show()
                                itemForPhotoUpload = null
                            },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(label, fontSize = 12.sp)
                        }
                    }

                    if (customPictures.containsKey(item.id)) {
                        TextButton(
                            onClick = {
                                viewModel.removeItemPicture(item.id)
                                Toast.makeText(context, "تصویر ری سیٹ ہو گئی • Reset to default photo", Toast.LENGTH_SHORT).show()
                                itemForPhotoUpload = null
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Reset to Default Photo", color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { itemForPhotoUpload = null }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Inquiry Dialog for Selected Variety
    selectedItemForInquiry?.let { item ->
        val currentImage = customPictures[item.id] ?: item.defaultImageResName
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
                    // Item photo in inquiry
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .clip(RoundedCornerShape(12.dp))
                    ) {
                        AsyncImage(
                            model = resolveVarietyImageModel(currentImage, item.category),
                            contentDescription = item.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

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
    imageModel: Any,
    hasCustomPhoto: Boolean,
    onInquireClick: () -> Unit,
    onUploadPhotoClick: () -> Unit
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
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product photo thumbnail with upload overlay
            Box(
                modifier = Modifier
                    .size(76.dp)
                    .clip(RoundedCornerShape(14.dp))
            ) {
                AsyncImage(
                    model = imageModel,
                    contentDescription = item.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Quick camera upload button on corner of thumbnail
                Surface(
                    shape = CircleShape,
                    color = Color.Black.copy(alpha = 0.55f),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(3.dp)
                        .size(24.dp)
                        .clickable(onClick = onUploadPhotoClick)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.AddPhotoAlternate,
                            contentDescription = "Upload Picture",
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                    }
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
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
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

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable(onClick = onUploadPhotoClick)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = null,
                            tint = MelaCrimson,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = if (hasCustomPhoto) "Change Photo" else "Upload Photo",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MelaCrimson
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable(onClick = onInquireClick)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Chat,
                            contentDescription = null,
                            tint = WhatsAppGreen,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "Inquire",
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
