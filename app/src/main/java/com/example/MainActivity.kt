package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
    val context = LocalContext.current

    var showWhatsAppDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Karachi Sale Mela",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "Pasrur • Loharan Mandi Bazaar",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f),
                            maxLines = 1
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { viewModel.navigateTo(AppSection.HOME) }) {
                        Icon(
                            imageVector = Icons.Default.Store,
                            contentDescription = "Home Shop",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    // Google Maps direct action
                    IconButton(
                        onClick = { launchGoogleMaps(context) },
                        modifier = Modifier.testTag("topbar_maps_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Open in Google Maps",
                            tint = Color.White
                        )
                    }

                    // WhatsApp quick contact
                    IconButton(
                        onClick = { showWhatsAppDialog = true },
                        modifier = Modifier.testTag("topbar_whatsapp_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Chat,
                            contentDescription = "WhatsApp Us",
                            tint = WhatsAppGreen
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MelaCrimson,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp,
                windowInsets = WindowInsets.navigationBars,
                modifier = Modifier.testTag("bottom_nav_bar")
            ) {
                // 1. Home
                NavigationBarItem(
                    selected = currentSection == AppSection.HOME,
                    onClick = { viewModel.navigateTo(AppSection.HOME) },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        indicatorColor = MelaCrimson,
                        selectedTextColor = MelaCrimson
                    ),
                    modifier = Modifier.testTag("nav_home")
                )

                // 2. Varieties
                NavigationBarItem(
                    selected = currentSection == AppSection.VARIETIES,
                    onClick = { viewModel.navigateTo(AppSection.VARIETIES) },
                    icon = { Icon(Icons.Default.Category, contentDescription = "Varieties") },
                    label = { Text("Varieties", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        indicatorColor = MelaCrimson,
                        selectedTextColor = MelaCrimson
                    ),
                    modifier = Modifier.testTag("nav_varieties")
                )

                // 3. Daily Arrivals
                NavigationBarItem(
                    selected = currentSection == AppSection.DAILY_ARRIVALS,
                    onClick = { viewModel.navigateTo(AppSection.DAILY_ARRIVALS) },
                    icon = {
                        BadgedBox(
                            badge = {
                                if (dailyArrivals.isNotEmpty()) {
                                    Badge(
                                        containerColor = MelaGold,
                                        contentColor = Color.White
                                    ) {
                                        Text("${dailyArrivals.size}")
                                    }
                                }
                            }
                        ) {
                            Icon(Icons.Default.NewReleases, contentDescription = "Daily Arrivals")
                        }
                    },
                    label = { Text("Arrivals", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        indicatorColor = MelaCrimson,
                        selectedTextColor = MelaCrimson
                    ),
                    modifier = Modifier.testTag("nav_arrivals")
                )

                // 4. Gallery
                NavigationBarItem(
                    selected = currentSection == AppSection.GALLERY,
                    onClick = { viewModel.navigateTo(AppSection.GALLERY) },
                    icon = { Icon(Icons.Default.PhotoLibrary, contentDescription = "Gallery") },
                    label = { Text("Gallery", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        indicatorColor = MelaCrimson,
                        selectedTextColor = MelaCrimson
                    ),
                    modifier = Modifier.testTag("nav_gallery")
                )

                // 5. Visit & Contact
                NavigationBarItem(
                    selected = currentSection in listOf(AppSection.VISIT_SHOP, AppSection.PROPRIETOR, AppSection.CONTACT, AppSection.ABOUT),
                    onClick = { viewModel.navigateTo(AppSection.VISIT_SHOP) },
                    icon = { Icon(Icons.Default.Place, contentDescription = "Visit & Contact") },
                    label = { Text("Visit", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        indicatorColor = MelaCrimson,
                        selectedTextColor = MelaCrimson
                    ),
                    modifier = Modifier.testTag("nav_visit")
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
                        onNavigate = { section -> viewModel.navigateTo(section) }
                    )
                }

                AppSection.VARIETIES -> {
                    VarietiesScreen(
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

                AppSection.VISIT_SHOP,
                AppSection.PROPRIETOR,
                AppSection.CONTACT,
                AppSection.ABOUT -> {
                    VisitContactScreen()
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
}
