package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.*
import com.example.ui.theme.*

class MainActivity : ComponentActivity() {
    private val viewModel: MelaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                KarachiSaleMelaApp(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KarachiSaleMelaApp(
    viewModel: MelaViewModel,
    modifier: Modifier = Modifier
) {
    val currentSection by viewModel.currentSection.collectAsStateWithLifecycle()
    val dailyArrivals by viewModel.allArrivals.collectAsStateWithLifecycle()
    val session by viewModel.session.collectAsStateWithLifecycle()
    val appVersion by viewModel.appVersion.collectAsStateWithLifecycle()
    val isUpdateApplied by viewModel.isUpdateApplied.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var showWhatsAppDialog by remember { mutableStateOf(false) }
    var showSafeAppDialog by remember { mutableStateOf(false) }
    var showSecurityLoginDialog by remember { mutableStateOf(false) }
    var showUpdateDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "KARACHI SALE MELA",
                                fontWeight = FontWeight.Black,
                                fontSize = 16.sp,
                                letterSpacing = 0.5.sp,
                                color = Color(0xFF211400),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                color = MelaBurgundy,
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = "VIP",
                                    color = MelaGoldLight,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                )
                            }
                        }
                        Text(
                            text = if (session.isLoggedIn) "Logged in: ${session.name} • Pasrur" else "PASRUR • VIP BRAND",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF5A4110),
                            maxLines = 1
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { viewModel.navigateTo(AppSection.MORE) }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = Color(0xFF211400)
                        )
                    }
                },
                actions = {
                    // Notification Bell (Daily Arrivals)
                    IconButton(
                        onClick = { viewModel.navigateTo(AppSection.DAILY_ARRIVALS) },
                        modifier = Modifier.testTag("topbar_notifications_btn")
                    ) {
                        BadgedBox(
                            badge = {
                                if (dailyArrivals.isNotEmpty()) {
                                    Badge(
                                        containerColor = MelaCrimson,
                                        contentColor = Color.White
                                    ) {
                                        Text("${dailyArrivals.size}")
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "New Arrivals",
                                tint = Color(0xFF211400),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    // Safe App Verified direct action
                    IconButton(
                        onClick = { showSafeAppDialog = true },
                        modifier = Modifier.testTag("topbar_safe_app_btn")
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFF2E7D32),
                            modifier = Modifier.size(28.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.VerifiedUser,
                                    contentDescription = "Safe App Verified",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }

                    // Security Login action
                    IconButton(
                        onClick = { showSecurityLoginDialog = true },
                        modifier = Modifier.testTag("topbar_security_login_btn")
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = if (session.isLoggedIn) MelaBurgundy else Color(0xFF211400).copy(alpha = 0.12f),
                            modifier = Modifier.size(28.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = if (session.isLoggedIn) Icons.Default.LockOpen else Icons.Default.Lock,
                                    contentDescription = "Security Login",
                                    tint = if (session.isLoggedIn) MelaGoldLight else Color(0xFF211400),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }

                    // WhatsApp quick contact
                    IconButton(
                        onClick = { showWhatsAppDialog = true },
                        modifier = Modifier.testTag("topbar_whatsapp_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Chat,
                            contentDescription = "WhatsApp Us",
                            tint = Color(0xFF1B5E20)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MelaTopBarGold,
                    titleContentColor = Color(0xFF211400),
                    navigationIconContentColor = Color(0xFF211400),
                    actionIconContentColor = Color(0xFF211400)
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MelaBurgundy,
                tonalElevation = 8.dp,
                windowInsets = WindowInsets.navigationBars,
                modifier = Modifier.testTag("bottom_nav_bar")
            ) {
                // 1. Home
                NavigationBarItem(
                    selected = currentSection == AppSection.HOME,
                    onClick = { viewModel.navigateTo(AppSection.HOME) },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home", fontSize = 11.sp, fontWeight = if (currentSection == AppSection.HOME) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MelaGoldLight,
                        selectedTextColor = MelaGoldLight,
                        unselectedIconColor = Color(0xFFE5D5C5),
                        unselectedTextColor = Color(0xFFE5D5C5),
                        indicatorColor = Color(0xFF9E1B1B)
                    ),
                    modifier = Modifier.testTag("nav_home")
                )

                // 2. Categories
                NavigationBarItem(
                    selected = currentSection == AppSection.VARIETIES,
                    onClick = { viewModel.navigateTo(AppSection.VARIETIES) },
                    icon = { Icon(Icons.Default.GridView, contentDescription = "Categories") },
                    label = { Text("Categories", fontSize = 11.sp, fontWeight = if (currentSection == AppSection.VARIETIES) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MelaGoldLight,
                        selectedTextColor = MelaGoldLight,
                        unselectedIconColor = Color(0xFFE5D5C5),
                        unselectedTextColor = Color(0xFFE5D5C5),
                        indicatorColor = Color(0xFF9E1B1B)
                    ),
                    modifier = Modifier.testTag("nav_varieties")
                )

                // 3. Search
                NavigationBarItem(
                    selected = currentSection == AppSection.SEARCH,
                    onClick = { viewModel.navigateTo(AppSection.SEARCH) },
                    icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    label = { Text("Search", fontSize = 11.sp, fontWeight = if (currentSection == AppSection.SEARCH) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MelaGoldLight,
                        selectedTextColor = MelaGoldLight,
                        unselectedIconColor = Color(0xFFE5D5C5),
                        unselectedTextColor = Color(0xFFE5D5C5),
                        indicatorColor = Color(0xFF9E1B1B)
                    ),
                    modifier = Modifier.testTag("nav_search")
                )

                // 4. Contact
                NavigationBarItem(
                    selected = currentSection in listOf(AppSection.CONTACT, AppSection.VISIT_SHOP),
                    onClick = { viewModel.navigateTo(AppSection.CONTACT) },
                    icon = { Icon(Icons.Default.Call, contentDescription = "Contact") },
                    label = { Text("Contact", fontSize = 11.sp, fontWeight = if (currentSection in listOf(AppSection.CONTACT, AppSection.VISIT_SHOP)) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MelaGoldLight,
                        selectedTextColor = MelaGoldLight,
                        unselectedIconColor = Color(0xFFE5D5C5),
                        unselectedTextColor = Color(0xFFE5D5C5),
                        indicatorColor = Color(0xFF9E1B1B)
                    ),
                    modifier = Modifier.testTag("nav_contact")
                )

                // 5. More
                NavigationBarItem(
                    selected = currentSection == AppSection.MORE || currentSection == AppSection.GALLERY || currentSection == AppSection.DAILY_ARRIVALS || currentSection == AppSection.PROPRIETOR || currentSection == AppSection.ABOUT,
                    onClick = { viewModel.navigateTo(AppSection.MORE) },
                    icon = { Icon(Icons.Default.Menu, contentDescription = "More") },
                    label = { Text("More", fontSize = 11.sp, fontWeight = if (currentSection == AppSection.MORE) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MelaGoldLight,
                        selectedTextColor = MelaGoldLight,
                        unselectedIconColor = Color(0xFFE5D5C5),
                        unselectedTextColor = Color(0xFFE5D5C5),
                        indicatorColor = Color(0xFF9E1B1B)
                    ),
                    modifier = Modifier.testTag("nav_more")
                )
            }
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentSection) {
                AppSection.HOME -> {
                    HomeScreen(
                        viewModel = viewModel,
                        dailyArrivals = dailyArrivals,
                        appVersion = appVersion,
                        onNavigate = { section -> viewModel.navigateTo(section) },
                        onOpenSafeAppInfo = { showSafeAppDialog = true },
                        onOpenSecurityLogin = { showSecurityLoginDialog = true },
                        onOpenUpdateCenter = { showUpdateDialog = true }
                    )
                }

                AppSection.VARIETIES -> {
                    VarietiesScreen(
                        viewModel = viewModel
                    )
                }

                AppSection.SEARCH -> {
                    SearchScreen(
                        viewModel = viewModel
                    )
                }

                AppSection.DAILY_ARRIVALS -> {
                    DailyArrivalsScreen(
                        viewModel = viewModel,
                        dailyArrivals = dailyArrivals
                    )
                }

                AppSection.GALLERY -> {
                    GalleryScreen()
                }

                AppSection.MORE -> {
                    MoreScreen(
                        session = session,
                        appVersion = appVersion,
                        onNavigate = { section -> viewModel.navigateTo(section) },
                        onOpenSafeAppInfo = { showSafeAppDialog = true },
                        onOpenSecurityLogin = { showSecurityLoginDialog = true },
                        onOpenUpdateCenter = { showUpdateDialog = true }
                    )
                }

                AppSection.VISIT_SHOP,
                AppSection.PROPRIETOR,
                AppSection.CONTACT,
                AppSection.ABOUT -> {
                    VisitContactScreen(
                        appVersion = appVersion,
                        onOpenSafeAppInfo = { showSafeAppDialog = true },
                        onOpenUpdateCenter = { showUpdateDialog = true }
                    )
                }
            }
        }
    }

    // WhatsApp Us Popup Dialog
    if (showWhatsAppDialog) {
        AlertDialog(
            onDismissRequest = { showWhatsAppDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.Default.Chat,
                    contentDescription = null,
                    tint = WhatsAppGreen,
                    modifier = Modifier.size(32.dp)
                )
            },
            title = {
                Text(
                    text = "WhatsApp Karachi Sale Mela",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Surface(
                        color = Color(0xFFFFF3CD),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "WhatsApp Only. No Phone Calls.",
                            color = Color(0xFF856404),
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(8.dp)
                        )
                    }

                    Text(
                        text = "Choose who you would like to message:",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

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
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showWhatsAppDialog = false }) {
                    Text("Close")
                }
            }
        )
    }

    // Safe App Verified & Not Harmful Dialog
    if (showSafeAppDialog) {
        SafeAppDialog(
            onDismiss = { showSafeAppDialog = false }
        )
    }

    // Security Login Dialog
    if (showSecurityLoginDialog) {
        SecurityLoginDialog(
            session = session,
            onManagerLogin = { pin -> viewModel.loginAsManager(pin) },
            onCustomerLogin = { name, phone, city -> viewModel.loginAsCustomer(name, phone, city) },
            onLogout = { viewModel.logout() },
            onUpdateNotice = { notice -> viewModel.updateNotice(notice) },
            onShowSafeAppInfo = {
                showSecurityLoginDialog = false
                showSafeAppDialog = true
            },
            onDismiss = { showSecurityLoginDialog = false }
        )
    }

    // App Update & Version Center Dialog
    if (showUpdateDialog) {
        val autoCheckEnabled by viewModel.autoCheckUpdates.collectAsStateWithLifecycle()
        val lastCheckTime by viewModel.lastUpdateCheck.collectAsStateWithLifecycle()
        AppUpdateDialog(
            session = session,
            installedVersion = appVersion,
            isUpdateApplied = isUpdateApplied,
            autoCheckEnabled = autoCheckEnabled,
            lastCheckTime = lastCheckTime,
            onToggleAutoCheck = { viewModel.setAutoCheckUpdates(it) },
            onRecordCheck = { viewModel.recordUpdateCheck() },
            onApplyUpdate = { newVer -> viewModel.applyAppUpdate(newVer) },
            onOpenNoticeEditor = {
                showUpdateDialog = false
                showSecurityLoginDialog = true
            },
            onDismiss = { showUpdateDialog = false }
        )
    }
}
