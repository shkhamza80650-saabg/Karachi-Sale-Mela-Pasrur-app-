package com.example.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.R
import com.example.ui.theme.*
import kotlinx.coroutines.delay

data class VideoChapter(
    val timeSeconds: Int,
    val timeLabel: String,
    val title: String,
    val urduTitle: String,
    val icon: String,
    val highlightText: String
)

data class ShopVideo(
    val id: String,
    val title: String,
    val urduTitle: String,
    val durationSeconds: Int,
    val durationLabel: String,
    val thumbnailRes: Int,
    val description: String,
    val badgeText: String,
    val chapters: List<VideoChapter>
)

object ShopVideoCatalog {
    val storeWalkthroughVideo = ShopVideo(
        id = "interior_tour",
        title = "Full Mega Store Interior Tour",
        urduTitle = "دکان کا اندرونی مکمل ویڈیو وزٹ (3 منٹ)",
        durationSeconds = 181,
        durationLabel = "3:01",
        thumbnailRes = R.drawable.img_video_tour_interior,
        description = "Comprehensive walkthrough showing the entire store: wall clocks gallery, cash counter with Sheikh Danish, imported perfumes & cosmetics, handbags wall, plush soft toys, plastic household containers, stainless steel cookware, dinner sets, and crystal glassware.",
        badgeText = "3:01 FULL STORE TOUR",
        chapters = listOf(
            VideoChapter(0, "00:00", "Wall Clocks Gallery", "وال کلاک اور ڈیکوریشن آئینے", "⏰", "Designer decorative clocks and elegant framed wall mirrors"),
            VideoChapter(3, "00:03", "Cash Counter & Proprietor", "کاؤنٹر اور شیخ دانش صاحب", "👤", "Proprietor Sheikh Danish seated at the billing counter"),
            VideoChapter(6, "00:06", "Cosmetics & Perfumes", "کاسمیٹکس اور پرفیومز ریک", "💄", "Imported fragrances, lipsticks, makeup sets and body sprays"),
            VideoChapter(9, "00:09", "Ladies Handbags Wall", "لیڈیز ہینڈ بیگز اور پرس", "👜", "Exclusive wall of shoulder bags, totes, clutches & casual bags"),
            VideoChapter(15, "00:15", "Toys, Dolls & Plush Bears", "کھلونے اور پیارے ٹیڈی بیئر", "🧸", "Fluffy plush teddy bears, toy cars, and gifts for children"),
            VideoChapter(22, "00:22", "Rs. 120 & Rs. 600 Fixed Rates", "120 اور 600 روپے بچت بورڈز", "🏷️", "Official fixed price boards guaranteeing huge savings"),
            VideoChapter(30, "00:30", "Plastic Crockery & Storage", "پلاسٹک برتن اور ٹوکریاں", "🪣", "Unbreakable tubs, food boxes, laundry baskets & organizers"),
            VideoChapter(38, "00:38", "Steel Cookware & Pots", "سٹیل کڑاہی اور پتیلے", "🍳", "Stainless steel cooking pots, pans, bowls & serving cutlery"),
            VideoChapter(47, "00:47", "Melamine Dinner Sets", "میلامائن ڈنر سیٹ اور ٹرے", "🍽️", "Royal floral print melamine plates, soup bowls & tea sets"),
            VideoChapter(55, "00:55", "Glass Items & Jug Sets", "شیشے کے جگ اور گلاس", "🥛", "Crystal clear glass water jugs, drinking glasses & juice sets"),
            VideoChapter(65, "01:05", "Cleaning Mops & Household", "صفائی کے موپس اور وائپر", "🧹", "Brooms, floor wipers, dustbins & everyday cleaning supplies"),
            VideoChapter(85, "01:25", "Backpacks & Bazaar Exit", "سکول بیگز اور بازار کا رخ", "🎒", "School bags, travel backpacks, outdoor bazaar entrance view")
        )
    )

