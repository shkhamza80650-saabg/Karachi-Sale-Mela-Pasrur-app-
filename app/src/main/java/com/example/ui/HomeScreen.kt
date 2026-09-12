package com.example.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.R
import com.example.data.*
import com.example.ui.theme.*

@Composable
fun HomeScreen(
    viewModel: MelaViewModel,
    dailyArrivals: List<DailyArrivalEntity>,
    onNavigate: (AppSection) -> Unit,
    appVersion: String = "v1.3",
    onOpenSafeAppInfo: () -> Unit = {},
    onOpenSecurityLogin: () -> Unit = {},
    onOpenUpdateCenter: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val session by viewModel.session.collectAsStateWithLifecycle()
    val customNotice by viewModel.customNotice.collectAsStateWithLifecycle()
    var fullPosterDialogVisible by remember { mutableStateOf(false) }
    var selectedPhotoRes by remember { mutableStateOf<Int?>(null) }
    var selectedPhotoTitle by remember { mutableStateOf<String?>(null) }
    var selectedPhotoSubtitle by remember { mutableStateOf<String?>(null) }
    var activeShopVideo by remember { mutableStateOf<ShopVideo?>(null) }

    var homeSearchText by remember { mutableStateOf("") }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MelaBackgroundLight)
            .testTag("home_screen"),
        contentPadding = PaddingValues(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // VIP Brand Hero Header with Seal, 3D Gold Typography & Store Showcase
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                MelaTopBarGold,
                                MelaGoldPrimary,
                                MelaBurgundy
                            )
                        )
                    )
                    .padding(top = 16.dp, bottom = 20.dp, start = 16.dp, end = 16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Circular Seal Badge with Logo
                    Surface(
                        shape = CircleShape,
                        shadowElevation = 8.dp,
                        color = Color.White,
                        border = androidx.compose.foundation.BorderStroke(3.dp, MelaGoldLight),
                        modifier = Modifier.size(92.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_app_icon),
                            contentDescription = "Karachi Sale Mela Logo",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp)
                                .clip(CircleShape)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // 👑 VIP Crown & Brand Badge
                    Surface(
                        color = MelaBurgundy,
                        shape = RoundedCornerShape(20.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MelaGoldLight),
                        modifier = Modifier.padding(bottom = 6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Text(text = "👑", fontSize = 13.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "VIP BRAND • PASRUR",
                                color = MelaGoldLight,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.sp
                            )
                        }
                    }

                    Text(
                        text = "KARACHI SALE MELA",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "PASRUR",
                        color = MelaGoldLight,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 3.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Red Ribbon Banner
                    Surface(
                        color = MelaCrimson,
                        shape = RoundedCornerShape(8.dp),
                        shadowElevation = 4.dp,
                        border = androidx.compose.foundation.BorderStroke(1.dp, MelaGoldLight.copy(alpha = 0.6f))
                    ) {
                        Text(
                            text = "A COMPLETE SHOPPING DESTINATION • ALL KINDS OF VARIETIES",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold,
                            textAlign = TextAlign.Center,
                            letterSpacing = 0.5.sp,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Store Showcase Image Card
                    Card(
                        shape = RoundedCornerShape(18.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        border = androidx.compose.foundation.BorderStroke(2.dp, MelaGoldLight),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clickable {
                                selectedPhotoRes = R.drawable.img_shop_bazaar_view
                                selectedPhotoTitle = "Karachi Sale Mela Bachat Store"
                                selectedPhotoSubtitle = "Loharan Mandi Bazaar Pasrur • Aap ki soch say b Sasta!"
                            }
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Image(
                                painter = painterResource(id = R.drawable.img_shop_bazaar_view),
                                contentDescription = "Shop View",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.verticalGradient(
                                            listOf(Color.Transparent, Color.Black.copy(alpha = 0.75f))
                                        )
                                    )
                            )
                            Row(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = MelaTopBarGold,
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Default.Storefront,
                                            contentDescription = null,
                                            tint = Color(0xFF211400),
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = "Karachi Sale Mela Bachat Store",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                    Text(
                                        text = "Loharan Mandi Bazaar, Pasrur • Tap to Zoom",
                                        color = MelaGoldLight,
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }
                    }

                    // 3 Carousel Indicator Dots
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(shape = CircleShape, color = MelaGoldLight, modifier = Modifier.size(8.dp)) {}
                        Surface(shape = CircleShape, color = Color.White.copy(alpha = 0.5f), modifier = Modifier.size(6.dp)) {}
                        Surface(shape = CircleShape, color = Color.White.copy(alpha = 0.5f), modifier = Modifier.size(6.dp)) {}
                    }
                }
            }
        }

        // Mockup Search Bar Pill with Circular Crimson Search Button
        item {
            Surface(
                shape = RoundedCornerShape(30.dp),
                color = Color.White,
                shadowElevation = 4.dp,
                border = androidx.compose.foundation.BorderStroke(1.5.dp, MelaGoldBorder),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 5.dp),
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
                        value = homeSearchText,
                        onValueChange = { homeSearchText = it },
                        placeholder = {
                            Text("Search for products...", color = Color.Gray, fontSize = 14.sp)
                        },
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
                    if (homeSearchText.isNotBlank()) {
                        IconButton(
                            onClick = { homeSearchText = "" },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear", tint = Color.Gray, modifier = Modifier.size(16.dp))
                        }
                    }
                    Surface(
                        shape = CircleShape,
                        color = MelaCrimson,
                        shadowElevation = 3.dp,
                        modifier = Modifier
                            .size(38.dp)
                            .clickable {
                                if (homeSearchText.isNotBlank()) {
                                    viewModel.onSearchQueryChanged(homeSearchText)
                                }
                                onNavigate(AppSection.SEARCH)
                            }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Now",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }

        // 6 Quick Action Grid Cards (2 rows x 3 columns from Mockup)
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, MelaGoldBorder),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Row 1: ALL CATEGORIES | NEW ARRIVALS | BEST SELLERS
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        QuickActionCardItem(
                            icon = Icons.Default.GridView,
                            iconTint = Color(0xFFFFA000),
                            label = "ALL CATEGORIES",
                            modifier = Modifier.weight(1f),
                            onClick = { onNavigate(AppSection.VARIETIES) }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        QuickActionCardItem(
                            icon = Icons.Default.NewReleases,
                            iconTint = Color(0xFFD32F2F),
                            label = "NEW ARRIVALS",
                            badgeText = if (dailyArrivals.isNotEmpty()) "${dailyArrivals.size}" else null,
                            modifier = Modifier.weight(1f),
                            onClick = { onNavigate(AppSection.DAILY_ARRIVALS) }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        QuickActionCardItem(
                            icon = Icons.Default.Star,
                            iconTint = Color(0xFFFFB300),
                            label = "BEST SELLERS",
                            modifier = Modifier.weight(1f),
                            onClick = { onNavigate(AppSection.VARIETIES) }
                        )
                    }

                    // Row 2: SPECIAL OFFERS | ABOUT US | CONTACT US
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        QuickActionCardItem(
                            icon = Icons.Default.LocalOffer,
                            iconTint = Color(0xFFE53935),
                            label = "SPECIAL OFFERS",
                            modifier = Modifier.weight(1f),
                            onClick = { onNavigate(AppSection.VARIETIES) }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        QuickActionCardItem(
                            icon = Icons.Default.Info,
                            iconTint = Color(0xFF880E4F),
                            label = "ABOUT US",
                            modifier = Modifier.weight(1f),
                            onClick = { onNavigate(AppSection.ABOUT) }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        QuickActionCardItem(
                            icon = Icons.Default.Call,
                            iconTint = Color(0xFFC62828),
                            label = "CONTACT US",
                            modifier = Modifier.weight(1f),
                            onClick = { onNavigate(AppSection.CONTACT) }
                        )
                    }
                }
            }
        }

        // TOP CATEGORIES Section (Mockup Row with "View All >")
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "TOP CATEGORIES",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF211400)
                    )
                    TextButton(onClick = { onNavigate(AppSection.VARIETIES) }) {
                        Text(
                            text = "View All >",
                            color = MelaCrimson,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                val topCategoryList = listOf(
                    Triple("Jewellers", R.drawable.img_jewellery_collection, "Jewellery"),
                    Triple("Cosmatics", R.drawable.img_cosmetics_items, "Cosmetics & Perfumes"),
                    Triple("Steel Items", R.drawable.img_crockery_items, "Crockery & Glassware"),
                    Triple("Home Decor", R.drawable.img_clocks_decor, "Decoration & Clocks"),
                    Triple("Plastic Items", R.drawable.img_plastic_items, "Plastic Utensils"),
                    Triple("Toys & Gifts", R.drawable.img_toys_items, "Kids Toys"),
                    Triple("Bags & Pouches", R.drawable.img_bags_items, "Bags & Wallets")
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(topCategoryList) { (title, imageRes, filterName) ->
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            border = androidx.compose.foundation.BorderStroke(1.dp, MelaGoldBorder),
                            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                            modifier = Modifier
                                .width(108.dp)
                                .clickable {
                                    viewModel.onCategorySelected(filterName)
                                    onNavigate(AppSection.VARIETIES)
                                }
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    border = androidx.compose.foundation.BorderStroke(1.5.dp, MelaGoldPrimary),
                                    modifier = Modifier.size(64.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = imageRes),
                                        contentDescription = title,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = title,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF211400),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }

        // 100% Safe App Verified & Not Harmful Banner
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFA5D6A7)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable { onOpenSafeAppInfo() }
                    .testTag("home_safe_app_banner")
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFF2E7D32),
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.VerifiedUser,
                                contentDescription = "Safe App",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Play Protect Safe App",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 13.sp,
                                color = Color(0xFF1B5E20)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                color = Color(0xFF2E7D32),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = "NOT HARMFUL",
                                    color = Color.White,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                )
                            }
                        }
                        Text(
                            text = "محفوظ ایپ • 100% Not Harmful • Zero Sensitive Permissions • Tap for Guide",
                            fontSize = 11.sp,
                            color = Color(0xFF388E3C),
                            lineHeight = 15.sp
                        )
                    }

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = Color(0xFF2E7D32),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        // Security & Login Bar
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (session.isLoggedIn) Color(0xFFFFF3E0) else MaterialTheme.colorScheme.surface
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (session.isLoggedIn) Color(0xFFFFB74D) else MaterialTheme.colorScheme.outlineVariant
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable { onOpenSecurityLogin() }
                    .testTag("home_security_login_bar")
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = if (session.isLoggedIn) MelaCrimson else MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = if (session.isLoggedIn) Icons.Default.LockOpen else Icons.Default.Lock,
                                contentDescription = "Login",
                                tint = if (session.isLoggedIn) Color.White else MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (session.isLoggedIn) {
                                "${session.role.displayName}: ${session.name}"
                            } else {
                                "Security Login & Access"
                            },
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = if (session.isLoggedIn) {
                                "Active session • Tap to manage or logout"
                            } else {
                                "Proprietor (Sheikh Danish) PIN & Shopper Access"
                            },
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Text(
                        text = if (session.isLoggedIn) "MANAGE" else "LOGIN",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MelaCrimson
                    )
                }
            }
        }

        // App Update Option Bar
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FBE7)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFDCE775)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable { onOpenUpdateCenter() }
                    .testTag("home_update_option_bar")
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFF827717),
                        modifier = Modifier.size(34.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.SystemUpdate,
                                contentDescription = "App Update Option",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "App Update Option",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = Color(0xFF33691E)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                color = if (appVersion == "v1.3") Color(0xFF2E7D32) else Color(0xFF827717),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = if (appVersion == "v1.3") "v1.3 UPDATED" else "$appVersion ACTIVE",
                                    color = Color.White,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
                                )
                            }
                        }
                        Text(
                            text = if (appVersion == "v1.3") "ایپ اپ ڈیٹ ہو چکی ہے • App Updated to Latest v1.3" else "نئے ورژن اور اے پی کے کی جانچ • Check Updates & Releases",
                            fontSize = 11.sp,
                            color = if (appVersion == "v1.3") Color(0xFF2E7D32) else Color(0xFF558B2F)
                        )
                    }

                    Surface(
                        color = if (appVersion == "v1.3") Color(0xFF2E7D32).copy(alpha = 0.12f) else MelaCrimson.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = if (appVersion == "v1.3") "UPDATED" else "UPDATE",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = if (appVersion == "v1.3") Color(0xFF2E7D32) else MelaCrimson,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }

        // Custom Shop Notice (If set by Proprietor)
        if (customNotice.isNotBlank()) {
            item {
                Surface(
                    color = Color(0xFFFFF9C4),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFFF59D)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Campaign,
                            contentDescription = "Announcement",
                            tint = Color(0xFFF57F17),
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = customNotice,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF5D4037),
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }

        // Shop Featured Photos Card
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable {
                        selectedPhotoRes = R.drawable.img_shop_bazaar_view
                        selectedPhotoTitle = "Karachi Sale Mela Bachat Store"
                        selectedPhotoSubtitle = "Loharan Mandi Bazaar Pasrur • Aap ki soch say b Sasta!"
                    }
                    .testTag("home_shop_featured_card")
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_shop_bazaar_view),
                            contentDescription = "Karachi Sale Mela Bachat Store Bazaar Street View",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color.Black.copy(alpha = 0.75f)
                                        )
                                    )
                                )
                        )
                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Storefront,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Column {
                                Text(
                                    text = "Karachi Sale Mela Bachat Store",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                                Text(
                                    text = "Loharan Mandi Bazaar, Pasrur • Tap to Zoom Photo",
                                    color = Color(0xFFFFECB3),
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }

                    // Short Shop Introduction
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Welcome to Karachi Sale Mela Pasrur",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Karachi Sale Mela Pasrur offers a wide variety of products at affordable sale prices. Visit our shop to explore our latest varieties and daily new arrivals.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 20.sp
                        )
                    }
                }
            }
        }

        // Real Photos Horizontal Showcase
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.PhotoLibrary,
                            contentDescription = null,
                            tint = MelaCrimson,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Shop & Proprietor Photos",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    TextButton(onClick = { onNavigate(AppSection.GALLERY) }) {
                        Text("See All (8)", color = MelaCrimson, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    val quickPhotos = listOf(
                        Triple(R.drawable.img_signboard_vertical, "Bachat Store Signboard", "کراچی سیل میلہ - آپ کی سوچ سے بھی سستا!"),
                        Triple(R.drawable.img_shop_front, "Main Shop Front Entrance", "لوہاراں منڈی بازار Pasrur Storefront"),
                        Triple(R.drawable.img_proprietor_counter, "Sheikh Danish at Counter", "کاؤنٹر پر شیخ دانش صاحب"),
                        Triple(R.drawable.img_shop_bazaar_view, "Bachat Store Bazaar View", "لوہاراں منڈی بازار Street Entrance"),
                        Triple(R.drawable.img_plastic_items, "Plastic Items & Crockery", "پلاسٹک کے برتن اور ٹوکریاں"),
                        Triple(R.drawable.img_hosiery_items, "Hosiery & Undergarments", "ہوزری، جرابیں اور بنیان"),
                        Triple(R.drawable.img_cosmetics_items, "Cosmetics & Perfumes", "کاسمیٹکس اور پرفیومز"),
                        Triple(R.drawable.img_crockery_items, "Crockery & Glassware", "کروکری اور ڈنر سیٹ"),
                        Triple(R.drawable.img_jewellery_collection, "Jewellery Collection", "زیورات اور کندن سیٹ"),
                        Triple(R.drawable.img_bags_items, "Bags & Pouches", "بیگز اور ہینڈ بیگز"),
                        Triple(R.drawable.img_toys_items, "Toys & Gifts", "کھلونے اور تحائف"),
                        Triple(R.drawable.img_clocks_decor, "Clocks & Decor", "وال کلاک اور ڈیکوریشن"),
                        Triple(R.drawable.img_shop_interior, "Store Aisles", "Display Racks")
                    )

                    items(quickPhotos) { (res, title, sub) ->
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            modifier = Modifier
                                .width(130.dp)
                                .clickable {
                                    selectedPhotoRes = res
                                    selectedPhotoTitle = title
                                    selectedPhotoSubtitle = sub
                                }
                        ) {
                            Column {
                                Image(
                                    painter = painterResource(id = res),
                                    contentDescription = title,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(110.dp)
                                )
                                Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)) {
                                    Text(
                                        text = title,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = sub,
                                        fontSize = 10.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Shop Video Tours & Walkthrough Section (دکان کی ویڈیوز اور ورچوئل وزٹ)
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = MelaCrimson,
                            modifier = Modifier.size(26.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "SHOP VIDEO TOURS (دکان کی ویڈیوز)",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF211400),
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = "اندرونی اور بیرونی دکان کا لائیو ویڈیو وزٹ",
                                fontSize = 11.sp,
                                color = MelaCrimson,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    TextButton(onClick = { onNavigate(AppSection.GALLERY) }) {
                        Text("View Both (2)", color = MelaCrimson, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = MelaCrimson,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    val videoList = ShopVideoCatalog.allVideos

                    items(videoList) { video ->
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            border = androidx.compose.foundation.BorderStroke(1.dp, MelaGoldBorder),
                            modifier = Modifier
                                .width(270.dp)
                                .clickable { activeShopVideo = video }
                                .testTag("home_video_card_${video.id}")
                        ) {
                            Column {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(145.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = video.thumbnailRes),
                                        contentDescription = video.title,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )

                                    // Gradient overlay
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(
                                                Brush.verticalGradient(
                                                    listOf(
                                                        Color.Black.copy(alpha = 0.2f),
                                                        Color.Transparent,
                                                        Color.Black.copy(alpha = 0.7f)
                                                    )
                                                )
                                            )
                                    )

                                    // Glowing Play button in center
                                    Surface(
                                        shape = CircleShape,
                                        color = MelaBurgundy.copy(alpha = 0.88f),
                                        border = androidx.compose.foundation.BorderStroke(2.dp, MelaGoldLight),
                                        shadowElevation = 6.dp,
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .size(46.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(
                                                imageVector = Icons.Default.PlayArrow,
                                                contentDescription = "Play",
                                                tint = MelaGoldLight,
                                                modifier = Modifier.size(26.dp)
                                            )
                                        }
                                    }

                                    // Duration pill
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
                                                modifier = Modifier.size(10.dp)
                                            )
                                            Spacer(modifier = Modifier.width(3.dp))
                                            Text(
                                                text = video.durationLabel,
                                                color = Color.White,
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }

                                    // Tag pill
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = Color.Black.copy(alpha = 0.7f),
                                        modifier = Modifier
                                            .align(Alignment.TopStart)
                                            .padding(8.dp)
                                    ) {
                                        Text(
                                            text = video.badgeText,
                                            color = MelaGoldLight,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }

                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        text = video.title,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = Color(0xFF211400),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = video.urduTitle,
                                        fontSize = 11.sp,
                                        color = MelaCrimson,
                                        fontWeight = FontWeight.SemiBold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = video.description,
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
                }
            }
        }

        // Proprietor Sheikh Danish Feature Card
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable { onNavigate(AppSection.PROPRIETOR) }
                    .testTag("home_proprietor_card")
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        shadowElevation = 3.dp,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.size(76.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_proprietor_counter),
                            contentDescription = "Proprietor Sheikh Danish",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Surface(
                            color = MelaCrimson.copy(alpha = 0.12f),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "PROPRIETOR",
                                color = MelaCrimson,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = "Sheikh Danish",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Seated at Cash Counter • Wall Clocks & Toys",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "Karachi Sale Mela Pasrur • WhatsApp: 03216671694",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 15.sp
                        )
                    }

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        // Official Store Showcase Poster Card
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable { fullPosterDialogVisible = true }
                    .testTag("home_poster_card")
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_shop_poster),
                            contentDescription = "Official Shop Showcase Poster",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color.Black.copy(alpha = 0.75f)
                                        )
                                    )
                                )
                        )
                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MelaCrimson
                            ) {
                                Text(
                                    text = "OFFICIAL POSTER",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Tap to View Full Poster",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "Complete 15 Family Varieties Showcase",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = "Jewellery, Cosmetics, Handbags, Crockery, Toys, Glassware, Plastics & Decor at Rs. 120 & Rs. 600 Mega Sale rates.",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }

        // Bachat Store Signboard Card
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.size(70.dp, 80.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_shop_signboard),
                            contentDescription = "Bachat Store Signboard",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Surface(
                            color = Color(0xFFFFF9C4),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "BACHAT STORE SIGNBOARD",
                                color = Color(0xFF8D6E63),
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = "کراچی سیل میلہ بچت سٹور",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "آپ کی سوچ سے بھی سستا! • لوہاراں منڈی بازار پسرور",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // SALE RATES HIGHLIGHT
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocalOffer,
                        contentDescription = null,
                        tint = MelaCrimson,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "OUR SALE RATES",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("Rs. 120", "Rs. 300", "Rs. 600", "Rs. 1,200").forEach { rate ->
                        SaleRateBadge(
                            rate = rate,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        // Primary App Buttons Grid (as explicitly requested)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "QUICK SHOP NAVIGATION",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickNavButton(
                        title = "Our Varieties",
                        subtitle = "60+ Categories",
                        icon = Icons.Default.Category,
                        color = MelaCrimson,
                        onClick = { onNavigate(AppSection.VARIETIES) },
                        modifier = Modifier.weight(1f)
                    )
                    QuickNavButton(
                        title = "Daily Arrivals",
                        subtitle = "Fresh Updates",
                        icon = Icons.Default.NewReleases,
                        color = MelaGold,
                        onClick = { onNavigate(AppSection.DAILY_ARRIVALS) },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickNavButton(
                        title = "Shop Gallery",
                        subtitle = "Photos & Interior",
                        icon = Icons.Default.PhotoLibrary,
                        color = Color(0xFF5E35B1),
                        onClick = { onNavigate(AppSection.GALLERY) },
                        modifier = Modifier.weight(1f)
                    )
                    QuickNavButton(
                        title = "Visit Our Shop",
                        subtitle = "Map & Timings",
                        icon = Icons.Default.Place,
                        color = Color(0xFF00897B),
                        onClick = { onNavigate(AppSection.VISIT_SHOP) },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickNavButton(
                        title = "Proprietor",
                        subtitle = "Sheikh Danish",
                        icon = Icons.Default.Person,
                        color = Color(0xFF3949AB),
                        onClick = { onNavigate(AppSection.PROPRIETOR) },
                        modifier = Modifier.weight(1f)
                    )
                    QuickNavButton(
                        title = "About Us",
                        subtitle = "Shop Details",
                        icon = Icons.Default.Info,
                        color = Color(0xFF455A64),
                        onClick = { onNavigate(AppSection.ABOUT) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Action Buttons: Open in Google Maps & WhatsApp Us
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "VISIT & CONNECT",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // Google Maps Button
                GoogleMapsActionButton()

                // WhatsApp Us Buttons (Sheikh Danish & Sheikh Hamza)
                WhatsAppActionButton(
                    contactName = "Sheikh Danish",
                    phoneNumber = MelaConstants.PHONE_DANISH,
                    displayPhone = MelaConstants.DISPLAY_DANISH
                )

                WhatsAppActionButton(
                    contactName = "Sheikh Hamza",
                    phoneNumber = MelaConstants.PHONE_HAMZA,
                    displayPhone = MelaConstants.DISPLAY_HAMZA
                )
            }
        }

        // Daily Arrivals Preview Carousel
        if (dailyArrivals.isNotEmpty()) {
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.FiberNew,
                                contentDescription = null,
                                tint = MelaCrimson,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "LATEST NEW ARRIVALS",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        TextButton(onClick = { onNavigate(AppSection.DAILY_ARRIVALS) }) {
                            Text(
                                text = "View All",
                                fontWeight = FontWeight.Bold,
                                color = MelaCrimson
                            )
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(dailyArrivals.take(5)) { arrival ->
                            ArrivalPreviewCard(
                                arrival = arrival,
                                onClick = { onNavigate(AppSection.DAILY_ARRIVALS) }
                            )
                        }
                    }
                }
            }
        }

        // Shop Hours & Open 7 Days Notice Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = MelaCrimson,
                        modifier = Modifier.size(44.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.AccessTime,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column {
                        Text(
                            text = "Opening Hours: 09:30 AM to 09:00 PM",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Open 7 Days a Week (Sunday to Saturday)",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                        )
                    }
                }
            }
        }

        // OUR CONTACTS Card (Exact replica of Mockup Screen 3)
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, MelaGoldBorder),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Column {
                    // Burgundy Header Banner with Gold Border
                    Surface(
                        color = MelaBurgundy,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "👑", fontSize = 14.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "OUR CONTACTS",
                                color = MelaGoldLight,
                                fontWeight = FontWeight.Black,
                                fontSize = 14.sp,
                                letterSpacing = 1.sp
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Contact 1: Sheikh Danish
                        ContactRowItem(
                            name = "Sheikh Danish",
                            role = "Proprietor",
                            phone = MelaConstants.PHONE_DANISH,
                            display = "0321-6671694"
                        )
                        HorizontalDivider(color = MelaGoldBorder.copy(alpha = 0.4f))

                        // Contact 2: Sheikh Hamza
                        ContactRowItem(
                            name = "Sheikh Hamza",
                            role = "Customer Relations",
                            phone = MelaConstants.PHONE_HAMZA,
                            display = "0307-4029245"
                        )
                        HorizontalDivider(color = MelaGoldBorder.copy(alpha = 0.4f))

                        // Contact 3: Sheikh Amran
                        ContactRowItem(
                            name = "Sheikh Amran",
                            role = "Management",
                            phone = MelaConstants.PHONE_AMRAN,
                            display = "03217624996"
                        )
                    }
                }
            }
        }

        // OUR ADDRESS Card (Exact replica of Mockup Screen 3)
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, MelaGoldBorder),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Column {
                    // Burgundy Header Banner with Gold Border
                    Surface(
                        color = MelaBurgundy,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = MelaGoldLight,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "OUR ADDRESS",
                                color = MelaGoldLight,
                                fontWeight = FontWeight.Black,
                                fontSize = 14.sp,
                                letterSpacing = 1.sp
                            )
                        }
                    }

                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.Top) {
                            Surface(
                                shape = CircleShape,
                                color = MelaCrimson.copy(alpha = 0.12f),
                                modifier = Modifier.size(34.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Place,
                                        contentDescription = null,
                                        tint = MelaCrimson,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Karachi Sale Mela Bachat Store",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = Color(0xFF211400)
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "7M76+94R, Near Naseem Hayat Shaheed Rd, Loharan Mandi Bazaar, Walled City, Pasrur, 51480",
                                    fontSize = 12.sp,
                                    color = Color(0xFF554440),
                                    lineHeight = 16.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Map Image Preview Box
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(130.dp)
                                .clickable { launchGoogleMaps(context) }
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                Image(
                                    painter = painterResource(id = R.drawable.img_shop_front),
                                    contentDescription = "Map and Storefront Location",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            Brush.verticalGradient(
                                                listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f))
                                            )
                                        )
                                )
                                Row(
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.Map, contentDescription = null, tint = MelaGoldLight, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Google Maps Pin • Tap to Navigate",
                                        color = Color.White,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // GET DIRECTIONS Button (Burgundy with Gold Pin)
                        Button(
                            onClick = { launchGoogleMaps(context) },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MelaBurgundy,
                                contentColor = MelaGoldLight
                            ),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Directions,
                                contentDescription = null,
                                tint = MelaGoldLight,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "GET DIRECTIONS",
                                fontWeight = FontWeight.Black,
                                fontSize = 13.sp,
                                letterSpacing = 1.sp
                            )
                        }
                    }
                }
            }
        }

        // Trust Badges Row (Bottom of Poster - 6 Circular Badges)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "WHY SHOP AT KARACHI SALE MELA?",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    color = MelaBurgundy,
                    letterSpacing = 1.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(10.dp))

                val trustBadges = listOf(
                    Pair("BEST QUALITY", "100% Genuine"),
                    Pair("HUGE VARIETY", "All in One Place"),
                    Pair("REASONABLE PRICES", "Aap Ki Soch Sy B Sasta"),
                    Pair("CUSTOMER SATISFACTION", "Our First Priority"),
                    Pair("TRUSTED BRAND", "VIP Experience"),
                    Pair("24/7 SUPPORT", "WhatsApp Ready")
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    trustBadges.take(3).forEach { (title, subtitle) ->
                        TrustBadgeItem(title = title, subtitle = subtitle, modifier = Modifier.weight(1f))
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    trustBadges.drop(3).forEach { (title, subtitle) ->
                        TrustBadgeItem(title = title, subtitle = subtitle, modifier = Modifier.weight(1f))
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Golden Tagline Banner
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MelaTopBarGold,
                    border = androidx.compose.foundation.BorderStroke(1.dp, MelaGoldBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "KARACHI SALE MELA PASRUR – YOUR COMPLETE SHOPPING DESTINATION",
                        color = Color(0xFF211400),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.5.sp,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }

    // Full-Size Poster Lightbox Dialog
    if (fullPosterDialogVisible) {
        androidx.compose.ui.window.Dialog(
            onDismissRequest = { fullPosterDialogVisible = false },
            properties = androidx.compose.ui.window.DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.95f))
                    .clickable { fullPosterDialogVisible = false },
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
                        IconButton(onClick = { fullPosterDialogVisible = false }) {
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
                            painter = painterResource(id = R.drawable.img_shop_poster),
                            contentDescription = "Official Karachi Sale Mela Pasrur Poster",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 460.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Official Store Poster • 15 Family Varieties",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                    Text(
                        text = "Mega Sale Rs. 120 & Rs. 600 • Loharan Mandi Bazaar, Pasrur",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 12.sp
                    )
                }
            }
        }
    }

    // Full-Size Photo Lightbox Dialog
    selectedPhotoRes?.let { photoRes ->
        androidx.compose.ui.window.Dialog(
            onDismissRequest = { selectedPhotoRes = null },
            properties = androidx.compose.ui.window.DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.95f))
                    .clickable { selectedPhotoRes = null },
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
                        IconButton(onClick = { selectedPhotoRes = null }) {
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
                            painter = painterResource(id = photoRes),
                            contentDescription = selectedPhotoTitle ?: "Shop Photo",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 480.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = selectedPhotoTitle ?: "Karachi Sale Mela Pasrur",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                    selectedPhotoSubtitle?.let { sub ->
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = sub,
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }

    // Shop Video Tour Player Dialog
    activeShopVideo?.let { video ->
        ShopVideoPlayerDialog(
            video = video,
            onDismiss = { activeShopVideo = null }
        )
    }
}

