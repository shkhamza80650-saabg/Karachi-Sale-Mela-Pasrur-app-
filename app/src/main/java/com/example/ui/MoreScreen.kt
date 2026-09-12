package com.example.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.UserSession
import com.example.ui.theme.*

@Composable
fun MoreScreen(
    session: UserSession,
    appVersion: String,
    onNavigate: (AppSection) -> Unit,
    onOpenSafeAppInfo: () -> Unit,
    onOpenSecurityLogin: () -> Unit,
    onOpenUpdateCenter: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MelaBackgroundLight)
            .testTag("more_screen"),
        contentPadding = PaddingValues(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // VIP Header Banner
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
                    .padding(20.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Surface(
                        shape = CircleShape,
                        color = Color.White,
                        shadowElevation = 6.dp,
                        modifier = Modifier.size(74.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_app_icon),
                            contentDescription = "Karachi Sale Mela Pasrur",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp)
                                .clip(CircleShape)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "KARACHI SALE MELA PASRUR",
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp,
                        letterSpacing = 1.sp
                    )

                    Surface(
                        color = MelaGoldLight,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Text(
                            text = "👑 VIP BRAND • MORE OPTIONS",
                            color = Color(0xFF211400),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (session.isLoggedIn) "Logged in as ${session.name} (${session.role})" else "All Kinds of Varieties Available • Loharan Mandi Bazaar",
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 12.sp
                    )
                }
            }
        }

        // Section: Key Features
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, MelaGoldBorder),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    MoreMenuItem(
                        icon = Icons.Default.NewReleases,
                        iconTint = MelaCrimson,
                        title = "Daily Arrivals (روزانہ نیا مال)",
                        subtitle = "Check brand new daily stock arrivals and prices",
                        onClick = { onNavigate(AppSection.DAILY_ARRIVALS) }
                    )
                    HorizontalDivider(color = MelaGoldBorder.copy(alpha = 0.4f), modifier = Modifier.padding(horizontal = 16.dp))

                    MoreMenuItem(
                        icon = Icons.Default.VideoLibrary,
                        iconTint = MelaCrimson,
                        title = "Shop Pictures & Videos (دکان کی ویڈیوز)",
                        subtitle = "Watch 3-minute video walkthrough, exterior bazaar walk & full photo gallery",
                        onClick = { onNavigate(AppSection.GALLERY) }
                    )
                    HorizontalDivider(color = MelaGoldBorder.copy(alpha = 0.4f), modifier = Modifier.padding(horizontal = 16.dp))

                    MoreMenuItem(
                        icon = Icons.Default.Person,
                        iconTint = MelaBurgundy,
                        title = "Proprietor Sheikh Danish (شیخ دانش صاحب)",
                        subtitle = "Cash counter photo, greeting & business profile",
                        onClick = { onNavigate(AppSection.PROPRIETOR) }
                    )
                }
            }
        }

        // Section: App Verification & Updates
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, MelaGoldBorder),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    MoreMenuItem(
                        icon = Icons.Default.VerifiedUser,
                        iconTint = Color(0xFF2E7D32),
                        title = "100% Safe App Verified (محفوظ ایپ)",
                        subtitle = "Google Play Protect verified • Zero sensitive permissions",
                        onClick = onOpenSafeAppInfo
                    )
                    HorizontalDivider(color = MelaGoldBorder.copy(alpha = 0.4f), modifier = Modifier.padding(horizontal = 16.dp))

                    MoreMenuItem(
                        icon = Icons.Default.SystemUpdate,
                        iconTint = MelaGoldPrimary,
                        title = "App Update Center ($appVersion)",
                        subtitle = "Check latest version, updates and release notes",
                        onClick = onOpenUpdateCenter
                    )
                    HorizontalDivider(color = MelaGoldBorder.copy(alpha = 0.4f), modifier = Modifier.padding(horizontal = 16.dp))

                    MoreMenuItem(
                        icon = if (session.isLoggedIn) Icons.Default.LockOpen else Icons.Default.Lock,
                        iconTint = if (session.isLoggedIn) MelaCrimson else Color(0xFF554440),
                        title = if (session.isLoggedIn) "Admin Session (${session.name})" else "Manager & Security Login",
                        subtitle = if (session.isLoggedIn) "Tap to manage notices or logout" else "Proprietor PIN login for announcements & edit options",
                        onClick = onOpenSecurityLogin
                    )
                }
            }
        }

        // Section: Direct Support & Contacts
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, MelaGoldBorder),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = MelaBurgundy,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.Phone, contentDescription = null, tint = MelaGoldLight, modifier = Modifier.size(18.dp))
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "OUR CONTACTS & PROPRIETORS",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = MelaBurgundy
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    val contacts = listOf(
                        Triple("Sheikh Danish", MelaConstants.PHONE_DANISH, "0321-6671694"),
                        Triple("Sheikh Hamza", MelaConstants.PHONE_HAMZA, "0307-4029245"),
                        Triple("Sheikh Amran", MelaConstants.PHONE_AMRAN, "03217624996")
                    )

                    contacts.forEach { (name, phone, display) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = Color(0xFF211400),
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = display,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = MelaBurgundy
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            IconButton(
                                onClick = {
                                    val msg = "Assalam-o-Alaikum $name, I am contacting you regarding Karachi Sale Mela Pasrur."
                                    val url = "https://api.whatsapp.com/send?phone=$phone&text=${Uri.encode(msg)}"
                                    try {
                                        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                                    } catch (_: Exception) {}
                                },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(Icons.Default.Chat, contentDescription = "WhatsApp", tint = WhatsAppGreen, modifier = Modifier.size(18.dp))
                            }
                        }
                    }
                }
            }
        }

        // Section: Share App
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9E6)),
                border = androidx.compose.foundation.BorderStroke(1.dp, MelaGoldBorder),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable {
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(
                                Intent.EXTRA_TEXT,
                                "Visit *Karachi Sale Mela Pasrur* (VIP Brand) at Loharan Mandi Bazaar! Fixed Rate varieties in Rs. 120, 300, 600, 1,200. Contact Sheikh Danish: 0321-6671694"
                            )
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Share Karachi Sale Mela"))
                    }
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = MelaBurgundy,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Share, contentDescription = "Share", tint = MelaGoldLight, modifier = Modifier.size(20.dp))
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Share Karachi Sale Mela App",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color(0xFF211400)
                        )
                        Text(
                            text = "Share with family and friends on WhatsApp",
                            fontSize = 11.sp,
                            color = Color(0xFF554440)
                        )
                    }
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = MelaBurgundy)
                }
            }
        }
    }
}

@Composable
private fun MoreMenuItem(
    icon: ImageVector,
    iconTint: Color,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = CircleShape,
            color = iconTint.copy(alpha = 0.12f),
            modifier = Modifier.size(40.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(imageVector = icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(22.dp))
            }
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color(0xFF211400)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = Color(0xFF554440),
                lineHeight = 14.sp
            )
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(16.dp)
        )
    }
}