    val exteriorTourVideo = ShopVideo(
        id = "exterior_tour",
        title = "Shopfront & Bazaar Walk Tour",
        urduTitle = "لوہاراں منڈی بازار اور دکان کا بیرونی منظر",
        durationSeconds = 17,
        durationLabel = "0:17",
        thumbnailRes = R.drawable.img_video_tour_exterior,
        description = "Exterior camera walk through Loharan Mandi Bazaar showing the prominent yellow Karachi Sale Mela signboard, motorcycle street parking, roadside Shawarma stall, and storefront display of plastic tubs and mops.",
        badgeText = "0:17 EXTERIOR WALK",
        chapters = listOf(
            VideoChapter(0, "00:00", "Loharan Mandi Bazaar", "لوہاراں منڈی بازار روڈ", "🏍️", "Bustling bazaar street with shoppers, rickshaws, and motorcycles"),
            VideoChapter(4, "00:04", "Yellow Sale Mela Signboard", "کراچی سیل میلہ بڑا بورڈ", "📢", "Yellow overhead banner: Bachat Store Aap ki soch say b Sasta!"),
            VideoChapter(8, "00:08", "Outdoor Displays & Tubs", "باہر لگے ہوئے برتن اور موپس", "🪣", "Household buckets, laundry baskets, brooms and hanging bags"),
            VideoChapter(13, "00:13", "Shop Entrance & Glass Door", "دکان کا شیشے والا گیٹ", "🚪", "Entrance adorned with Kalima Tayyaba calligraphy")
        )
    )

    val allVideos = listOf(storeWalkthroughVideo, exteriorTourVideo)
}