@Composable
fun QuickNavButton(
    title: String,
    subtitle: String,
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .clickable(onClick = onClick)
            .testTag("nav_btn_${title.replace(" ", "_")}")
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = color.copy(alpha = 0.15f),
                modifier = Modifier.size(42.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
fun ArrivalPreviewCard(
    arrival: DailyArrivalEntity,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .width(220.dp)
            .clickable(onClick = onClick)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(115.dp)
            ) {
                AsyncImage(
                    model = resolveArrivalImageModel(arrival.imageResName, arrival.category),
                    contentDescription = arrival.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Surface(
                    color = MelaCrimson,
                    shape = RoundedCornerShape(bottomEnd = 8.dp),
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Text(
                        text = arrival.arrivalDate,
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                    )
                }

                Surface(
                    color = Rate300,
                    shape = RoundedCornerShape(bottomStart = 8.dp),
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Text(
                        text = arrival.priceTier,
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                    )
                }
            }

            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = arrival.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = arrival.category,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun QuickActionCardItem(
    icon: ImageVector,
    iconTint: Color,
    label: String,
    modifier: Modifier = Modifier,
    badgeText: String? = null,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = Color(0xFFFAF7F2),
        border = androidx.compose.foundation.BorderStroke(1.dp, MelaGoldBorder.copy(alpha = 0.5f)),
        modifier = modifier
            .clickable(onClick = onClick)
            .height(82.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Surface(
                    shape = CircleShape,
                    color = iconTint.copy(alpha = 0.12f),
                    modifier = Modifier.size(38.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = label,
                            tint = iconTint,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = label,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF211400),
                    textAlign = TextAlign.Center,
                    lineHeight = 11.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (badgeText != null) {
                Surface(
                    color = MelaCrimson,
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                ) {
                    Text(
                        text = badgeText,
                        color = Color.White,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ContactRowItem(
    name: String,
    role: String,
    phone: String,
    display: String
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = CircleShape,
            color = MelaBurgundy,
            modifier = Modifier.size(38.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = MelaGoldLight,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color(0xFF211400)
            )
            Text(
                text = "$role • $display",
                fontSize = 12.sp,
                color = Color(0xFF554440)
            )
        }
        // Call button
        IconButton(
            onClick = {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
                try { context.startActivity(intent) } catch (_: Exception) {}
            },
            modifier = Modifier.size(34.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = MelaBurgundy.copy(alpha = 0.1f),
                modifier = Modifier.size(32.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "Call $name",
                        tint = MelaBurgundy,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(6.dp))
        // WhatsApp button
        IconButton(
            onClick = {
                val msg = "Assalam-o-Alaikum $name, I am contacting you regarding Karachi Sale Mela Pasrur."
                val url = "https://api.whatsapp.com/send?phone=$phone&text=${Uri.encode(msg)}"
                try { context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url))) } catch (_: Exception) {}
            },
            modifier = Modifier.size(34.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = WhatsAppGreen.copy(alpha = 0.12f),
                modifier = Modifier.size(32.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Chat,
                        contentDescription = "WhatsApp $name",
                        tint = WhatsAppGreen,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TrustBadgeItem(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(4.dp)
    ) {
        Surface(
            shape = CircleShape,
            color = MelaBurgundy,
            border = androidx.compose.foundation.BorderStroke(2.dp, MelaGoldLight),
            shadowElevation = 4.dp,
            modifier = Modifier.size(48.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = when {
                        title.contains("QUALITY") -> Icons.Default.WorkspacePremium
                        title.contains("VARIETY") -> Icons.Default.AutoAwesome
                        title.contains("PRICES") -> Icons.Default.LocalOffer
                        title.contains("SATISFACTION") -> Icons.Default.ThumbUp
                        title.contains("BRAND") -> Icons.Default.Star
                        else -> Icons.Default.SupportAgent
                    },
                    contentDescription = title,
                    tint = MelaGoldLight,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            fontSize = 8.sp,
            fontWeight = FontWeight.Black,
            color = MelaBurgundy,
            textAlign = TextAlign.Center,
            lineHeight = 10.sp
        )
        Text(
            text = subtitle,
            fontSize = 8.sp,
            color = Color(0xFF6B584E),
            textAlign = TextAlign.Center,
            lineHeight = 9.sp
        )
    }
}

