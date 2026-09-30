package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.CppContentRepository
import com.example.ui.components.CreatorCard
import com.example.ui.components.FreeBadge
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppUiState
import com.example.ui.viewmodel.MainTab

@Composable
fun HomeScreen(
  uiState: AppUiState,
  onNavigateTab: (MainTab) -> Unit,
  onSelectLesson: (com.example.data.model.CppLesson) -> Unit,
  modifier: Modifier = Modifier
) {
  val continueLesson = CppContentRepository.levels[9].modules[0].lessons[0] // Smart pointers

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(CppBackgroundDark)
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    contentPadding = PaddingValues(top = 16.dp, bottom = 90.dp)
  ) {
    // Header Greeting
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = "Good morning, ${uiState.userName}",
              style = MaterialTheme.typography.headlineMedium,
              fontWeight = FontWeight.Bold,
              color = Color.White
            )
            Spacer(modifier = Modifier.width(6.dp))
            FreeBadge()
          }
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = "Ready to continue mastering Modern C++?",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF94A3B8)
          )
        }

        // Streak badge pill
        Surface(
          color = CppSurfaceCard,
          shape = RoundedCornerShape(20.dp),
          border = androidx.compose.foundation.BorderStroke(1.dp, CppAccentAmber.copy(alpha = 0.5f))
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.LocalFireDepartment,
              contentDescription = "Streak",
              tint = CppAccentAmber,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "${uiState.streakDays}d streak",
              fontWeight = FontWeight.Bold,
              color = CppAccentAmber,
              fontSize = 12.sp
            )
          }
        }
      }
    }

    // Continue Learning Hero Card
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("continue_learning_card")
          .clickable { onSelectLesson(continueLesson) },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
      ) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .background(
              Brush.linearGradient(
                colors = listOf(CppBlueDark, Color(0xFF0F2B48), CppSurfaceCard)
              )
            )
            .border(1.dp, CppBlueLight.copy(alpha = 0.4f), RoundedCornerShape(18.dp))
            .padding(18.dp)
        ) {
          Column {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Surface(
                color = CppBlueLight.copy(alpha = 0.2f),
                shape = RoundedCornerShape(6.dp)
              ) {
                Text(
                  text = "CONTINUE LEARNING",
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  color = CppBlueLight,
                  letterSpacing = 0.5.sp,
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
              }
              Text(
                text = "Level 10",
                fontSize = 11.sp,
                color = Color(0xFFCBD5E1),
                fontWeight = FontWeight.Medium
              )
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(
              text = continueLesson.title,
              style = MaterialTheme.typography.titleLarge,
              fontWeight = FontWeight.Bold,
              color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = continueLesson.overview,
              style = MaterialTheme.typography.bodySmall,
              color = Color(0xFFCBD5E1),
              maxLines = 2
            )

            Spacer(modifier = Modifier.height(14.dp))
            Row(
              modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Column(modifier = Modifier.weight(1f).padding(end = 16.dp)) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Text("Progress", fontSize = 11.sp, color = Color(0xFF94A3B8))
                  Text("68%", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = CppAccentCyan)
                }
                Spacer(modifier = Modifier.height(4.dp))
                LinearProgressIndicator(
                  progress = { 0.68f },
                  modifier = Modifier.fillMaxWidth().height(6.dp).clip(CircleShape),
                  color = CppAccentCyan,
                  trackColor = CppSurfaceDark
                )
              }

              Button(
                onClick = { onSelectLesson(continueLesson) },
                colors = ButtonDefaults.buttonColors(containerColor = CppBluePrimary),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
              ) {
                Text("Resume", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                  imageVector = Icons.Default.PlayArrow,
                  contentDescription = null,
                  modifier = Modifier.size(16.dp)
                )
              }
            }
          }
        }
      }
    }

    // Daily Goal & Streak Card
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        // Daily Goal
        Card(
          modifier = Modifier.weight(1f),
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = CppSurfaceCard),
          border = androidx.compose.foundation.BorderStroke(1.dp, CppBorderDark)
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.CheckCircleOutline,
                contentDescription = null,
                tint = CppAccentEmerald,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Today's Goal",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
              )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "2 / 5 completed",
              fontSize = 14.sp,
              fontWeight = FontWeight.Black,
              color = CppAccentEmerald
            )
            Spacer(modifier = Modifier.height(6.dp))
            LinearProgressIndicator(
              progress = { 2f / 5f },
              modifier = Modifier.fillMaxWidth().height(5.dp).clip(CircleShape),
              color = CppAccentEmerald,
              trackColor = CppSurfaceDark
            )
          }
        }

        // Streak Card
        Card(
          modifier = Modifier.weight(1f),
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = CppSurfaceCard),
          border = androidx.compose.foundation.BorderStroke(1.dp, CppBorderDark)
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.EmojiEvents,
                contentDescription = null,
                tint = CppAccentAmber,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Streak Record",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
              )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "Best: ${uiState.longestStreak} Days",
              fontSize = 14.sp,
              fontWeight = FontWeight.Black,
              color = CppAccentAmber
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = "Current: ${uiState.streakDays} days active",
              fontSize = 10.sp,
              color = Color(0xFF94A3B8)
            )
          }
        }
      }
    }

    // Quick Actions Grid
    item {
      Text(
        text = "Quick Actions",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = Color.White
      )
      Spacer(modifier = Modifier.height(8.dp))

      val actions = listOf(
        Triple("Curriculum", Icons.Default.School, MainTab.LEARN),
        Triple("C++ Playground", Icons.Default.Code, MainTab.CODE),
        Triple("Memory Lab", Icons.Default.Layers, MainTab.LABS),
        Triple("Practice & CP", Icons.Default.SportsEsports, MainTab.PRACTICE),
        Triple("AI Tutor", Icons.Default.SmartToy, MainTab.MORE),
        Triple("C++ Reference", Icons.Default.MenuBook, MainTab.MORE)
      )

      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          actions.take(3).forEach { (title, icon, tab) ->
            QuickActionItem(
              title = title,
              icon = icon,
              onClick = { onNavigateTab(tab) },
              modifier = Modifier.weight(1f)
            )
          }
        }
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          actions.drop(3).forEach { (title, icon, tab) ->
            QuickActionItem(
              title = title,
              icon = icon,
              onClick = { onNavigateTab(tab) },
              modifier = Modifier.weight(1f)
            )
          }
        }
      }
    }

    // Dashboard Statistics Cards
    item {
      Text(
        text = "Developer Progress",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = Color.White
      )
      Spacer(modifier = Modifier.height(8.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        StatCard(
          number = "${uiState.completedLessonIds.size}",
          label = "Lessons Done",
          color = CppBlueLight,
          modifier = Modifier.weight(1f)
        )
        StatCard(
          number = "${uiState.solvedChallengeIds.size}",
          label = "Problems Solved",
          color = CppAccentEmerald,
          modifier = Modifier.weight(1f)
        )
        StatCard(
          number = "4",
          label = "Projects Built",
          color = CppAccentPurple,
          modifier = Modifier.weight(1f)
        )
      }
    }

    // Creator & LinkedIn Card
    item {
      CreatorCard()
    }
  }
}

@Composable
fun QuickActionItem(
  title: String,
  icon: ImageVector,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(12.dp))
      .background(CppSurfaceCard)
      .border(1.dp, CppBorderDark, RoundedCornerShape(12.dp))
      .clickable(onClick = onClick)
      .padding(12.dp),
    contentAlignment = Alignment.Center
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Icon(
        imageVector = icon,
        contentDescription = title,
        tint = CppAccentCyan,
        modifier = Modifier.size(24.dp)
      )
      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = title,
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.White
      )
    }
  }
}

@Composable
fun StatCard(
  number: String,
  label: String,
  color: Color,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(12.dp))
      .background(CppSurfaceCard)
      .border(1.dp, CppBorderDark, RoundedCornerShape(12.dp))
      .padding(12.dp)
  ) {
    Column {
      Text(
        text = number,
        fontSize = 20.sp,
        fontWeight = FontWeight.Black,
        color = color
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = label,
        fontSize = 11.sp,
        color = Color(0xFF94A3B8)
      )
    }
  }
}
