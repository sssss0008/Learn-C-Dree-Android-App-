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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.NoteEntity
import com.example.ui.components.CreatorCard
import com.example.ui.components.FreeBadge
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppUiState

@Composable
fun ProfileScreen(
  uiState: AppUiState,
  onShowCertificate: () -> Unit,
  onDeleteNote: (Long) -> Unit,
  modifier: Modifier = Modifier
) {
  val achievements = listOf(
    Triple("First C++ Program", "Compiled first std::cout binary", true),
    Triple("Pointer Master", "Dereferenced memory addresses without segfaults", true),
    Triple("OOP Architect", "Built classes with encapsulation & inheritance", true),
    Triple("STL Explorer", "Utilized std::vector & std::map in algorithms", true),
    Triple("Smart Pointer Pro", "Mastered unique_ptr & shared_ptr ownership", false),
    Triple("Concurrency Pioneer", "Prevented data races with std::mutex", false)
  )

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(CppBackgroundDark)
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp),
    contentPadding = PaddingValues(top = 16.dp, bottom = 90.dp)
  ) {
    // Header Profile Card
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CppSurfaceCard),
        border = androidx.compose.foundation.BorderStroke(1.dp, CppBorderDark)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(54.dp)
                  .clip(CircleShape)
                  .background(CppBlueDark)
                  .border(2.dp, CppBlueLight, CircleShape),
                contentAlignment = Alignment.Center
              ) {
                Text(
                  text = uiState.userName.take(1).uppercase(),
                  fontSize = 24.sp,
                  fontWeight = FontWeight.Black,
                  color = CppAccentCyan
                )
              }
              Spacer(modifier = Modifier.width(14.dp))
              Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(
                    text = uiState.userName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  FreeBadge()
                }
                Text(
                  text = "${uiState.experienceLevel} • Level 10 Explorer",
                  fontSize = 12.sp,
                  color = CppBlueLight
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Learning Goals pills
          Text(
            text = "Active Learning Goals:",
            fontSize = 11.sp,
            color = Color(0xFF94A3B8)
          )
          Spacer(modifier = Modifier.height(6.dp))
          LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            items(uiState.learningGoals.toList()) { goal ->
              Surface(
                color = CppSurfaceDark,
                shape = RoundedCornerShape(6.dp)
              ) {
                Text(
                  text = goal,
                  fontSize = 10.sp,
                  color = Color(0xFFCBD5E1),
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          Button(
            onClick = onShowCertificate,
            colors = ButtonDefaults.buttonColors(containerColor = CppAccentAmber),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth().testTag("view_certificate_button")
          ) {
            Icon(
              imageVector = Icons.Default.WorkspacePremium,
              contentDescription = null,
              tint = Color.Black
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "View C++ Completion Certificate",
              color = Color.Black,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }
    }

    // Achievements Section
    item {
      Text(
        text = "Milestones & Achievements",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = Color.White
      )
      Spacer(modifier = Modifier.height(8.dp))

      Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        achievements.forEach { (title, desc, unlocked) ->
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = CppSurfaceCard),
            border = androidx.compose.foundation.BorderStroke(1.dp, CppBorderDark)
          ) {
            Row(
              modifier = Modifier.padding(12.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(34.dp)
                  .clip(CircleShape)
                  .background(if (unlocked) CppAccentEmerald.copy(alpha = 0.2f) else CppSurfaceDark),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = if (unlocked) Icons.Default.CheckCircle else Icons.Default.Lock,
                  contentDescription = null,
                  tint = if (unlocked) CppAccentEmerald else Color(0xFF64748B),
                  modifier = Modifier.size(18.dp)
                )
              }
              Spacer(modifier = Modifier.width(12.dp))
              Column {
                Text(
                  text = title,
                  fontWeight = FontWeight.Bold,
                  color = if (unlocked) Color.White else Color(0xFF94A3B8),
                  fontSize = 13.sp
                )
                Text(
                  text = desc,
                  fontSize = 11.sp,
                  color = Color(0xFF64748B)
                )
              }
            }
          }
        }
      }
    }

    // Saved Notes (Room Database)
    item {
      Text(
        text = "Saved Learning Notes (Local Room Database)",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = Color.White
      )
      Spacer(modifier = Modifier.height(8.dp))

      if (uiState.notes.isEmpty()) {
        Surface(
          color = CppSurfaceCard,
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Text(
            text = "No notes saved yet. Tap the note icon inside any lesson to record insights.",
            color = Color(0xFF94A3B8),
            fontSize = 12.sp,
            modifier = Modifier.padding(14.dp)
          )
        }
      } else {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          uiState.notes.forEach { note ->
            Card(
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(10.dp),
              colors = CardDefaults.cardColors(containerColor = CppSurfaceCard),
              border = androidx.compose.foundation.BorderStroke(1.dp, CppBorderDark)
            ) {
              Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = note.title,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 13.sp
                  )
                  Spacer(modifier = Modifier.height(2.dp))
                  Text(
                    text = note.content,
                    fontSize = 12.sp,
                    color = Color(0xFFCBD5E1)
                  )
                  Spacer(modifier = Modifier.height(4.dp))
                  Text(
                    text = "Topic: ${note.topicTag}",
                    fontSize = 10.sp,
                    color = CppBlueLight
                  )
                }
                IconButton(onClick = { onDeleteNote(note.id) }) {
                  Icon(
                    imageVector = Icons.Default.DeleteOutline,
                    contentDescription = "Delete note",
                    tint = Color(0xFFEF4444),
                    modifier = Modifier.size(18.dp)
                  )
                }
              }
            }
          }
        }
      }
    }

    // Creator Card
    item {
      CreatorCard()
    }
  }
}
