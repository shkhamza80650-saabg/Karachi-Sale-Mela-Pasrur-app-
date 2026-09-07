package com.example.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ZoomIn
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.R
import com.example.ui.theme.MelaCrimson

data class GalleryItem(
    val id: Int,
    val title: String,
    val subtitle: String,
    val imageRes: Int,
    val isFeatured: Boolean = false
)

@Composable
fun GalleryScreen(
    modifier: Modifier = Modifier
) {
    val galleryItems = listOf(
        GalleryItem(
            id = 1,
            title = "Shop Front & Entrance",
            subtitle = "Loharan Mandi Bazaar Pasrur shop front with floral pillars & Islamic calligraphy",
            imageRes = R.drawable.img_shop_front,
            isFeatured = true
        ),
        GalleryItem(
            id = 2,
            title = "Proprietor Sheikh Danish",
            subtitle = "Sheikh Danish at the shop counter welcoming customers with toys, clocks & mirrors",
            imageRes = R.drawable.img_proprietor,
            isFeatured = true
        ),
        GalleryItem(
            id = 3,
            title = "Official Store Showcase Poster",
            subtitle = "Complete family shopping poster with 15 variety categories at Rs. 120 & 600",
            imageRes = R.drawable.img_shop_poster,
            isFeatured = true
        ),
        GalleryItem(
            id = 4,
            title = "Shop Bachat Store Signboard",
            subtitle = "Karachi Sale Mela Bachat Store - Aap ki soch say b Sasta!",
            imageRes = R.drawable.img_shop_signboard,
            isFeatured = false
        ),
        GalleryItem(
            id = 5,
            title = "Official 3D KSM Emblem",
            subtitle = "Karachi Sale Mela Pasrur 3D Gold Medallion Logo",
            imageRes = R.drawable.img_app_icon,
            isFeatured = false
        ),
        GalleryItem(
            id = 6,
            title = "Shop Interior & Display Aisles",
            subtitle = "Spacious multi-floor racks with crockery, plastics & gift items",
            imageRes = R.drawable.img_shop_interior,
            isFeatured = false
        )
    )

    var selectedItem by remember { mutableStateOf<GalleryItem?>(null) }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .fillMaxSize()
            .testTag("gallery_screen"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item(span = { GridItemSpan(2) }) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Shop Gallery & Real Photos",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = "Experience our physical shopping environment. Tap any photo to enlarge.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                    )
                }
            }
        }

        items(galleryItems, key = { it.id }, span = { item ->
            if (item.isFeatured) GridItemSpan(2) else GridItemSpan(1)
        }) { item ->
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedItem = item }
                    .testTag("gallery_card_${item.id}")
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(if (item.isFeatured) 190.dp else 140.dp)
                    ) {
                        Image(
                            painter = painterResource(id = item.imageRes),
                            contentDescription = item.title,
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
                                            Color.Black.copy(alpha = 0.65f)
                                        )
                                    )
                                )
                        )

                        Icon(
                            imageVector = Icons.Default.ZoomIn,
                            contentDescription = "Zoom",
                            tint = Color.White.copy(alpha = 0.85f),
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(8.dp)
                                .size(24.dp)
                        )

                        Text(
                            text = item.title,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = if (item.isFeatured) 14.sp else 12.sp,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(10.dp)
                        )
                    }

                    Text(
                        text = item.subtitle,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(10.dp),
                        lineHeight = 15.sp
                    )
                }
            }
        }

        item(span = { GridItemSpan(2) }) {
            Spacer(modifier = Modifier.height(70.dp))
        }
    }

    // Lightbox / Fullscreen Dialog
    selectedItem?.let { item ->
        Dialog(
            onDismissRequest = { selectedItem = null },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.95f))
                    .clickable { selectedItem = null },
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
                        IconButton(onClick = { selectedItem = null }) {
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
                                .heightIn(max = 420.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = item.title,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

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
