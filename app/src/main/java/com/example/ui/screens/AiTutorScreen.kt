package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.CppLogoBadge
import com.example.ui.components.FreeBadge
import com.example.ui.theme.*
import com.example.ui.viewmodel.ChatMessage

@Composable
fun AiTutorScreen(
  chatMessages: List<ChatMessage>,
  onSendMessage: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  var userInput by remember { mutableStateOf("") }

  val promptShortcuts = listOf(
    "Explain Pointers vs References",
    "Why must base destructor be virtual?",
    "Explain Smart Pointers (unique_ptr vs shared_ptr)",
    "What is RAII in C++?",
    "How does std::vector reallocation work?",
    "What is Move Semantics?",
    "Explain std::optional in C++17"
  )

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(CppBackgroundDark)
  ) {
    // Top Bar
    Surface(
      color = CppSurfaceDark,
      border = androidx.compose.foundation.BorderStroke(1.dp, CppBorderDark)
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(36.dp)
              .clip(CircleShape)
              .background(CppBluePrimary.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.SmartToy,
              contentDescription = null,
              tint = CppAccentCyan,
              modifier = Modifier.size(20.dp)
            )
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = "AI C++ MENTOR",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = Color.White
            )
            Text(
              text = "Expert on modern standards, memory, & compilers",
              fontSize = 11.sp,
              color = Color(0xFF94A3B8)
            )
          }
        }
        FreeBadge()
      }
    }

    // Chat Messages List
    LazyColumn(
      modifier = Modifier
        .weight(1f)
        .padding(horizontal = 14.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp),
      contentPadding = PaddingValues(top = 14.dp, bottom = 14.dp)
    ) {
      items(chatMessages) { msg ->
        val isUser = msg.sender == "user"
        Column(
          modifier = Modifier.fillMaxWidth(),
          horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
        ) {
          Surface(
            color = if (isUser) CppBluePrimary else CppSurfaceCard,
            shape = RoundedCornerShape(14.dp),
            border = androidx.compose.foundation.BorderStroke(
              1.dp,
              if (isUser) CppAccentCyan else CppBorderDark
            ),
            modifier = Modifier.widthIn(max = 320.dp)
          ) {
            Column(modifier = Modifier.padding(12.dp)) {
              Text(
                text = if (isUser) "You" else "C++ Mentor",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = if (isUser) Color(0xFFE0F2FE) else CppBlueLight
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = msg.text,
                fontSize = 13.sp,
                lineHeight = 19.sp,
                color = Color.White
              )

              if (!msg.codeSnippet.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                  color = CodeBackground,
                  shape = RoundedCornerShape(8.dp),
                  border = androidx.compose.foundation.BorderStroke(1.dp, CppBorderDark),
                  modifier = Modifier.fillMaxWidth()
                ) {
                  Text(
                    text = msg.codeSnippet,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    color = Color(0xFFCBD5E1),
                    modifier = Modifier.padding(8.dp)
                  )
                }
              }
            }
          }
        }
      }
    }

    // Shortcut Prompt Pills
    LazyRow(
      horizontalArrangement = Arrangement.spacedBy(6.dp),
      modifier = Modifier
        .fillMaxWidth()
        .background(CppSurfaceDark)
        .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
      items(promptShortcuts) { shortcut ->
        Surface(
          color = CppSurfaceCard,
          shape = RoundedCornerShape(16.dp),
          border = androidx.compose.foundation.BorderStroke(1.dp, CppBorderDark),
          modifier = Modifier.clickable {
            onSendMessage(shortcut)
          }
        ) {
          Text(
            text = shortcut,
            color = CppAccentCyan,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
          )
        }
      }
    }

    // Input Bar
    Surface(
      color = CppSurfaceDark,
      border = androidx.compose.foundation.BorderStroke(1.dp, CppBorderDark)
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 10.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        OutlinedTextField(
          value = userInput,
          onValueChange = { userInput = it },
          placeholder = { Text("Ask about memory, virtual tables, STL...", fontSize = 12.sp) },
          maxLines = 3,
          shape = RoundedCornerShape(20.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = CppBlueLight,
            unfocusedBorderColor = CppBorderDark,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedPlaceholderColor = Color(0xFF64748B),
            unfocusedPlaceholderColor = Color(0xFF64748B)
          ),
          modifier = Modifier.weight(1f).testTag("tutor_input_field")
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
          onClick = {
            if (userInput.isNotBlank()) {
              onSendMessage(userInput)
              userInput = ""
            }
          },
          modifier = Modifier
            .size(42.dp)
            .clip(CircleShape)
            .background(CppBluePrimary)
            .testTag("tutor_send_button")
        ) {
          Icon(
            imageVector = Icons.Default.Send,
            contentDescription = "Send",
            tint = Color.White,
            modifier = Modifier.size(18.dp)
          )
        }
      }
    }
  }
}
