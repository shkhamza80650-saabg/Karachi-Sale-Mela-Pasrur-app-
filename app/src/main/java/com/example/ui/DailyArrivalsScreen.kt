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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.data.DailyArrivalEntity
import com.example.ui.theme.*

fun categoryToArrivalDrawable(category: String): Int {
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

fun resolveArrivalImageModel(imageResName: String, category: String = ""): Any {
    return when {
        imageResName.startsWith("content://") || imageResName.startsWith("file://") -> {
            Uri.parse(imageResName)
        }
        imageResName == "img_plastic_items" -> R.drawable.img_plastic_items
        imageResName == "img_hosiery_items" -> R.drawable.img_hosiery_items
        imageResName == "img_cosmetics_items" -> R.drawable.img_cosmetics_items
        imageResName == "img_crockery_items" -> R.drawable.img_crockery_items
        imageResName == "img_jewellery_collection" -> R.drawable.img_jewellery_collection
        imageResName == "img_bags_items" -> R.drawable.img_bags_items
        imageResName == "img_toys_items" -> R.drawable.img_toys_items
        imageResName == "img_clocks_decor" -> R.drawable.img_clocks_decor
        imageResName == "img_shop_front" -> R.drawable.img_shop_front
        imageResName == "img_proprietor_counter" -> R.drawable.img_proprietor_counter
        imageResName == "img_sale_banner" -> R.drawable.img_sale_banner
        imageResName == "img_shop_bazaar_view" -> R.drawable.img_shop_bazaar_view
        imageResName == "img_shop_interior" -> {
            if (category.isNotBlank()) categoryToArrivalDrawable(category) else R.drawable.img_shop_interior
        }
        else -> categoryToArrivalDrawable(category)
    }
}

@Composable
fun DailyArrivalsScreen(
    viewModel: MelaViewModel,
    dailyArrivals: List<DailyArrivalEntity>,
    modifier: Modifier = Modifier
) {
    val showAddDialog by viewModel.showAddArrivalDialog.collectAsState()
    val context = LocalContext.current

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { viewModel.setAddArrivalDialogVisible(true) },
                icon = { Icon(Icons.Default.AddPhotoAlternate, contentDescription = "Add New Arrival") },
                text = { Text("Upload New Arrival", fontWeight = FontWeight.Bold) },
                containerColor = MelaCrimson,
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .padding(bottom = 64.dp)
                    .testTag("add_arrival_fab")
            )
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .testTag("daily_arrivals_screen"),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header Card
            item {
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = MelaCrimson,
                            modifier = Modifier.size(48.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.NewReleases,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column {
                            Text(
                                text = "Daily New Varieties & Arrivals",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Real daily stock photos from Loharan Mandi Bazaar Pasrur. Tap 'Upload New Arrival' or tap camera on any card to update photos!",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f),
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }

            if (dailyArrivals.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.Inventory2,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.outline,
                                modifier = Modifier.size(64.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "No daily arrivals listed yet",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Tap 'Upload New Arrival' below to post today's stock!",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }
            } else {
                items(dailyArrivals, key = { it.id }) { arrival ->
                    DailyArrivalCard(
                        arrival = arrival,
                        onWhatsAppInquiry = {
                            val msg = "Assalam-o-Alaikum, I saw '${arrival.title}' (${arrival.priceTier}) on the Karachi Sale Mela Pasrur App Daily Arrivals. Is it in stock today?"
                            val url = "https://api.whatsapp.com/send?phone=${MelaConstants.PHONE_DANISH}&text=${android.net.Uri.encode(msg)}"
                            context.startActivity(android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(url)))
                        },
                        onDelete = {
                            viewModel.removeArrival(arrival.id)
                        },
                        onUpdateImage = { newImage ->
                            viewModel.updateArrivalImage(arrival.id, newImage)
                            Toast.makeText(context, "تصویر اپ ڈیٹ ہو گئی • Photo updated successfully!", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(60.dp))
            }
        }
    }

    // Add New Arrival Dialog
    if (showAddDialog) {
        AddArrivalDialog(
            onDismiss = { viewModel.setAddArrivalDialogVisible(false) },
            onConfirm = { title, category, priceTier, date, desc, imageResName ->
                viewModel.addNewArrival(title, category, priceTier, date, desc, imageResName)
                Toast.makeText(context, "نئی ورائٹی اور تصویر اپ لوڈ ہو گئی • New arrival posted!", Toast.LENGTH_SHORT).show()
            }
        )
    }
}

@Composable
fun DailyArrivalCard(
    arrival: DailyArrivalEntity,
    onWhatsAppInquiry: () -> Unit,
    onDelete: () -> Unit,
    onUpdateImage: (String) -> Unit
) {
    var showChangePhotoDialog by remember { mutableStateOf(false) }
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            onUpdateImage(uri.toString())
            showChangePhotoDialog = false
        }
    }

    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("arrival_card_${arrival.id}")
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
            ) {
                AsyncImage(
                    model = resolveArrivalImageModel(arrival.imageResName, arrival.category),
                    contentDescription = arrival.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Arrival Date Badge (Top Start)
                Surface(
                    color = MelaCrimson,
                    shape = RoundedCornerShape(bottomEnd = 12.dp),
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.EventAvailable,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = arrival.arrivalDate,
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Price Badge (Top End)
                Surface(
                    color = Rate300,
                    shape = RoundedCornerShape(bottomStart = 12.dp),
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Text(
                        text = arrival.priceTier,
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                    )
                }

                // Upload / Change Photo Floating Action (Bottom End)
                Surface(
                    color = Color.Black.copy(alpha = 0.65f),
                    shape = RoundedCornerShape(topStart = 12.dp),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .clickable { showChangePhotoDialog = true }
                        .testTag("change_photo_btn_${arrival.id}")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddPhotoAlternate,
                            contentDescription = "Change / Upload Picture",
                            tint = Color.White,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Upload / Change Photo",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = arrival.category,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }

                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.DeleteOutline,
                            contentDescription = "Remove item",
                            tint = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = arrival.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = arrival.description,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                // WhatsApp Inquiry button
                Button(
                    onClick = onWhatsAppInquiry,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WhatsAppGreen,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Chat,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "WhatsApp Inquiry for this Item",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    if (showChangePhotoDialog) {
        AlertDialog(
            onDismissRequest = { showChangePhotoDialog = false },
            title = {
                Text(
                    text = "Upload / Select Item Photo",
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Upload a photo from your phone or choose a shop collection photo:",
                        fontSize = 13.sp,
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
                        Icon(Icons.Default.PhotoLibrary, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Pick Photo from Phone Gallery")
                    }

                    Text("Shop Collection Presets:", fontSize = 12.sp, fontWeight = FontWeight.Bold)

                    val presets = listOf(
                        Pair("💎 Jewellery & Bridal Set", "img_jewellery_collection"),
                        Pair("🍽️ Crockery & Dinnerware", "img_crockery_items"),
                        Pair("💄 Cosmetics & Perfumes", "img_cosmetics_items"),
                        Pair("🏬 Shop Entrance & Front", "img_shop_front"),
                        Pair("🛒 Store Aisles & Racks", "img_shop_interior")
                    )

                    presets.forEach { (label, key) ->
                        OutlinedButton(
                            onClick = {
                                onUpdateImage(key)
                                showChangePhotoDialog = false
                            },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(label, fontSize = 12.sp)
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showChangePhotoDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun AddArrivalDialog(
    onDismiss: () -> Unit,
    onConfirm: (title: String, category: String, priceTier: String, date: String, desc: String, imageResName: String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Jewellery & Accessories") }
    var priceTier by remember { mutableStateOf("Rs. 300") }
    var arrivalDate by remember { mutableStateOf("Today's Arrival") }
    var description by remember { mutableStateOf("") }
    var selectedImageUriOrRes by remember { mutableStateOf("img_jewellery_collection") }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            selectedImageUriOrRes = uri.toString()
        }
    }

    val categories = listOf(
        "Plastics & Household",
        "Cosmetics & Perfumes",
        "Textiles & Linens",
        "Kitchen & Crockery",
        "Jewellery & Accessories",
        "Bags & Pouches",
        "Toys & Kids",
        "Home Decor & Clocks"
    )
    val rates = listOf("Rs. 120", "Rs. 300", "Rs. 600", "Rs. 1,200")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Upload Daily New Arrival",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        },
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Item Name / Variety") },
                        placeholder = { Text("e.g. Plastic Crockery / Hosiery / Cosmetics") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    Text("Picture for this Item:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.LightGray.copy(alpha = 0.2f))
                    ) {
                        AsyncImage(
                            model = resolveArrivalImageModel(selectedImageUriOrRes, category),
                            contentDescription = "Selected Picture",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MelaCrimson),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.AddPhotoAlternate, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Pick from Gallery", fontSize = 11.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text("Or select category photo preset:", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val presets = listOf(
                            Pair("🪣 Plastic (پلاسٹک کے برتن)", "img_plastic_items"),
                            Pair("🧦 Hosiery (ہوزری اور جرابیں)", "img_hosiery_items"),
                            Pair("💄 Cosmetics (کاسمیٹکس)", "img_cosmetics_items"),
                            Pair("🍽️ Crockery (کروکری)", "img_crockery_items"),
                            Pair("💎 Jewellery (زیورات)", "img_jewellery_collection"),
                            Pair("👜 Bags (بیگز)", "img_bags_items"),
                            Pair("🧸 Toys (کھلونے)", "img_toys_items"),
                            Pair("🕰️ Clocks (وال کلاک)", "img_clocks_decor")
                        )
                        items(presets) { (name, key) ->
                            FilterChip(
                                selected = selectedImageUriOrRes == key,
                                onClick = { selectedImageUriOrRes = key },
                                label = { Text(name, fontSize = 10.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MelaCrimson,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }
                }

                item {
                    Text("Sale Rate:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        rates.forEach { rate ->
                            val isSelected = rate == priceTier
                            FilterChip(
                                selected = isSelected,
                                onClick = { priceTier = rate },
                                label = { Text(rate, fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MelaCrimson,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }
                }

                item {
                    Text("Category:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    var expanded by remember { mutableStateOf(false) }
                    Box {
                        OutlinedButton(
                            onClick = { expanded = true },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(category, fontSize = 12.sp)
                            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                        }
                        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                            categories.forEach { cat ->
                                DropdownMenuItem(
                                    text = { Text(cat) },
                                    onClick = {
                                        category = cat
                                        when (cat) {
                                            "Plastics & Household" -> selectedImageUriOrRes = "img_plastic_items"
                                            "Textiles & Linens" -> selectedImageUriOrRes = "img_hosiery_items"
                                            "Cosmetics & Perfumes" -> selectedImageUriOrRes = "img_cosmetics_items"
                                            "Kitchen & Crockery" -> selectedImageUriOrRes = "img_crockery_items"
                                            "Jewellery & Accessories" -> selectedImageUriOrRes = "img_jewellery_collection"
                                            "Bags & Pouches" -> selectedImageUriOrRes = "img_bags_items"
                                            "Toys & Kids" -> selectedImageUriOrRes = "img_toys_items"
                                            "Home Decor & Clocks" -> selectedImageUriOrRes = "img_clocks_decor"
                                        }
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                item {
                    OutlinedTextField(
                        value = arrivalDate,
                        onValueChange = { arrivalDate = it },
                        label = { Text("Arrival Tag (e.g. Today's Arrival)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Short Description / Notes") },
                        placeholder = { Text("e.g. Pure Kundan golden finish, complete set") },
                        maxLines = 3,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        onConfirm(title.trim(), category, priceTier, arrivalDate.trim(), description.trim(), selectedImageUriOrRes)
                    }
                },
                enabled = title.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = MelaCrimson)
            ) {
                Text("Post Arrival & Picture")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
