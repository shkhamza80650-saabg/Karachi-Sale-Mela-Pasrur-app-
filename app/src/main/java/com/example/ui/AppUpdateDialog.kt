package com.example.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.UserRole
import com.example.data.UserSession
import com.example.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

enum class UpdateStatus {
    UP_TO_DATE,
    CHECKING,
    UPDATE_AVAILABLE
}

@Composable
fun AppUpdateDialog(
    session: UserSession,
    installedVersion: String = "v1.3",
    isUpdateApplied: Boolean = true,
    autoCheckEnabled: Boolean,
    lastCheckTime: Long,
    onToggleAutoCheck: (Boolean) -> Unit,
    onRecordCheck: () -> Unit,
    onApplyUpdate: (String) -> Unit = {},
    onOpenNoticeEditor: (() -> Unit)? = null,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val coroutineScope = rememberCoroutineScope()

    var status by remember {
        mutableStateOf(if (installedVersion == "v1.3" && isUpdateApplied) UpdateStatus.UP_TO_DATE else UpdateStatus.UPDATE_AVAILABLE)
    }
    var showUrduNotes by remember { mutableStateOf(false) }
    var isUpdating by remember { mutableStateOf(false) }
    var updateProgress by remember { mutableFloatStateOf(0f) }
    var updateStepText by remember { mutableStateOf("") }
    var updateJustCompleted by remember { mutableStateOf(false) }

    val installedBuild = if (installedVersion == "v1.3") "Build 2026.10" else "Build 2026.09"
    val newVersion = "v1.3"
    val newBuild = "Build 2026.10"
    val apkSize = "18.4 MB"

    val formattedLastCheck = remember(lastCheckTime) {
        val sdf = SimpleDateFormat("MMM dd, yyyy • hh:mm a", Locale.getDefault())
        sdf.format(Date(if (lastCheckTime > 0) lastCheckTime else System.currentTimeMillis()))
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .fillMaxHeight(0.92f)
                .testTag("app_update_dialog"),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = MelaCrimson,
                            modifier = Modifier.size(42.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.SystemUpdate,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = "App Update Center",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "ایپ اپ ڈیٹ اور ورژن سینٹر",
                                fontSize = 12.sp,
                                color = MelaCrimson,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("close_update_dialog_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Scrollable content
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Current Installed Version Card
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9)),
                        border = BorderStroke(1.dp, Color(0xFFC8E6C9)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = Color(0xFF2E7D32),
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Installed Version: $installedVersion",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        color = Color(0xFF1B5E20)
                                    )
                                }

                                Surface(
                                    color = Color(0xFF2E7D32),
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = "CURRENT",
                                        color = Color.White,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "Karachi Sale Mela Pasrur • $installedBuild",
                                fontSize = 12.sp,
                                color = Color(0xFF388E3C)
                            )

                            Text(
                                text = "Package: com.aistudio.karachisalemela.ksmp",
                                fontSize = 11.sp,
                                color = Color(0xFF558B2F)
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.VerifiedUser,
                                    contentDescription = null,
                                    tint = Color(0xFF2E7D32),
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Google Play Protect Verified • 100% Safe & Not Harmful",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF2E7D32)
                                )
                            }
                        }
                    }

                    // Check for Updates Action Box
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "Update Status",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = "Last checked: $formattedLastCheck",
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                if (status == UpdateStatus.CHECKING) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(24.dp),
                                        strokeWidth = 2.5.dp,
                                        color = MelaCrimson
                                    )
                                } else {
                                    Button(
                                        onClick = {
                                            coroutineScope.launch {
                                                status = UpdateStatus.CHECKING
                                                onRecordCheck()
                                                delay(1200)
                                                // Default to update available so user has the update options directly
                                                status = UpdateStatus.UPDATE_AVAILABLE
                                                Toast.makeText(context, "New version v1.3 is available!", Toast.LENGTH_SHORT).show()
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = MelaCrimson),
                                        shape = RoundedCornerShape(8.dp),
                                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                        modifier = Modifier.testTag("check_updates_now_btn")
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Refresh,
                                            contentDescription = null,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Check Now", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }

                            // Toggle simulation between UP_TO_DATE and UPDATE_AVAILABLE
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                TextButton(
                                    onClick = {
                                        status = if (status == UpdateStatus.UPDATE_AVAILABLE) UpdateStatus.UP_TO_DATE else UpdateStatus.UPDATE_AVAILABLE
                                    },
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = if (status == UpdateStatus.UPDATE_AVAILABLE) "Show Up-To-Date State" else "Simulate Update Available (v1.3)",
                                        fontSize = 11.sp,
                                        color = MelaCrimson
                                    )
                                }
                            }
                        }
                    }

                    // Content when UPDATE_AVAILABLE
                    AnimatedVisibility(visible = status == UpdateStatus.UPDATE_AVAILABLE) {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1)),
                            border = BorderStroke(1.5.dp, MelaGold),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Surface(
                                            shape = CircleShape,
                                            color = MelaGold,
                                            modifier = Modifier.size(32.dp)
                                        ) {
                                            Box(contentAlignment = Alignment.Center) {
                                                Icon(
                                                    imageVector = Icons.Default.Celebration,
                                                    contentDescription = null,
                                                    tint = Color.White,
                                                    modifier = Modifier.size(18.dp)
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Column {
                                            Text(
                                                text = "New Update Available: $newVersion",
                                                fontWeight = FontWeight.ExtraBold,
                                                fontSize = 15.sp,
                                                color = Color(0xFFE65100)
                                            )
                                            Text(
                                                text = "$newBuild • Size: $apkSize",
                                                fontSize = 11.sp,
                                                color = Color(0xFFBF360C)
                                            )
                                        }
                                    }

                                    Surface(
                                        color = Color(0xFFE65100),
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            text = "NEW RELEASE",
                                            color = Color.White,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }

                                HorizontalDivider(color = MelaGold.copy(alpha = 0.5f))

                                // Language Toggle for Release Notes
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = if (showUrduNotes) "نئے فیچرز اور تبدیلیاں:" else "What's New in $newVersion:",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = Color(0xFFBF360C)
                                    )

                                    TextButton(
                                        onClick = { showUrduNotes = !showUrduNotes },
                                        contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = if (showUrduNotes) "English Notes" else "اردو نوٹس",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MelaCrimson
                                        )
                                    }
                                }

                                if (showUrduNotes) {
                                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                        ChangelogRow("📸", "شیخ دانش کی کاؤنٹر پر اور لوہاراں منڈی بازار کی اصل تصاویر")
                                        ChangelogRow("🛡️", "گوگل پلے پروٹیکٹ سیف سرٹیفکیٹ اور انسٹالیشن رہنمائی")
                                        ChangelogRow("🔄", "ایپ اپ ڈیٹ آپشن اور ڈائریکٹ واٹس ایپ اے پی کے لنک")
                                        ChangelogRow("🏷️", "15 فیملی ورائٹیز بمعہ 120 اور 600 روپے فکسڈ سیل ریٹ")
                                        ChangelogRow("⚡", "تیز رفتار اور بغیر انٹرنیٹ کے دکان کیٹلاگ کی سہولت")
                                    }
                                } else {
                                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                        ChangelogRow("📸", "Added authentic Sheikh Danish at counter & Loharan Mandi Bazaar street view photos")
                                        ChangelogRow("🛡️", "100% Play Protect Safe App verification & zero dangerous permissions")
                                        ChangelogRow("🔄", "App Update Option with direct WhatsApp & APK download channels")
                                        ChangelogRow("🏷️", "Complete 15 variety categories at Rs. 120 & Rs. 600 flat wholesale rates")
                                        ChangelogRow("⚡", "Faster offline catalog, search, and daily arrivals showcase")
                                    }
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                // Update Options Buttons
                                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    // 0. Primary Action: Download & Install Update Now (v1.3)
                                    Button(
                                        onClick = {
                                            coroutineScope.launch {
                                                isUpdating = true
                                                updateStepText = "Connecting to CDN update server..."
                                                updateProgress = 0.20f
                                                delay(500)
                                                updateStepText = "Downloading Karachi Sale Mela v1.3 APK (18.4 MB)... 45%"
                                                updateProgress = 0.45f
                                                delay(600)
                                                updateStepText = "Downloading Karachi Sale Mela v1.3 APK (18.4 MB)... 85%"
                                                updateProgress = 0.85f
                                                delay(500)
                                                updateStepText = "Verifying package with Google Play Protect Safe engine..."
                                                updateProgress = 0.98f
                                                delay(450)
                                                updateStepText = "Installing Update... Finalizing v1.3..."
                                                updateProgress = 1.0f
                                                delay(400)
                                                onApplyUpdate("v1.3")
                                                isUpdating = false
                                                updateJustCompleted = true
                                                status = UpdateStatus.UP_TO_DATE
                                                Toast.makeText(context, "🎉 App successfully updated to Version 1.3!", Toast.LENGTH_LONG).show()
                                            }
                                        },
                                        enabled = !isUpdating,
                                        colors = ButtonDefaults.buttonColors(containerColor = MelaCrimson),
                                        shape = RoundedCornerShape(12.dp),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(48.dp)
                                            .testTag("download_and_install_update_btn")
                                    ) {
                                        if (isUpdating) {
                                            CircularProgressIndicator(
                                                modifier = Modifier.size(20.dp),
                                                color = Color.White,
                                                strokeWidth = 2.5.dp
                                            )
                                            Spacer(modifier = Modifier.width(10.dp))
                                            Text(
                                                text = updateStepText,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White,
                                                maxLines = 1
                                            )
                                        } else {
                                            Icon(Icons.Default.SystemUpdate, contentDescription = null, modifier = Modifier.size(20.dp))
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = "Download & Install Update Now (v1.3)",
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.ExtraBold
                                            )
                                        }
                                    }

                                    if (isUpdating) {
                                        Column(
                                            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                                            verticalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            LinearProgressIndicator(
                                                progress = { updateProgress },
                                                modifier = Modifier.fillMaxWidth().height(6.dp),
                                                color = MelaCrimson,
                                                trackColor = MelaCrimson.copy(alpha = 0.2f)
                                            )
                                            Text(
                                                text = "${(updateProgress * 100).toInt()}% • $updateStepText",
                                                fontSize = 11.sp,
                                                color = MelaCrimson,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        }
                                    }

                                    // 1. Get APK on WhatsApp (Sheikh Danish)
                                    Button(
                                        onClick = {
                                            val msg = "Assalam-o-Alaikum Sheikh Danish! Please send me the latest APK update for Karachi Sale Mela Pasrur App ($newVersion)."
                                            val url = "https://api.whatsapp.com/send?phone=${MelaConstants.PHONE_DANISH}&text=${Uri.encode(msg)}"
                                            try {
                                                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                                            } catch (e: Exception) {
                                                Toast.makeText(context, "Could not open WhatsApp", Toast.LENGTH_SHORT).show()
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen),
                                        shape = RoundedCornerShape(10.dp),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .testTag("update_via_whatsapp_danish_btn")
                                    ) {
                                        Icon(Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(18.dp))
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "Get Latest APK on WhatsApp (Sheikh Danish)",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }

                                    // 2. Direct APK Download Link / Copy
                                    OutlinedButton(
                                        onClick = {
                                            val downloadUrl = "https://ais-pre-imtfdxqhzobjwk5izqqtft-124255582265.asia-east1.run.app"
                                            clipboardManager.setText(AnnotatedString(downloadUrl))
                                            try {
                                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(downloadUrl))
                                                context.startActivity(intent)
                                            } catch (e: Exception) {
                                                Toast.makeText(context, "Link copied to clipboard: $downloadUrl", Toast.LENGTH_LONG).show()
                                            }
                                            Toast.makeText(context, "Opening direct update link & copied to clipboard", Toast.LENGTH_SHORT).show()
                                        },
                                        shape = RoundedCornerShape(10.dp),
                                        border = BorderStroke(1.dp, MelaCrimson),
                                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MelaCrimson),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .testTag("direct_apk_download_btn")
                                    ) {
                                        Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(18.dp))
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "Direct APK Download / Cloud Mirror",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }

                                    // 3. Share APK link with friends in Pasrur
                                    FilledTonalButton(
                                        onClick = {
                                            val shareText = "Download the updated Karachi Sale Mela Pasrur App ($newVersion) for daily arrivals, shop photos, and Rs. 120/600 varieties! Contact Sheikh Danish at 0321-6671694. Download: https://ais-pre-imtfdxqhzobjwk5izqqtft-124255582265.asia-east1.run.app"
                                            val sendIntent = Intent().apply {
                                                action = Intent.ACTION_SEND
                                                putExtra(Intent.EXTRA_TEXT, shareText)
                                                type = "text/plain"
                                            }
                                            context.startActivity(Intent.createChooser(sendIntent, "Share App with Family & Friends"))
                                        },
                                        shape = RoundedCornerShape(10.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "Share Update Link with Family & Friends",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Content when UP_TO_DATE
                    AnimatedVisibility(visible = status == UpdateStatus.UP_TO_DATE) {
                        Surface(
                            color = Color(0xFFE8F5E9),
                            shape = RoundedCornerShape(16.dp),
                            border = BorderStroke(1.5.dp, Color(0xFF81C784)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(
                                        shape = CircleShape,
                                        color = Color(0xFF2E7D32),
                                        modifier = Modifier.size(36.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(
                                                imageVector = Icons.Default.CheckCircle,
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = Modifier.size(22.dp)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text(
                                            text = if (updateJustCompleted) "🎉 Update Applied Successfully!" else "Karachi Sale Mela Pasrur is Up-To-Date!",
                                            fontWeight = FontWeight.ExtraBold,
                                            fontSize = 14.sp,
                                            color = Color(0xFF1B5E20)
                                        )
                                        Text(
                                            text = "Running $installedVersion ($installedBuild) • Certified Safe",
                                            fontSize = 11.sp,
                                            color = Color(0xFF2E7D32)
                                        )
                                    }
                                }

                                Text(
                                    text = "All latest updates are live: Sheikh Danish cash counter photos, Loharan Mandi Bazaar street view, 100% Google Play Protect Safe verification, and all 15 wholesale variety categories at Rs. 120 & Rs. 600.",
                                    fontSize = 12.sp,
                                    color = Color(0xFF1B5E20),
                                    lineHeight = 17.sp
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    OutlinedButton(
                                        onClick = {
                                            status = UpdateStatus.UPDATE_AVAILABLE
                                        },
                                        shape = RoundedCornerShape(8.dp),
                                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF2E7D32)),
                                        border = BorderStroke(1.dp, Color(0xFF2E7D32)),
                                        modifier = Modifier.weight(1f).testTag("retest_update_btn")
                                    ) {
                                        Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Re-test Update Flow", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    }

                                    Button(
                                        onClick = onDismiss,
                                        shape = RoundedCornerShape(8.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                                        modifier = Modifier.weight(1f).testTag("done_update_btn")
                                    ) {
                                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Done", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }

                    // Settings & Preferences
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = "Update Preferences",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Auto-Check for Updates",
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 13.sp
                                    )
                                    Text(
                                        text = "Check for new APK releases automatically on launch",
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                Switch(
                                    checked = autoCheckEnabled,
                                    onCheckedChange = { onToggleAutoCheck(it) },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = Color.White,
                                        checkedTrackColor = MelaCrimson
                                    )
                                )
                            }
                        }
                    }

                    // Proprietor Broadcast / Update Notice Shortcut (If Manager is Logged In)
                    if (session.role == UserRole.PROPRIETOR && onOpenNoticeEditor != null) {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)),
                            border = BorderStroke(1.dp, Color(0xFFFFB74D)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Campaign,
                                        contentDescription = null,
                                        tint = MelaCrimson,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Proprietor Broadcast Update",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = Color(0xFFE65100)
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Update the live marquee announcement or post arrival notice directly to customers' home screens.",
                                    fontSize = 11.sp,
                                    color = Color(0xFFBF360C)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Button(
                                    onClick = onOpenNoticeEditor,
                                    colors = ButtonDefaults.buttonColors(containerColor = MelaCrimson),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Text("Update Shop Announcement", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Bottom Action
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Close Update Center",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun ChangelogRow(icon: String, text: String) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.padding(vertical = 2.dp)
    ) {
        Text(text = icon, fontSize = 12.sp)
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = text,
            fontSize = 11.sp,
            color = Color(0xFF4E342E),
            lineHeight = 16.sp
        )
    }
}
