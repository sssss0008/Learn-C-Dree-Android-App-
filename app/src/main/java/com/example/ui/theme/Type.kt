package com.example.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(
  displayLarge = TextStyle(
    fontWeight = FontWeight.Bold,
    fontSize = 32.sp,
    lineHeight = 38.sp,
    letterSpacing = (-0.5).sp
  ),
  displayMedium = TextStyle(
    fontWeight = FontWeight.Bold,
    fontSize = 26.sp,
    lineHeight = 32.sp
  ),
  headlineLarge = TextStyle(
    fontWeight = FontWeight.SemiBold,
    fontSize = 22.sp,
    lineHeight = 28.sp
  ),
  headlineMedium = TextStyle(
    fontWeight = FontWeight.SemiBold,
    fontSize = 18.sp,
    lineHeight = 24.sp
  ),
  titleLarge = TextStyle(
    fontWeight = FontWeight.SemiBold,
    fontSize = 16.sp,
    lineHeight = 22.sp
  ),
  titleMedium = TextStyle(
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
    lineHeight = 20.sp
  ),
  bodyLarge = TextStyle(
    fontWeight = FontWeight.Normal,
    fontSize = 15.sp,
    lineHeight = 22.sp
  ),
  bodyMedium = TextStyle(
    fontWeight = FontWeight.Normal,
    fontSize = 13.sp,
    lineHeight = 18.sp
  ),
  bodySmall = TextStyle(
    fontWeight = FontWeight.Normal,
    fontSize = 11.sp,
    lineHeight = 16.sp
  ),
  labelLarge = TextStyle(
    fontWeight = FontWeight.Medium,
    fontSize = 13.sp,
    lineHeight = 18.sp
  ),
  labelSmall = TextStyle(
    fontWeight = FontWeight.Medium,
    fontSize = 10.sp,
    lineHeight = 14.sp,
    letterSpacing = 0.5.sp
  )
)

val MonospaceCodeStyle = TextStyle(
  fontFamily = FontFamily.Monospace,
  fontWeight = FontWeight.Normal,
  fontSize = 13.sp,
  lineHeight = 19.sp
)
