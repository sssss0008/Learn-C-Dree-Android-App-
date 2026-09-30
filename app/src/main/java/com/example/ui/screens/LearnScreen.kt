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
import com.example.data.model.CppLesson
import com.example.data.model.CppLevel
import com.example.data.repository.CppContentRepository
import com.example.ui.components.FreeBadge
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppUiState

@Composable
fun LearnScreen(
  uiState: AppUiState,
  onSelectLesson: (CppLesson) -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedFilterLevel by remember { mutableStateOf<Int?>(null) }
  val allLevels = CppContentRepository.levels

  val displayedLevels = if (selectedFilterLevel != null) {
    allLevels.filter { it.id == selectedFilterLevel }
  } else {
    allLevels
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(CppBackgroundDark)
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp),
    contentPadding = PaddingValues(top = 16.dp, bottom = 90.dp)
  ) {
    // Header
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = "C++ Curriculum",
              style = MaterialTheme.typography.headlineMedium,
              fontWeight = FontWeight.Bold,
              color = Color.White
            )
            Spacer(modifier = Modifier.width(6.dp))
            FreeBadge()
          }
          Text(
            text = "From hardware architecture to Modern C++23 standards",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF94A3B8)
          )
        }
      }
    }

    // Level filter pills
    item {
      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        item {
          FilterChip(
            selected = selectedFilterLevel == null,
            onClick = { selectedFilterLevel = null },
            label = { Text("All 15 Levels") },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = CppBluePrimary,
              selectedLabelColor = Color.White,
              containerColor = CppSurfaceCard,
              labelColor = Color(0xFFCBD5E1)
            )
          )
        }
        items(allLevels) { lvl ->
          FilterChip(
            selected = selectedFilterLevel == lvl.id,
            onClick = {
              selectedFilterLevel = if (selectedFilterLevel == lvl.id) null else lvl.id
            },
            label = { Text("L${lvl.id}: ${lvl.tag}") },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = CppBluePrimary,
              selectedLabelColor = Color.White,
              containerColor = CppSurfaceCard,
              labelColor = Color(0xFFCBD5E1)
            )
          )
        }
      }
    }

    // Levels list
    items(displayedLevels) { level ->
      LevelCard(
        level = level,
        completedLessonIds = uiState.completedLessonIds,
        onSelectLesson = onSelectLesson
      )
    }
  }
}

@Composable
fun LevelCard(
  level: CppLevel,
  completedLessonIds: Set<String>,
  onSelectLesson: (CppLesson) -> Unit,
  modifier: Modifier = Modifier
) {
  var isExpanded by remember { mutableStateOf(true) }
  val totalLessons = level.modules.flatMap { it.lessons }.size
  val completedInLevel = level.modules.flatMap { it.lessons }.count { completedLessonIds.contains(it.id) }
  val progress = if (totalLessons > 0) completedInLevel.toFloat() / totalLessons.toFloat() else 0f

  Card(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = CppSurfaceCard),
    border = androidx.compose.foundation.BorderStroke(1.dp, CppBorderDark)
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      // Level Header
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clickable { isExpanded = !isExpanded },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.weight(1f)
        ) {
          Box(
            modifier = Modifier
              .size(36.dp)
              .clip(CircleShape)
              .background(CppBlueDark),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "${level.id}",
              fontWeight = FontWeight.Black,
              color = CppAccentCyan,
              fontSize = 14.sp
            )
          }
          Spacer(modifier = Modifier.width(12.dp))
          Column {
            Text(
              text = level.title,
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = Color.White
            )
            Text(
              text = level.subtitle,
              style = MaterialTheme.typography.bodySmall,
              color = Color(0xFF94A3B8),
              maxLines = 1
            )
          }
        }

        IconButton(onClick = { isExpanded = !isExpanded }) {
          Icon(
            imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
            contentDescription = "Toggle",
            tint = Color(0xFF94A3B8)
          )
        }
      }

      // Progress bar for level
      Spacer(modifier = Modifier.height(10.dp))
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        LinearProgressIndicator(
          progress = { progress },
          modifier = Modifier
            .weight(1f)
            .height(5.dp)
            .clip(CircleShape),
          color = if (progress >= 1f) CppAccentEmerald else CppBlueLight,
          trackColor = CppSurfaceDark
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
          text = "$completedInLevel / $totalLessons",
          fontSize = 10.sp,
          color = Color(0xFF94A3B8),
          fontWeight = FontWeight.Bold
        )
      }

      // Lessons List inside Level
      if (isExpanded) {
        Spacer(modifier = Modifier.height(12.dp))
        Divider(color = CppBorderDark.copy(alpha = 0.5f))
        Spacer(modifier = Modifier.height(8.dp))

        level.modules.forEach { module ->
          module.lessons.forEach { lesson ->
            val isDone = completedLessonIds.contains(lesson.id)
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(CppSurfaceDark)
                .clickable { onSelectLesson(lesson) }
                .padding(10.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
              ) {
                Icon(
                  imageVector = if (isDone) Icons.Default.CheckCircle else Icons.Default.PlayCircleOutline,
                  contentDescription = null,
                  tint = if (isDone) CppAccentEmerald else CppBlueLight,
                  modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                  Text(
                    text = lesson.title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                  )
                  Text(
                    text = "${lesson.durationMinutes} min • ${lesson.overview}",
                    fontSize = 11.sp,
                    color = Color(0xFF94A3B8),
                    maxLines = 1
                  )
                }
              }

              Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Open",
                tint = Color(0xFF64748B),
                modifier = Modifier.size(18.dp)
              )
            }
          }
        }
      }
    }
  }
}
