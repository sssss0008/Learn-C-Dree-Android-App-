package com.example.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.components.CppLogoBadge
import com.example.ui.components.FreeBadge
import com.example.ui.components.openLinkedIn
import com.example.ui.theme.*

@Composable
fun CertificateDialog(
  userName: String,
  onDismiss: () -> Unit
) {
  val context = LocalContext.current

  Dialog(onDismissRequest = onDismiss) {
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight(),
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = CppSurfaceDark),
      border = androidx.compose.foundation.BorderStroke(2.dp, CppAccentAmber)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .background(
            Brush.verticalGradient(
              colors = listOf(Color(0xFF131F38), CppSurfaceDark, Color(0xFF0C1424))
            )
          )
          .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          FreeBadge()
          IconButton(onClick = onDismiss) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = Color.White)
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box(
          modifier = Modifier
            .size(54.dp)
            .clip(CircleShape)
            .background(CppAccentAmber.copy(alpha = 0.2f))
            .border(2.dp, CppAccentAmber, CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.WorkspacePremium,
            contentDescription = null,
            tint = CppAccentAmber,
            modifier = Modifier.size(32.dp)
          )
        }

        Spacer(modifier = Modifier.height(12.dp))
        Text(
          text = "CERTIFICATE OF MASTERY",
          letterSpacing = 1.sp,
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = CppAccentAmber
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
          text = "Learn C++ Academy",
          style = MaterialTheme.typography.headlineMedium,
          fontWeight = FontWeight.Black,
          color = Color.White,
          textAlign = TextAlign.Center
        )
        Text(
          text = "“Learn. Code. Build. Master C++.”",
          fontSize = 12.sp,
          fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
          color = CppBlueLight
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
          text = "This officially certifies that",
          fontSize = 11.sp,
          color = Color(0xFF94A3B8)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = userName,
          fontSize = 22.sp,
          fontWeight = FontWeight.Bold,
          color = Color.White,
          textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
          text = "has successfully mastered Modern C++ programming fundamentals, memory management, pointers, object-oriented design, STL containers, data structures, and algorithms.",
          fontSize = 11.sp,
          color = Color(0xFFCBD5E1),
          textAlign = TextAlign.Center,
          lineHeight = 16.sp
        )

        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = CppBorderDark)
        Spacer(modifier = Modifier.height(12.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "Awiskar Acharya",
              fontWeight = FontWeight.Bold,
              fontSize = 13.sp,
              color = Color.White
            )
            Text(
              text = "Lead Creator & Instructor",
              fontSize = 10.sp,
              color = CppBlueLight
            )
          }

          Column(horizontalAlignment = Alignment.End) {
            Text(
              text = "CPP-MASTER-2026",
              fontFamily = FontFamily.Monospace,
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = Color(0xFF64748B)
            )
            Text(
              text = "Verified Credential",
              fontSize = 9.sp,
              color = CppAccentEmerald
            )
          }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Button(
          onClick = { openLinkedIn(context) },
          colors = ButtonDefaults.buttonColors(containerColor = CppBluePrimary),
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Icon(
            imageVector = Icons.Default.OpenInNew,
            contentDescription = null,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text("Connect with Awiskar on LinkedIn", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}
