package com.example.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

object MelaConstants {
    const val APP_NAME = "Karachi Sale Mela Pasrur"
    const val PROPRIETOR_NAME = "Sheikh Danish"
    const val PROPRIETOR_TITLE = "Proprietor - Karachi Sale Mela Pasrur"
    
    const val PHONE_DANISH = "923216671694"
    const val DISPLAY_DANISH = "03216671694"
    
    const val PHONE_HAMZA = "923074029245"
    const val DISPLAY_HAMZA = "03074029245"

    const val SHOP_ADDRESS = "7M76+94R Karachi Sale Mela Pasrur, Near Naseem Hayat Shaheed Rd, Loharan Mandi Bazaar, Walled City, Pasrur, 51480"
    const val PLUS_CODE = "7M76+94R Pasrur"
    const val OPENING_HOURS = "09:30 AM to 09:00 PM"
    const val OPEN_DAYS = "Sunday to Saturday (Open 7 Days)"
    
    val SALE_RATES = listOf("Rs. 120", "Rs. 300", "Rs. 600", "Rs. 1,200")
}

fun launchWhatsApp(context: Context, phone: String, contactName: String) {
    try {
        val defaultMsg = "Assalam-o-Alaikum $contactName, I am contacting you regarding Karachi Sale Mela Pasrur varieties & shop visit."
        val url = "https://api.whatsapp.com/send?phone=$phone&text=${Uri.encode(defaultMsg)}"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "Could not open WhatsApp. Please check if WhatsApp is installed.", Toast.LENGTH_LONG).show()
    }
}

fun launchGoogleMaps(context: Context) {
    val query = "7M76+94R Karachi Sale Mela Pasrur, Near Naseem Hayat Shaheed Rd, Loharan Mandi Bazaar, Pasrur"
    try {
        val geoUri = Uri.parse("geo:0,0?q=${Uri.encode(query)}")
        val mapIntent = Intent(Intent.ACTION_VIEW, geoUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        context.startActivity(mapIntent)
    } catch (e: Exception) {
        val webUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=${Uri.encode(query)}")
        val webIntent = Intent(Intent.ACTION_VIEW, webUri)
        context.startActivity(webIntent)
    }
}

@Composable
fun SaleRateBadge(
    rate: String,
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor) = when (rate.trim()) {
        "Rs. 120" -> Pair(Rate120, Color.White)
        "Rs. 300" -> Pair(Rate300, Color.White)
        "Rs. 600" -> Pair(Rate600, Color.White)
        "Rs. 1,200" -> Pair(Rate1200, Color.White)
        else -> Pair(MaterialTheme.colorScheme.primary, Color.White)
    }

    Surface(
        color = bgColor,
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 2.dp,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = rate,
                color = textColor,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun WhatsAppActionButton(
    contactName: String,
    phoneNumber: String,
    displayPhone: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Button(
        onClick = { launchWhatsApp(context, phoneNumber, contactName) },
        colors = ButtonDefaults.buttonColors(
            containerColor = WhatsAppGreen,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(14.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .testTag("whatsapp_btn_${contactName.replace(" ", "_")}")
    ) {
        Icon(
            imageVector = Icons.Default.Chat,
            contentDescription = "WhatsApp",
            tint = Color.White,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "WhatsApp $contactName",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Text(
                text = "$displayPhone (WhatsApp Only)",
                fontSize = 11.sp,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}

@Composable
fun GoogleMapsActionButton(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Button(
        onClick = { launchGoogleMaps(context) },
        colors = ButtonDefaults.buttonColors(
            containerColor = MelaCrimson,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(14.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .testTag("google_maps_btn")
    ) {
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Map Location",
            tint = Color.White,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "Open in Google Maps",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )
    }
}
