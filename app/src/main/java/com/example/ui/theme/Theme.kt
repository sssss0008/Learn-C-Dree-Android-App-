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

private val DarkColorScheme = darkColorScheme(
  primary = CppBlueLight,
  onPrimary = Color(0xFF001E36),
  primaryContainer = CppBlueDark,
  onPrimaryContainer = Color(0xFFD1E4FF),
  secondary = CppAccentCyan,
  onSecondary = Color(0xFF00363D),
  secondaryContainer = Color(0xFF004F58),
  onSecondaryContainer = Color(0xFF97F0FF),
  tertiary = CppAccentEmerald,
  onTertiary = Color(0xFF003822),
  background = CppBackgroundDark,
  onBackground = Color(0xFFE2E8F0),
  surface = CppSurfaceDark,
  onSurface = Color(0xFFF1F5F9),
  surfaceVariant = CppSurfaceCard,
  onSurfaceVariant = Color(0xFFCBD5E1),
  outline = CppBorderDark,
  error = CppAccentRose
)

private val LightColorScheme = lightColorScheme(
  primary = CppBlue,
  onPrimary = Color.White,
  primaryContainer = Color(0xFFD6E4FF),
  onPrimaryContainer = Color(0xFF001C39),
  secondary = CppBluePrimary,
  onSecondary = Color.White,
  secondaryContainer = Color(0xFFC7E7FF),
  onSecondaryContainer = Color(0xFF001E30),
  tertiary = Color(0xFF047857),
  onTertiary = Color.White,
  background = CppBackgroundLight,
  onBackground = CppTextPrimaryLight,
  surface = CppSurfaceLight,
  onSurface = CppTextPrimaryLight,
  surfaceVariant = CppSurfaceCardLight,
  onSurfaceVariant = CppTextSecondaryLight,
  outline = CppBorderLight,
  error = Color(0xFFDC2626)
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = true, // Default to sleek developer dark theme for C++ IDE experience
  dynamicColor: Boolean = false, // Keep intentional C++ identity
  content: @Composable () -> Unit,
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
