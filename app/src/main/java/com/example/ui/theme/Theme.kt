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

private val LightColorScheme = lightColorScheme(
  primary = AgriGreenPrimary,
  onPrimary = AgriGreenOnPrimary,
  primaryContainer = AgriGreenContainer,
  onPrimaryContainer = AgriGreenOnContainer,
  secondary = AgriHarvestAmber,
  onSecondary = Color.White,
  secondaryContainer = AgriAmberContainer,
  onSecondaryContainer = AgriAmberOnContainer,
  tertiary = AgriEarthSecondary,
  background = AgriBackgroundLight,
  onBackground = AgriTextPrimary,
  surface = AgriSurfaceLight,
  onSurface = AgriTextPrimary,
  surfaceVariant = AgriSurfaceVariant,
  onSurfaceVariant = AgriTextSecondary,
  outline = AgriOutline
)

private val DarkColorScheme = darkColorScheme(
  primary = AgriGreenDarkPrimary,
  onPrimary = Color(0xFF00391A),
  primaryContainer = AgriGreenDarkContainer,
  onPrimaryContainer = Color(0xFF94F9BD),
  secondary = Color(0xFFFFB951),
  background = AgriDarkBackground,
  onBackground = Color(0xFFE1E8E2),
  surface = AgriDarkSurface,
  onSurface = Color(0xFFE1E8E2),
  surfaceVariant = AgriDarkSurfaceVariant,
  onSurfaceVariant = Color(0xFFC0CEC3)
)

@Composable
fun AgriConnectTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // Preserve dedicated green agricultural branding
  content: @Composable () -> Unit
) {
  val colorScheme = when {
    dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
      val context = LocalContext.current
      if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    }
    darkTheme -> DarkColorScheme
    else -> LightColorScheme
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit
) {
  AgriConnectTheme(darkTheme = darkTheme, dynamicColor = dynamicColor, content = content)
}
