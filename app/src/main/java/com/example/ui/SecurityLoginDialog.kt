package com.example.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.UserRole
import com.example.data.UserSession
import com.example.ui.theme.MelaCrimson
import com.example.ui.theme.WhatsAppGreen

@Composable
fun SecurityLoginDialog(
    session: UserSession,
    onManagerLogin: (pin: String) -> Boolean,
    onCustomerLogin: (name: String, phone: String, city: String) -> Boolean,
    onLogout: () -> Unit,
    onUpdateNotice: (notice: String) -> Unit,
    onShowSafeAppInfo: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedTab by remember { mutableIntStateOf(if (session.isLoggedIn && session.role == UserRole.PROPRIETOR) 0 else 1) }

    // Manager Login States
    var managerPin by remember { mutableStateOf("") }
    var pinError by remember { mutableStateOf(false) }
    var showPin by remember { mutableStateOf(false) }

    // Customer Login States
    var customerName by remember { mutableStateOf(if (session.role == UserRole.CUSTOMER) session.name else "") }
    var customerPhone by remember { mutableStateOf(if (session.role == UserRole.CUSTOMER) session.phone else "") }
    var customerCity by remember { mutableStateOf(if (session.role == UserRole.CUSTOMER) session.city else "Pasrur") }

    // Notice update
    var showEditNoticeDialog by remember { mutableStateOf(false) }
    var newNoticeText by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = modifier
                .fillMaxWidth(0.94f)
                .fillMaxHeight(0.88f)
                .testTag("security_login_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                // Header
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Surface(
                        shape = CircleShape,
                        color = MelaCrimson.copy(alpha = 0.12f),
                        modifier = Modifier.size(44.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = if (session.isLoggedIn) Icons.Default.LockOpen else Icons.Default.Lock,
                                contentDescription = "Security",
                                tint = MelaCrimson,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Security & Login",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = if (session.isLoggedIn) "Active Session: ${session.name}" else "Safe & Secure Access",
                            fontSize = 12.sp,
                            color = if (session.isLoggedIn) Color(0xFF2E7D32) else MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                // If already logged in, show Session Dashboard
                if (session.isLoggedIn) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(scrollState),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // User Profile Card
                        Surface(
                            color = if (session.role == UserRole.PROPRIETOR) Color(0xFFFFF3E0) else Color(0xFFE8F5E9),
                            shape = RoundedCornerShape(16.dp),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (session.role == UserRole.PROPRIETOR) Color(0xFFFFB74D) else Color(0xFFA5D6A7)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = if (session.role == UserRole.PROPRIETOR) MelaCrimson else Color(0xFF2E7D32),
                                    modifier = Modifier.size(48.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = if (session.role == UserRole.PROPRIETOR) Icons.Default.AdminPanelSettings else Icons.Default.Person,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(28.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(14.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Surface(
                                        color = if (session.role == UserRole.PROPRIETOR) MelaCrimson else Color(0xFF2E7D32),
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            text = session.role.displayName.uppercase(),
                                            color = Color.White,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = session.name,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                    if (session.phone.isNotBlank()) {
                                        Text(
                                            text = "Phone: ${session.phone} • ${session.city}",
                                            fontSize = 12.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }

                        // Manager Privileges Section
                        if (session.role == UserRole.PROPRIETOR) {
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = "Manager Actions & Controls",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))

                                    Button(
                                        onClick = { showEditNoticeDialog = true },
                                        colors = ButtonDefaults.buttonColors(containerColor = MelaCrimson),
                                        shape = RoundedCornerShape(10.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Icon(Icons.Default.Campaign, contentDescription = null, modifier = Modifier.size(18.dp))
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("Update Shop Notice / Sale Announcement")
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    OutlinedButton(
                                        onClick = onShowSafeAppInfo,
                                        shape = RoundedCornerShape(10.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Icon(Icons.Default.Security, contentDescription = null, modifier = Modifier.size(18.dp))
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("View Safe App Certificate & Play Protect")
                                    }
                                }
                            }
                        }

                        // Security Status Notice
                        Surface(
                            color = Color(0xFFE8F5E9),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "100% Safe App Session • No Sensitive Permissions • AES Encrypted",
                                    fontSize = 11.sp,
                                    color = Color(0xFF1B5E20),
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        // Logout Button
                        Button(
                            onClick = {
                                onLogout()
                                Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth().height(46.dp)
                        ) {
                            Icon(Icons.Default.ExitToApp, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Logout Current Session", fontWeight = FontWeight.Bold)
                        }
                    }
                } else {
                    // Tabs: Proprietor Login vs Customer Login
                    TabRow(
                        selectedTabIndex = selectedTab,
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MelaCrimson
                    ) {
                        Tab(
                            selected = selectedTab == 0,
                            onClick = { selectedTab = 0 },
                            text = { Text("Proprietor Login", fontWeight = FontWeight.Bold, fontSize = 13.sp) },
                            icon = { Icon(Icons.Default.Storefront, contentDescription = null, modifier = Modifier.size(18.dp)) }
                        )
                        Tab(
                            selected = selectedTab == 1,
                            onClick = { selectedTab = 1 },
                            text = { Text("Customer Access", fontWeight = FontWeight.Bold, fontSize = 13.sp) },
                            icon = { Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(18.dp)) }
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(scrollState),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        if (selectedTab == 0) {
                            // Proprietor / Manager Login View
                            Surface(
                                color = Color(0xFFFFF3E0),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.AdminPanelSettings, contentDescription = null, tint = Color(0xFFE65100), modifier = Modifier.size(20.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Login for Sheikh Danish & Sheikh Hamza to manage daily shop arrivals & announcements.",
                                        fontSize = 11.sp,
                                        color = Color(0xFFBF360C),
                                        lineHeight = 15.sp
                                    )
                                }
                            }

                            OutlinedTextField(
                                value = managerPin,
                                onValueChange = {
                                    if (it.length <= 6) {
                                        managerPin = it
                                        pinError = false
                                    }
                                },
                                label = { Text("Enter 4-digit Security PIN") },
                                placeholder = { Text("e.g. 6671") },
                                isError = pinError,
                                supportingText = {
                                    if (pinError) {
                                        Text("Incorrect PIN. Please try again.", color = MaterialTheme.colorScheme.error)
                                    } else {
                                        Text("Default Manager PIN: 6671 (or 1234)", fontSize = 11.sp)
                                    }
                                },
                                leadingIcon = {
                                    Icon(Icons.Default.VpnKey, contentDescription = null, tint = MelaCrimson)
                                },
                                trailingIcon = {
                                    IconButton(onClick = { showPin = !showPin }) {
                                        Icon(
                                            imageVector = if (showPin) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                            contentDescription = "Toggle PIN"
                                        )
                                    }
                                },
                                visualTransformation = if (showPin) VisualTransformation.None else PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier.fillMaxWidth().testTag("input_manager_pin")
                            )

                            Button(
                                onClick = {
                                    if (onManagerLogin(managerPin)) {
                                        Toast.makeText(context, "Welcome Sheikh Danish!", Toast.LENGTH_SHORT).show()
                                        onDismiss()
                                    } else {
                                        pinError = true
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = MelaCrimson),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth().height(48.dp).testTag("btn_manager_login")
                            ) {
                                Icon(Icons.Default.LockOpen, contentDescription = null, modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Login as Proprietor", fontWeight = FontWeight.Bold)
                            }
                        } else {
                            // Customer / Family Shopper Access View
                            Surface(
                                color = Color(0xFFE8F5E9),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(20.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "100% Free & Safe access for Pasrur families and shoppers to explore prices & contact shop.",
                                        fontSize = 11.sp,
                                        color = Color(0xFF1B5E20),
                                        lineHeight = 15.sp
                                    )
                                }
                            }

                            OutlinedTextField(
                                value = customerName,
                                onValueChange = { customerName = it },
                                label = { Text("Your Name") },
                                placeholder = { Text("e.g. Hamza / Danish") },
                                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier.fillMaxWidth().testTag("input_customer_name")
                            )

                            OutlinedTextField(
                                value = customerPhone,
                                onValueChange = { customerPhone = it },
                                label = { Text("WhatsApp Number (Optional)") },
                                placeholder = { Text("e.g. 0300-1234567") },
                                leadingIcon = { Icon(Icons.Default.Chat, contentDescription = null, tint = WhatsAppGreen) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier.fillMaxWidth().testTag("input_customer_phone")
                            )

                            OutlinedTextField(
                                value = customerCity,
                                onValueChange = { customerCity = it },
                                label = { Text("City / Town") },
                                placeholder = { Text("e.g. Pasrur") },
                                leadingIcon = { Icon(Icons.Default.LocationCity, contentDescription = null) },
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier.fillMaxWidth().testTag("input_customer_city")
                            )

                            Button(
                                onClick = {
                                    if (customerName.isNotBlank()) {
                                        onCustomerLogin(customerName, customerPhone, customerCity)
                                        Toast.makeText(context, "Welcome, $customerName!", Toast.LENGTH_SHORT).show()
                                        onDismiss()
                                    } else {
                                        Toast.makeText(context, "Please enter your name", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth().height(48.dp).testTag("btn_customer_login")
                            ) {
                                Icon(Icons.Default.ShoppingBag, contentDescription = null, modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Continue as Shopper", fontWeight = FontWeight.Bold)
                            }
                        }

                        // Safe App Banner at bottom
                        Surface(
                            color = Color(0xFFF1F8E9),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onShowSafeAppInfo() }
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(22.dp))
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "100% Safe App Verified",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = Color(0xFF1B5E20)
                                    )
                                    Text(
                                        text = "No Harmful Code • Play Protect Compliant • Tap to View Certificate",
                                        fontSize = 10.sp,
                                        color = Color(0xFF2E7D32)
                                    )
                                }
                                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color(0xFF2E7D32))
                            }
                        }
                    }
                }
            }
        }
    }

    // Edit Notice Dialog for Proprietor
    if (showEditNoticeDialog) {
        AlertDialog(
            onDismissRequest = { showEditNoticeDialog = false },
            title = { Text("Update Shop Announcement", fontWeight = FontWeight.Bold) },
            text = {
                OutlinedTextField(
                    value = newNoticeText,
                    onValueChange = { newNoticeText = it },
                    label = { Text("Notice Text") },
                    placeholder = { Text("e.g. New Eid Collection arrived! Everything at wholesale rates.") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newNoticeText.isNotBlank()) {
                            onUpdateNotice(newNoticeText)
                            Toast.makeText(context, "Announcement updated!", Toast.LENGTH_SHORT).show()
                        }
                        showEditNoticeDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MelaCrimson)
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditNoticeDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
