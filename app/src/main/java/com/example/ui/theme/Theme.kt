package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = MelaGoldLight,
    onPrimary = Color(0xFF211400),
    primaryContainer = MelaBurgundyDark,
    onPrimaryContainer = Color.White,
    secondary = MelaGoldLight,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF5D3100),
    onSecondaryContainer = Color(0xFFFFDCC1),
    tertiary = WhatsAppGreen,
    onTertiary = Color.Black,
    tertiaryContainer = WhatsAppGreenDark,
    onTertiaryContainer = Color.White,
    background = MelaBackgroundDark,
    onBackground = Color(0xFFFBF4E8),
    surface = MelaSurfaceDark,
    onSurface = Color(0xFFFBF4E8),
    surfaceVariant = MelaSurfaceVariantDark,
    onSurfaceVariant = Color(0xFFE4D5C2)
  )

private val LightColorScheme =
  lightColorScheme(
    primary = MelaBurgundy,
    onPrimary = Color.White,
    primaryContainer = MelaTopBarGold,
    onPrimaryContainer = Color(0xFF211400),
    secondary = MelaGoldPrimary,
    onSecondary = Color(0xFF211400),
    secondaryContainer = MelaGoldContainer,
    onSecondaryContainer = Color(0xFF5D3100),
    tertiary = WhatsAppGreenDark,
    onTertiary = Color.White,
    tertiaryContainer = WhatsAppGreenLight,
    onTertiaryContainer = Color(0xFF003914),
    background = MelaBackgroundLight,
    onBackground = Color(0xFF211400),
    surface = MelaSurfaceLight,
    onSurface = Color(0xFF211400),
    surfaceVariant = MelaSurfaceVariantLight,
    onSurfaceVariant = Color(0xFF4A3B2C),
    outline = MelaGoldBorder
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // We prefer our vibrant curated brand theme for Karachi Sale Mela
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}

