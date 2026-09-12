package com.example.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.R
import com.example.ui.theme.*

enum class GalleryMediaType {
    PHOTO,
    VIDEO
}

data class GalleryItem(
    val id: Int,
    val title: String,
    val subtitle: String,
    val urduTitle: String = "",
    val imageRes: Int,
    val type: GalleryMediaType = GalleryMediaType.PHOTO,
    val videoRef: ShopVideo? = null,
    val category: String = "All",
    val isFeatured: Boolean = false,
    val durationLabel: String? = null
)

@Composable
fun GalleryScreen(
    modifier: Modifier = Modifier
) {
    val galleryItems = remember {
        listOf(
            // Shop Walkthrough Videos (Uploaded by User)
            GalleryItem(
                id = 101,
                title = "Full Store Interior Video Tour",
                subtitle = "Complete 3-minute walkthrough across wall clocks, perfumes, cosmetics, handbags, toys, plastics, dinnerware & glassware",
                urduTitle = "دکان کا اندرونی مکمل ویڈیو وزٹ (3 منٹ)",
                imageRes = R.drawable.img_video_tour_interior,
                type = GalleryMediaType.VIDEO,
                videoRef = ShopVideoCatalog.storeWalkthroughVideo,
                category = "Videos",
                isFeatured = true,
                durationLabel = "3:01 HD"
            ),
            GalleryItem(
                id = 102,
                title = "Shopfront & Bazaar Walk Tour",
                subtitle = "Exterior camera walk through Loharan Mandi Bazaar showing yellow Karachi Sale Mela signboard, motorcycles & entrance",
                urduTitle = "لوہاراں منڈی بازار اور دکان کا بیرونی منظر (17 سیکنڈ)",
                imageRes = R.drawable.img_video_tour_exterior,
                type = GalleryMediaType.VIDEO,
                videoRef = ShopVideoCatalog.exteriorTourVideo,
                category = "Videos",
                isFeatured = true,
                durationLabel = "0:17 HD"
            ),

            // Real Shopfront and Signboard Photos (From User Uploads)
            GalleryItem(
                id = 1,
                title = "Bachat Store Vertical Signboard",
                subtitle = "کراچی سیل میلہ بچت سٹور - آپ کی سوچ سے بھی سستا! 100 / 600 روپے",
                urduTitle = "سیل میلہ بڑا پیلا سائن بورڈ",
                imageRes = R.drawable.img_signboard_vertical,
                category = "Storefront",
                isFeatured = true
            ),
            GalleryItem(
                id = 2,
                title = "Shop Front Entrance & Bazaar Display",
                subtitle = "Loharan Mandi Bazaar Pasrur shop entrance with plastic baskets, tubs, mops & Kalima Tayyaba calligraphy",
                urduTitle = "لوہاراں منڈی بازار مین گیٹ اور برتن",
                imageRes = R.drawable.img_shop_front,
                category = "Storefront",
                isFeatured = true
            ),
            GalleryItem(
                id = 3,
                title = "Sheikh Danish at Cash Counter",
                subtitle = "Proprietor Sheikh Danish seated at the billing counter with wall clocks & toy displays",
                urduTitle = "کاؤنٹر پر شیخ دانش صاحب",
                imageRes = R.drawable.img_proprietor_counter,
                category = "Proprietor",
                isFeatured = true
            ),
            GalleryItem(
                id = 4,
                title = "Bachat Store & Bazaar Street",
                subtitle = "Bustling Loharan Mandi Bazaar street view with motorcycles, shoppers & street entrance",
                urduTitle = "لوہاراں منڈی بازار کا ماحول",
                imageRes = R.drawable.img_shop_bazaar_view,
                category = "Storefront",
                isFeatured = false
            ),
            GalleryItem(
                id = 5,
                title = "Official Store Showcase Poster",
                subtitle = "Complete family shopping poster with all 15 variety categories at Rs. 120 & 600",
                urduTitle = "15 ورائٹی فیملی شاپنگ مکمل پوسٹر",
                imageRes = R.drawable.img_shop_poster,
                category = "Storefront",
                isFeatured = true
            ),
            GalleryItem(
                id = 6,
                title = "Sheikh Danish (Proprietor)",
                subtitle = "Official proprietor portrait of Karachi Sale Mela Pasrur",
                urduTitle = "شیخ دانش (مالک)",
                imageRes = R.drawable.img_proprietor,
                category = "Proprietor",
                isFeatured = false
            ),
            GalleryItem(
                id = 7,
                title = "Store Aisles & Multi-Tier Shelves",
                subtitle = "Spacious multi-floor racks with crockery, glassware, plastics & toys",
                urduTitle = "اندرونی ریک اور ڈسپلے کاؤنٹرز",
                imageRes = R.drawable.img_shop_interior,
                category = "Departments",
                isFeatured = false
            ),

            // Departments & Product Aisles
            GalleryItem(
                id = 8,
                title = "Plastic Items & Crockery (پلاسٹک برتن)",
                subtitle = "Colorful unbreakable plastic washing basins, buckets, food containers & dish drainers",
                urduTitle = "پلاسٹک بالٹی، ٹب اور ڈبے",
                imageRes = R.drawable.img_plastic_items,
                category = "Departments",
                isFeatured = true
            ),
            GalleryItem(
                id = 9,
                title = "Cosmetics & Imported Perfumes (کاسمیٹکس)",
                subtitle = "Imported fragrances, lipsticks, makeup kits, eyeliner & body sprays",
                urduTitle = "کاسمیٹکس، لپ سٹک اور پرفیومز",
                imageRes = R.drawable.img_cosmetics_items,
                category = "Departments",
                isFeatured = true
            ),
            GalleryItem(
                id = 10,
                title = "Bridal Jewellery & Kundan Collection",
                subtitle = "Pakistani artificial bridal jewellery set, Kundan necklaces, jhumkay & bangles",
                urduTitle = "کندن سیٹ، بالیاں اور چوڑیال",
                imageRes = R.drawable.img_jewellery_collection,
                category = "Departments",
                isFeatured = false
            ),
            GalleryItem(
                id = 11,
                title = "Crockery, Glassware & Dinner Sets",
                subtitle = "Dinnerware, melamine bowls, crystal glass water jug & tumbler sets",
                urduTitle = "میلامائن ڈنر سیٹ اور شیشے کے گلاس",
                imageRes = R.drawable.img_crockery_items,
                category = "Departments",
                isFeatured = false
            ),
            GalleryItem(
                id = 12,
                title = "Hosiery & Undergarments (ہوزری)",
                subtitle = "Soft cotton socks, innerwear, vests, hand towels & daily wear hosiery items",
                urduTitle = "جرابیں، بنیان اور رومال",
                imageRes = R.drawable.img_hosiery_items,
                category = "Departments",
                isFeatured = false
            ),
            GalleryItem(
                id = 13,
                title = "Bags, Handbags & Pouches (بیگز اور پرس)",
                subtitle = "Fashion shoulder bags, clutch purses, school bags & makeup pouches",
                urduTitle = "لیڈیز ہینڈ بیگز اور شولڈر پرس",
                imageRes = R.drawable.img_bags_items,
                category = "Departments",
                isFeatured = false
            ),
            GalleryItem(
                id = 14,
                title = "Toys & Kids Gifts (کھلونے اور تحائف)",
                subtitle = "Plush teddy bears, battery-operated cars, building blocks & learning toys",
                urduTitle = "ٹیڈی بیئر، کھلونے اور کاریں",
                imageRes = R.drawable.img_toys_items,
                category = "Departments",
                isFeatured = false
            ),
            GalleryItem(
                id = 15,
                title = "Wall Clocks & Home Decor (وال کلاک)",
                subtitle = "Designer decorative wall clocks, crystal centerpieces & artificial flower vases",
                urduTitle = "وال کلاک اور ڈیکوریشن کے پھول",
                imageRes = R.drawable.img_clocks_decor,
                category = "Departments",
                isFeatured = false
            )
        )
    }

    var selectedFilter by remember { mutableStateOf("All") }
    var selectedPhotoItem by remember { mutableStateOf<GalleryItem?>(null) }
    var activeVideo by remember { mutableStateOf<ShopVideo?>(null) }

    val filterOptions = listOf(
        "All" to "All Media (سب)",
        "Videos" to "Shop Videos (ویڈیوز)",
        "Storefront" to "Store & Signboard (بورڈ)",
        "Departments" to "Departments (ورائٹیاں)",
        "Proprietor" to "Proprietor (شیخ دانش)"
    )

    val displayedItems = remember(selectedFilter, galleryItems) {
        if (selectedFilter == "All") {
            galleryItems
        } else {
            galleryItems.filter { it.category == selectedFilter }
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .fillMaxSize()
            .background(MelaBackgroundLight)
            .testTag("gallery_screen"),
        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // VIP Header Banner
        item(span = { GridItemSpan(2) }) {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, MelaGoldBorder),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Surface(
                        color = MelaBurgundy,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "🎬", fontSize = 18.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "SHOP PICTURES & VIDEO TOURS",
                                    color = MelaGoldLight,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 14.sp,
                                    letterSpacing = 1.sp
                                )
                                Text(
                                    text = "دکان کی تمام اصلی تصاویر اور مکمل ویڈیو وزٹ",
                                    color = Color.White.copy(alpha = 0.9f),
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }

                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "Watch the full 3-minute shop walkthrough and explore high-resolution photos of our Loharan Mandi Bazaar store.",
                            fontSize = 12.sp,
                            color = Color(0xFF554440),
                            lineHeight = 16.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Category Filter Chips
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(filterOptions) { (key, label) ->
                                val isSelected = selectedFilter == key
                                Surface(
                                    shape = RoundedCornerShape(20.dp),
                                    color = if (isSelected) MelaBurgundy else Color(0xFFFAF7F2),
                                    border = androidx.compose.foundation.BorderStroke(
                                        1.dp,
                                        if (isSelected) MelaBurgundy else MelaGoldBorder
                                    ),
                                    modifier = Modifier.clickable { selectedFilter = key }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        if (key == "Videos") {
                                            Icon(
                                                imageVector = Icons.Default.PlayCircle,
                                                contentDescription = null,
                                                tint = if (isSelected) MelaGoldLight else MelaCrimson,
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                        }
                                        Text(
                                            text = label,
                                            color = if (isSelected) MelaGoldLight else Color(0xFF211400),
                                            fontSize = 11.sp,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Media Grid Items
        items(displayedItems, key = { it.id }, span = { item ->
            if (item.isFeatured) GridItemSpan(2) else GridItemSpan(1)
        }) { item ->
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (item.type == GalleryMediaType.VIDEO) MelaCrimson.copy(alpha = 0.5f) else MelaGoldBorder.copy(alpha = 0.6f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        if (item.type == GalleryMediaType.VIDEO && item.videoRef != null) {
                            activeVideo = item.videoRef
                        } else {
                            selectedPhotoItem = item
                        }
                    }
                    .testTag("gallery_card_${item.id}")
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(if (item.isFeatured) 200.dp else 145.dp)
                    ) {
                        Image(
                            painter = painterResource(id = item.imageRes),
                            contentDescription = item.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        // Gradient protection overlay
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        listOf(
                                            Color.Black.copy(alpha = 0.25f),
                                            Color.Transparent,
                                            Color.Black.copy(alpha = 0.65f)
                                        )
                                    )
                                )
                        )

                        // If VIDEO: Play Button Overlay
                        if (item.type == GalleryMediaType.VIDEO) {
                            Surface(
                                shape = CircleShape,
                                color = MelaBurgundy.copy(alpha = 0.88f),
                                border = androidx.compose.foundation.BorderStroke(2.dp, MelaGoldLight),
                                shadowElevation = 6.dp,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(54.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = "Play Video",
                                        tint = MelaGoldLight,
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
                            }

                            // Duration badge
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MelaCrimson,
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(
                                        text = item.durationLabel ?: "VIDEO",
                                        color = Color.White,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            // Watch Video tag
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color.Black.copy(alpha = 0.7f),
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = "TAP TO WATCH VIDEO TOUR",
                                    color = MelaGoldLight,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                )
                            }
                        } else {
                            // Photo Zoom icon
                            Surface(
                                shape = CircleShape,
                                color = Color.Black.copy(alpha = 0.5f),
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(8.dp)
                                    .size(28.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.ZoomIn,
                                        contentDescription = "Zoom",
                                        tint = Color.White,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }

                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = item.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = if (item.isFeatured) 13.sp else 12.sp,
                            color = Color(0xFF211400),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        if (item.urduTitle.isNotBlank()) {
                            Text(
                                text = item.urduTitle,
                                fontSize = 11.sp,
                                color = MelaCrimson,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = item.subtitle,
                            fontSize = 10.sp,
                            color = Color(0xFF554440),
                            lineHeight = 14.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }

        item(span = { GridItemSpan(2) }) {
            Spacer(modifier = Modifier.height(70.dp))
        }
    }

    // Interactive Video Player Dialog
    activeVideo?.let { video ->
        ShopVideoPlayerDialog(
            video = video,
            onDismiss = { activeVideo = null }
        )
    }

    // Photo Lightbox Dialog
    selectedPhotoItem?.let { item ->
        Dialog(
            onDismissRequest = { selectedPhotoItem = null },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.95f))
                    .clickable { selectedPhotoItem = null },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = { selectedPhotoItem = null }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = item.imageRes),
                            contentDescription = item.title,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 440.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = item.title,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    if (item.urduTitle.isNotBlank()) {
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = item.urduTitle,
                            color = MelaGoldLight,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = item.subtitle,
                        color = Color(0xFFD7CCC8),
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

