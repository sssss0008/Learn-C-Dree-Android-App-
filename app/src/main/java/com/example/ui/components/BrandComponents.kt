package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

fun openLinkedIn(context: Context) {
  val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/awiskaracharya/"))
  context.startActivity(intent)
}

@Composable
fun CppLogoBadge(
  size: Int = 42,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .size(size.dp)
      .clip(RoundedCornerShape((size * 0.28).dp))
      .background(
        Brush.linearGradient(
          colors = listOf(CppBlueDark, CppBluePrimary, CppAccentCyan)
        )
      )
      .border(1.dp, CppBlueLight.copy(alpha = 0.5f), RoundedCornerShape((size * 0.28).dp)),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = "C++",
      color = Color.White,
      fontFamily = FontFamily.Monospace,
      fontWeight = FontWeight.Black,
      fontSize = (size * 0.42).sp
    )
  }
}

@Composable
fun FreeBadge(modifier: Modifier = Modifier) {
  Surface(
    modifier = modifier,
    color = CppAccentEmerald.copy(alpha = 0.15f),
    shape = RoundedCornerShape(12.dp),
    border = androidx.compose.foundation.BorderStroke(1.dp, CppAccentEmerald.copy(alpha = 0.4f))
  ) {
    Row(
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(6.dp)
          .clip(CircleShape)
          .background(CppAccentEmerald)
      )
      Spacer(modifier = Modifier.width(5.dp))
      Text(
        text = "100% FREE",
        color = CppAccentEmerald,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.5.sp
      )
    }
  }
}

@Composable
fun CreatorCard(
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  Card(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = CppSurfaceCard),
    border = androidx.compose.foundation.BorderStroke(1.dp, CppBorderDark)
  ) {
    Column(modifier = Modifier.padding(18.dp)) {
      Row(
        verticalAlignment = Alignment.CenterVertically
      ) {
        CppLogoBadge(size = 48)
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = "Learn C++",
              style = MaterialTheme.typography.titleLarge,
              color = Color.White,
              fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(6.dp))
            FreeBadge()
          }
          Text(
            text = "by Awiskar Acharya",
            style = MaterialTheme.typography.bodyMedium,
            color = CppBlueLight,
            fontWeight = FontWeight.Medium
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))
      Text(
        text = "“Learn. Code. Build. Master C++.”",
        style = MaterialTheme.typography.bodyMedium,
        color = Color(0xFF94A3B8),
        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
      )

      Spacer(modifier = Modifier.height(14.dp))
      Button(
        onClick = { openLinkedIn(context) },
        colors = ButtonDefaults.buttonColors(containerColor = CppBluePrimary),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Icon(
          imageVector = Icons.Default.OpenInNew,
          contentDescription = "LinkedIn",
          modifier = Modifier.size(16.dp),
          tint = Color.White
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Connect with Awiskar on LinkedIn",
          color = Color.White,
          fontWeight = FontWeight.SemiBold,
          fontSize = 13.sp
        )
      }
    }
  }
}
