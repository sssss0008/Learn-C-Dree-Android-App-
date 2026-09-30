package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.data.model.VisualizerType
import com.example.ui.components.CompilerPipelineView
import com.example.ui.components.CppCodeEditor
import com.example.ui.components.MemoryVisualizerView
import com.example.ui.theme.*

@Composable
fun LessonDetailScreen(
  lesson: CppLesson,
  isCompleted: Boolean,
  onComplete: () -> Unit,
  onBack: () -> Unit,
  onSaveNote: (title: String, content: String, topic: String) -> Unit,
  modifier: Modifier = Modifier
) {
  BackHandler { onBack() }

  var selectedTab by remember { mutableIntStateOf(0) } // 0: Explain, 1: Visualizer, 2: Code & Run, 3: Quiz, 4: Notes
  var selectedQuizOption by remember { mutableStateOf<Int?>(null) }
  var isQuizSubmitted by remember { mutableStateOf(false) }

  // Note dialog
  var showNoteDialog by remember { mutableStateOf(false) }
  var noteTitle by remember { mutableStateOf(lesson.title) }
  var noteContent by remember { mutableStateOf("") }
  var noteSavedMessage by remember { mutableStateOf(false) }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(CppBackgroundDark)
  ) {
    // Top AppBar
    Surface(
      color = CppSurfaceDark,
      border = androidx.compose.foundation.BorderStroke(1.dp, CppBorderDark)
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 8.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        IconButton(
          onClick = onBack,
          modifier = Modifier.testTag("back_button")
        ) {
          Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = Color.White
          )
        }
        Spacer(modifier = Modifier.width(4.dp))
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = "Level ${lesson.levelId}",
            fontSize = 11.sp,
            color = CppBlueLight,
            fontWeight = FontWeight.Bold
          )
          Text(
            text = lesson.title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            maxLines = 1
          )
        }

        IconButton(onClick = { showNoteDialog = true }) {
          Icon(
            imageVector = Icons.Default.EditNote,
            contentDescription = "Take Note",
            tint = CppAccentCyan
          )
        }

        if (isCompleted) {
          Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Completed",
            tint = CppAccentEmerald,
            modifier = Modifier.padding(end = 8.dp)
          )
        }
      }
    }

    // Tab bar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .background(CppSurfaceDark)
        .padding(horizontal = 12.dp, vertical = 6.dp),
      horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      val tabs = listOf(
        "Explain",
        if (lesson.visualizerType != VisualizerType.NONE) "Visualizer" else null,
        "Code & Run",
        if (lesson.quiz.isNotEmpty()) "Quiz" else null
      ).filterNotNull()

      tabs.forEachIndexed { index, label ->
        val isSelected = selectedTab == index
        Box(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) CppBluePrimary else CppSurfaceCard)
            .clickable { selectedTab = index }
            .padding(vertical = 8.dp),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) Color.White else Color(0xFF94A3B8)
          )
        }
      }
    }

    // Main Content Area
    Box(
      modifier = Modifier
        .weight(1f)
        .padding(16.dp)
    ) {
      when (selectedTab) {
        0 -> {
          // Explanation Tab
          Column(
            modifier = Modifier
              .fillMaxSize()
              .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(14.dp)
          ) {
            Surface(
              color = CppSurfaceCard,
              shape = RoundedCornerShape(14.dp),
              border = androidx.compose.foundation.BorderStroke(1.dp, CppBorderDark)
            ) {
              Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = Icons.Default.MenuBook,
                    contentDescription = null,
                    tint = CppBlueLight,
                    modifier = Modifier.size(20.dp)
                  )
                  Spacer(modifier = Modifier.width(8.dp))
                  Text(
                    text = "Core Concept",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                  )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                  text = lesson.explanation,
                  color = Color(0xFFCBD5E1),
                  fontSize = 14.sp,
                  lineHeight = 22.sp
                )
              }
            }

            if (lesson.deepDive.isNotBlank()) {
              Surface(
                color = CppSurfaceCard,
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CppAccentCyan.copy(alpha = 0.3f))
              ) {
                Column(modifier = Modifier.padding(16.dp)) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                      imageVector = Icons.Default.Lightbulb,
                      contentDescription = null,
                      tint = CppAccentAmber,
                      modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                      text = "Modern C++ Deep Dive",
                      style = MaterialTheme.typography.titleMedium,
                      fontWeight = FontWeight.Bold,
                      color = Color.White
                    )
                  }
                  Spacer(modifier = Modifier.height(8.dp))
                  Text(
                    text = lesson.deepDive,
                    color = Color(0xFFE2E8F0),
                    fontSize = 13.sp,
                    lineHeight = 20.sp
                  )
                }
              }
            }

            // Quick Code Preview
            Text(
              text = "Example Code Preview",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = Color.White
            )
            CppCodeEditor(
              initialCode = lesson.codeExample,
              expectedOutput = lesson.expectedOutput
            )

            Spacer(modifier = Modifier.height(10.dp))
            Button(
              onClick = onComplete,
              colors = ButtonDefaults.buttonColors(
                containerColor = if (isCompleted) CppSurfaceHighlight else CppAccentEmerald
              ),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.fillMaxWidth().testTag("mark_complete_button")
            ) {
              Icon(
                imageVector = if (isCompleted) Icons.Default.CheckCircle else Icons.Default.Check,
                contentDescription = null
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = if (isCompleted) "Completed (Tap to Toggle)" else "Mark Lesson as Complete",
                fontWeight = FontWeight.Bold
              )
            }
          }
        }

        1 -> {
          // Visualizer Tab
          Column(
            modifier = Modifier
              .fillMaxSize()
              .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(14.dp)
          ) {
            when (lesson.visualizerType) {
              VisualizerType.COMPILER_PIPELINE -> {
                CompilerPipelineView()
              }
              VisualizerType.POINTER_BOXES, VisualizerType.POINTER_VS_REFERENCE,
              VisualizerType.STACK_VS_HEAP, VisualizerType.SMART_POINTER_OWNERSHIP,
              VisualizerType.VARIABLE_MEMORY -> {
                MemoryVisualizerView()
              }
              else -> {
                MemoryVisualizerView()
              }
            }

            Text(
              text = "Interactive Code Demonstration",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = Color.White
            )
            CppCodeEditor(
              initialCode = lesson.codeExample,
              expectedOutput = lesson.expectedOutput
            )
          }
        }

        2 -> {
          // Code & Run Tab
          Column(
            modifier = Modifier
              .fillMaxSize()
              .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
          ) {
            Text(
              text = "Interactive C++ Sandbox",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = Color.White
            )
            Text(
              text = "Edit the C++ code freely, test modifications, and execute in the simulated compiler:",
              fontSize = 12.sp,
              color = Color(0xFF94A3B8)
            )

            CppCodeEditor(
              initialCode = lesson.codeExample,
              expectedOutput = lesson.expectedOutput
            )
          }
        }

        3 -> {
          // Quiz Tab
          val q = lesson.quiz.firstOrNull()
          if (q != null) {
            Column(
              modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
              verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
              Surface(
                color = CppSurfaceCard,
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CppBorderDark)
              ) {
                Column(modifier = Modifier.padding(16.dp)) {
                  Text(
                    text = "CHECK YOUR UNDERSTANDING",
                    color = CppBlueLight,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                  )
                  Spacer(modifier = Modifier.height(8.dp))
                  Text(
                    text = q.question,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                  )
                  Spacer(modifier = Modifier.height(14.dp))

                  q.options.forEachIndexed { optIndex, optionText ->
                    val isSelected = selectedQuizOption == optIndex
                    val isCorrect = optIndex == q.correctOptionIndex
                    val showResultColor = isQuizSubmitted

                    val bgColor = when {
                      showResultColor && isCorrect -> CppAccentEmerald.copy(alpha = 0.2f)
                      showResultColor && isSelected && !isCorrect -> CppAccentRose.copy(alpha = 0.2f)
                      isSelected -> CppBluePrimary.copy(alpha = 0.3f)
                      else -> CppBackgroundDark
                    }

                    val borderColor = when {
                      showResultColor && isCorrect -> CppAccentEmerald
                      showResultColor && isSelected && !isCorrect -> CppAccentRose
                      isSelected -> CppAccentCyan
                      else -> CppBorderDark
                    }

                    Box(
                      modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(bgColor)
                        .border(1.dp, borderColor, RoundedCornerShape(10.dp))
                        .clickable(enabled = !isQuizSubmitted) { selectedQuizOption = optIndex }
                        .padding(12.dp)
                    ) {
                      Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                          selected = isSelected,
                          onClick = { if (!isQuizSubmitted) selectedQuizOption = optIndex },
                          colors = RadioButtonDefaults.colors(selectedColor = CppAccentCyan)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                          text = optionText,
                          color = Color.White,
                          fontSize = 13.sp
                        )
                      }
                    }
                  }

                  Spacer(modifier = Modifier.height(12.dp))

                  if (!isQuizSubmitted) {
                    Button(
                      onClick = {
                        if (selectedQuizOption != null) {
                          isQuizSubmitted = true
                          if (selectedQuizOption == q.correctOptionIndex) {
                            onComplete()
                          }
                        }
                      },
                      enabled = selectedQuizOption != null,
                      shape = RoundedCornerShape(8.dp),
                      colors = ButtonDefaults.buttonColors(containerColor = CppBluePrimary),
                      modifier = Modifier.fillMaxWidth()
                    ) {
                      Text("Submit Answer", fontWeight = FontWeight.Bold)
                    }
                  } else {
                    Surface(
                      color = if (selectedQuizOption == q.correctOptionIndex) CppAccentEmerald.copy(alpha = 0.15f) else CppAccentRose.copy(alpha = 0.15f),
                      shape = RoundedCornerShape(8.dp),
                      modifier = Modifier.fillMaxWidth()
                    ) {
                      Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                          text = if (selectedQuizOption == q.correctOptionIndex) "Correct!" else "Not quite!",
                          fontWeight = FontWeight.Bold,
                          color = if (selectedQuizOption == q.correctOptionIndex) CppAccentEmerald else CppAccentRose,
                          fontSize = 13.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                          text = q.explanation,
                          fontSize = 12.sp,
                          color = Color(0xFFE2E8F0)
                        )
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  // Note dialog
  if (showNoteDialog) {
    AlertDialog(
      onDismissRequest = { showNoteDialog = false },
      title = { Text("Take Learning Note", fontWeight = FontWeight.Bold, color = Color.White) },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          OutlinedTextField(
            value = noteTitle,
            onValueChange = { noteTitle = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
          )
          OutlinedTextField(
            value = noteContent,
            onValueChange = { noteContent = it },
            label = { Text("Key insights, memory tips, syntax rules...") },
            minLines = 4,
            modifier = Modifier.fillMaxWidth()
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            if (noteContent.isNotBlank()) {
              onSaveNote(noteTitle, noteContent, "Level ${lesson.levelId}")
              showNoteDialog = false
              noteSavedMessage = true
            }
          },
          colors = ButtonDefaults.buttonColors(containerColor = CppBluePrimary)
        ) {
          Text("Save to Room DB")
        }
      },
      dismissButton = {
        TextButton(onClick = { showNoteDialog = false }) {
          Text("Cancel", color = Color(0xFF94A3B8))
        }
      },
      containerColor = CppSurfaceDark
    )
  }
}
