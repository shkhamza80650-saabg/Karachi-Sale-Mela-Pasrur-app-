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
    primary = MelaCrimsonLight,
    onPrimary = Color.White,
    primaryContainer = MelaCrimsonDark,
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
    onBackground = Color(0xFFF1EAE4),
    surface = MelaSurfaceDark,
    onSurface = Color(0xFFF1EAE4),
    surfaceVariant = MelaSurfaceVariantDark,
    onSurfaceVariant = Color(0xFFD7CCC8)
  )

private val LightColorScheme =
  lightColorScheme(
    primary = MelaCrimson,
    onPrimary = Color.White,
    primaryContainer = MelaCrimsonContainer,
    onPrimaryContainer = MelaCrimsonDark,
    secondary = MelaGold,
    onSecondary = Color.White,
    secondaryContainer = MelaGoldContainer,
    onSecondaryContainer = Color(0xFF662200),
    tertiary = WhatsAppGreenDark,
    onTertiary = Color.White,
    tertiaryContainer = WhatsAppGreenLight,
    onTertiaryContainer = Color(0xFF003914),
    background = MelaBackgroundLight,
    onBackground = Color(0xFF231A18),
    surface = MelaCardLight,
    onSurface = Color(0xFF231A18),
    surfaceVariant = MelaSurfaceVariantLight,
    onSurfaceVariant = Color(0xFF554440),
    outline = Color(0xFFD6C8C0)
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