@Composable
fun ShopVideoPlayerDialog(
    video: ShopVideo,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var isPlaying by remember { mutableStateOf(true) }
    var currentPositionSeconds by remember { mutableIntStateOf(0) }
    var isMuted by remember { mutableStateOf(false) }

    // Simulation of video playback timer
    LaunchedEffect(isPlaying, video.id) {
        while (isPlaying) {
            delay(1000L)
            if (currentPositionSeconds < video.durationSeconds) {
                currentPositionSeconds += 1
            } else {
                currentPositionSeconds = 0
            }
        }
    }

    // Identify current chapter
    val currentChapter = remember(currentPositionSeconds, video.chapters) {
        video.chapters.lastOrNull { it.timeSeconds <= currentPositionSeconds }
            ?: video.chapters.firstOrNull()
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            color = Color.Black.copy(alpha = 0.95f),
            modifier = Modifier
                .fillMaxSize()
                .testTag("video_player_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp, bottom = 24.dp)
            ) {
                // Top Action Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = MelaCrimson,
                            modifier = Modifier.size(28.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = video.title,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = video.urduTitle,
                                color = MelaGoldLight,
                                fontSize = 12.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .background(Color.White.copy(alpha = 0.15f), CircleShape)
                            .size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Video Display Container
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF1A1A1A))
                ) {
                    Image(
                        painter = painterResource(id = video.thumbnailRes),
                        contentDescription = video.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Cinematic gradient overlay
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        Color.Black.copy(alpha = 0.3f),
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.8f)
                                    )
                                )
                            )
                    )

                    // Center Play/Pause Overlay Indicator
                    Surface(
                        shape = CircleShape,
                        color = MelaBurgundy.copy(alpha = 0.85f),
                        border = androidx.compose.foundation.BorderStroke(2.dp, MelaGoldLight),
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(60.dp)
                            .clickable { isPlaying = !isPlaying }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = if (isPlaying) "Pause" else "Play",
                                tint = MelaGoldLight,
                                modifier = Modifier.size(34.dp)
                            )
                        }
                    }

                    // Live playback badge (Top Left)
                    Row(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = if (isPlaying) MelaCrimson else Color.DarkGray
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .background(Color.White, CircleShape)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (isPlaying) "HD STREAMING" else "PAUSED",
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.5.sp
                                )
                            }
                        }
                    }

                    // Sound Mute Toggle (Top Right)
                    IconButton(
                        onClick = { isMuted = !isMuted },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(12.dp)
                            .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                            .size(34.dp)
                    ) {
                        Icon(
                            imageVector = if (isMuted) Icons.Default.VolumeOff else Icons.Default.VolumeUp,
                            contentDescription = "Sound Toggle",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    // Bottom info on video
                    if (currentChapter != null) {
                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = currentChapter.icon, fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "${currentChapter.title} (${currentChapter.timeLabel})",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Timeline Scrubber & Controls
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Slider(
                        value = currentPositionSeconds.toFloat(),
                        onValueChange = { currentPositionSeconds = it.toInt() },
                        valueRange = 0f..video.durationSeconds.toFloat(),
                        colors = SliderDefaults.colors(
                            thumbColor = MelaTopBarGold,
                            activeTrackColor = MelaTopBarGold,
                            inactiveTrackColor = Color.White.copy(alpha = 0.3f)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = formatTime(currentPositionSeconds),
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = video.durationLabel,
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Player buttons: Skip -10, Play/Pause, Skip +10, Replay
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                currentPositionSeconds = (currentPositionSeconds - 10).coerceAtLeast(0)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Replay10,
                                contentDescription = "Back 10s",
                                tint = Color.White,
                                modifier = Modifier.size(26.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        FloatingActionButton(
                            onClick = { isPlaying = !isPlaying },
                            containerColor = MelaTopBarGold,
                            contentColor = Color(0xFF211400),
                            shape = CircleShape,
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = if (isPlaying) "Pause" else "Play",
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        IconButton(
                            onClick = {
                                currentPositionSeconds = (currentPositionSeconds + 10).coerceAtMost(video.durationSeconds)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Forward10,
                                contentDescription = "Forward 10s",
                                tint = Color.White,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Jump to Department Chapters Section
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "JUMP TO STORE DEPARTMENT (سیکشن منتخب کریں)",
                        color = MelaGoldLight,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(video.chapters) { chapter ->
                            val isSelected = currentChapter?.timeSeconds == chapter.timeSeconds
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = if (isSelected) MelaBurgundy else Color.White.copy(alpha = 0.1f),
                                border = androidx.compose.foundation.BorderStroke(
                                    1.dp,
                                    if (isSelected) MelaGoldLight else Color.White.copy(alpha = 0.25f)
                                ),
                                modifier = Modifier
                                    .clickable {
                                        currentPositionSeconds = chapter.timeSeconds
                                        isPlaying = true
                                    }
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = chapter.icon, fontSize = 14.sp)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Column {
                                        Text(
                                            text = chapter.title,
                                            color = if (isSelected) MelaGoldLight else Color.White,
                                            fontSize = 11.sp,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                        )
                                        Text(
                                            text = chapter.timeLabel,
                                            color = if (isSelected) Color.White else Color.White.copy(alpha = 0.6f),
                                            fontSize = 9.sp
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Current Chapter Detail Card
                    if (currentChapter != null) {
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.08f)),
                            border = androidx.compose.foundation.BorderStroke(1.dp, MelaGoldBorder.copy(alpha = 0.5f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(text = currentChapter.icon, fontSize = 22.sp)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(
                                            text = currentChapter.title,
                                            color = Color.White,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = currentChapter.urduTitle,
                                            color = MelaGoldLight,
                                            fontSize = 12.sp
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = currentChapter.highlightText,
                                    color = Color.White.copy(alpha = 0.85f),
                                    fontSize = 12.sp,
                                    lineHeight = 16.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // WhatsApp Action Button: Request Full HD Video from Danish
                    Button(
                        onClick = {
                            val msg = "Assalam-o-Alaikum Sheikh Danish! Please send me the complete HD shop video walkthrough of Karachi Sale Mela Pasrur."
                            val url = "https://api.whatsapp.com/send?phone=${MelaConstants.PHONE_DANISH}&text=${Uri.encode(msg)}"
                            try {
                                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                            } catch (_: Exception) {}
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = WhatsAppGreen,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Chat,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "GET FULL HD VIDEO ON WHATSAPP",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Share Video Button
                    OutlinedButton(
                        onClick = {
                            val sendIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "Watch Karachi Sale Mela Pasrur Video Tour!\n\n${video.title}\n${video.description}\n\nVisit Us: Loharan Mandi Bazaar, Pasrur\nContact: 0321-6671694"
                                )
                                type = "text/plain"
                            }
                            context.startActivity(Intent.createChooser(sendIntent, "Share Video Tour"))
                        },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.4f)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "SHARE VIDEO WITH FRIENDS",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

private fun formatTime(seconds: Int): String {
    val mins = seconds / 60
    val secs = seconds % 60
    return String.format("%02d:%02d", mins, secs)
}
