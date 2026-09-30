package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.example.data.model.CodingChallenge
import com.example.data.model.DebuggingChallenge
import com.example.data.model.OutputPredictionQuiz
import com.example.data.repository.CppContentRepository
import com.example.ui.components.CppCodeEditor
import com.example.ui.components.FreeBadge
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppUiState

@Composable
fun PracticeScreen(
  uiState: AppUiState,
  onSolveChallenge: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  var practiceMode by remember { mutableIntStateOf(0) } // 0: CP Challenges, 1: Output Prediction, 2: Debugging Traps
  var selectedChallenge by remember { mutableStateOf<CodingChallenge?>(null) }
  var selectedHintLevel by remember { mutableIntStateOf(0) } // 0: none, 1: concept, 2: logic, 3: strong, 4: full
  var showSolution by remember { mutableStateOf(false) }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(CppBackgroundDark)
  ) {
    // Header
    Surface(
      color = CppSurfaceDark,
      border = androidx.compose.foundation.BorderStroke(1.dp, CppBorderDark)
    ) {
      Column(modifier = Modifier.padding(14.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.SportsEsports,
              contentDescription = null,
              tint = CppAccentEmerald,
              modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "PRACTICE & PROBLEM SOLVING",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = Color.White
            )
          }
          FreeBadge()
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Tabs
        val modes = listOf("Coding Challenges", "Output Prediction", "Debugging Traps")
        LazyRow(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          items(modes.indices.toList()) { idx ->
            val isSel = practiceMode == idx
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(if (isSel) CppBluePrimary else CppSurfaceCard)
                .border(1.dp, if (isSel) CppAccentCyan else CppBorderDark, RoundedCornerShape(8.dp))
                .clickable {
                  practiceMode = idx
                  selectedChallenge = null
                }
                .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
              Text(
                text = modes[idx],
                fontSize = 12.sp,
                fontWeight = if (isSel) FontWeight.Bold else FontWeight.Medium,
                color = if (isSel) Color.White else Color(0xFFCBD5E1)
              )
            }
          }
        }
      }
    }

    // Body
    Box(
      modifier = Modifier
        .weight(1f)
        .padding(horizontal = 16.dp)
    ) {
      when (practiceMode) {
        0 -> {
          // Coding Challenges
          if (selectedChallenge == null) {
            LazyColumn(
              modifier = Modifier.fillMaxSize(),
              verticalArrangement = Arrangement.spacedBy(10.dp),
              contentPadding = PaddingValues(top = 14.dp, bottom = 90.dp)
            ) {
              item {
                Text(
                  text = "Competitive Programming Problems",
                  style = MaterialTheme.typography.titleMedium,
                  fontWeight = FontWeight.Bold,
                  color = Color.White
                )
                Text(
                  text = "Solve with modern C++ standard library, test cases, and tiered hints:",
                  fontSize = 12.sp,
                  color = Color(0xFF94A3B8)
                )
              }

              items(CppContentRepository.codingChallenges) { challenge ->
                val isSolved = uiState.solvedChallengeIds.contains(challenge.id)
                Card(
                  modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                      selectedChallenge = challenge
                      selectedHintLevel = 0
                      showSolution = false
                    },
                  shape = RoundedCornerShape(12.dp),
                  colors = CardDefaults.cardColors(containerColor = CppSurfaceCard),
                  border = androidx.compose.foundation.BorderStroke(1.dp, CppBorderDark)
                ) {
                  Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                  ) {
                    Column(modifier = Modifier.weight(1f)) {
                      Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                          text = challenge.title,
                          fontWeight = FontWeight.Bold,
                          color = Color.White,
                          fontSize = 14.sp
                        )
                        if (isSolved) {
                          Spacer(modifier = Modifier.width(6.dp))
                          Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Solved",
                            tint = CppAccentEmerald,
                            modifier = Modifier.size(16.dp)
                          )
                        }
                      }
                      Spacer(modifier = Modifier.height(4.dp))
                      Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                          text = challenge.difficulty,
                          color = if (challenge.difficulty == "Easy") CppAccentEmerald else CppAccentAmber,
                          fontWeight = FontWeight.Bold,
                          fontSize = 11.sp
                        )
                        Text("•", color = Color(0xFF64748B), fontSize = 11.sp)
                        Text(challenge.category, color = CppBlueLight, fontSize = 11.sp)
                        Text("•", color = Color(0xFF64748B), fontSize = 11.sp)
                        Text("Time: ${challenge.timeComplexity}", color = Color(0xFF94A3B8), fontSize = 11.sp)
                      }
                    }

                    Icon(
                      imageVector = Icons.Default.ChevronRight,
                      contentDescription = null,
                      tint = Color(0xFF94A3B8)
                    )
                  }
                }
              }
            }
          } else {
            // Selected Challenge Workspace
            val ch = selectedChallenge!!
            LazyColumn(
              modifier = Modifier.fillMaxSize(),
              verticalArrangement = Arrangement.spacedBy(12.dp),
              contentPadding = PaddingValues(top = 14.dp, bottom = 90.dp)
            ) {
              item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  IconButton(onClick = { selectedChallenge = null }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                  }
                  Column {
                    Text(ch.title, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 16.sp)
                    Text("${ch.difficulty} • ${ch.category}", fontSize = 11.sp, color = CppAccentCyan)
                  }
                }
              }

              item {
                Surface(
                  color = CppSurfaceCard,
                  shape = RoundedCornerShape(12.dp),
                  border = androidx.compose.foundation.BorderStroke(1.dp, CppBorderDark),
                  modifier = Modifier.fillMaxWidth()
                ) {
                  Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                      text = "Problem Statement",
                      fontWeight = FontWeight.Bold,
                      color = CppBlueLight,
                      fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                      text = ch.description,
                      color = Color(0xFFCBD5E1),
                      fontSize = 13.sp,
                      lineHeight = 19.sp
                    )
                  }
                }
              }

              item {
                // Tiered Hint System
                Surface(
                  color = CppSurfaceCard,
                  shape = RoundedCornerShape(12.dp),
                  border = androidx.compose.foundation.BorderStroke(1.dp, CppAccentAmber.copy(alpha = 0.4f)),
                  modifier = Modifier.fillMaxWidth()
                ) {
                  Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                      text = "Need help? Tiered Hint System",
                      fontWeight = FontWeight.Bold,
                      color = CppAccentAmber,
                      fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                      horizontalArrangement = Arrangement.spacedBy(6.dp),
                      modifier = Modifier.fillMaxWidth()
                    ) {
                      listOf("Concept", "Logic", "Strong Hint").forEachIndexed { i, label ->
                        val isLevelActive = selectedHintLevel >= (i + 1)
                        OutlinedButton(
                          onClick = { selectedHintLevel = i + 1 },
                          colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (isLevelActive) CppAccentAmber.copy(alpha = 0.2f) else Color.Transparent
                          ),
                          shape = RoundedCornerShape(6.dp),
                          contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                          modifier = Modifier.weight(1f)
                        ) {
                          Text(label, fontSize = 10.sp, color = if (isLevelActive) CppAccentAmber else Color(0xFFCBD5E1))
                        }
                      }
                    }

                    if (selectedHintLevel >= 1) {
                      Spacer(modifier = Modifier.height(8.dp))
                      Text(
                        text = when (selectedHintLevel) {
                          1 -> "💡 Concept: ${ch.conceptHint}"
                          2 -> "🧩 Logic: ${ch.logicHint}"
                          else -> "🔥 Strong Hint: ${ch.strongHint}"
                        },
                        fontSize = 12.sp,
                        color = Color.White
                      )
                    }
                  }
                }
              }

              item {
                Text(
                  text = "Write Your C++ Solution",
                  fontWeight = FontWeight.Bold,
                  color = Color.White,
                  fontSize = 14.sp
                )
                CppCodeEditor(
                  initialCode = ch.starterCode,
                  expectedOutput = ch.testCases.firstOrNull()?.expectedOutput
                )
              }

              item {
                Button(
                  onClick = {
                    onSolveChallenge(ch.id)
                  },
                  colors = ButtonDefaults.buttonColors(containerColor = CppAccentEmerald),
                  shape = RoundedCornerShape(8.dp),
                  modifier = Modifier.fillMaxWidth()
                ) {
                  Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null)
                  Spacer(modifier = Modifier.width(6.dp))
                  Text("Submit & Verify Test Cases", fontWeight = FontWeight.Bold)
                }
              }
            }
          }
        }

        1 -> {
          // Output Prediction Quizzes ("What Will This Code Output?")
          LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(top = 14.dp, bottom = 90.dp)
          ) {
            item {
              Text(
                text = "What Will This Code Output?",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
              )
              Text(
                text = "Predict C++ execution behavior, operator precedence, and memory nuances:",
                fontSize = 12.sp,
                color = Color(0xFF94A3B8)
              )
            }

            items(CppContentRepository.outputQuizzes) { quiz ->
              OutputQuizItem(quiz = quiz)
            }
          }
        }

        2 -> {
          // Debugging Traps
          LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(top = 14.dp, bottom = 90.dp)
          ) {
            item {
              Text(
                text = "C++ Error & Debugging Lab",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
              )
              Text(
                text = "Diagnose and fix real traps: dangling pointers, memory leaks, and iterator invalidation:",
                fontSize = 12.sp,
                color = Color(0xFF94A3B8)
              )
            }

            items(CppContentRepository.debuggingChallenges) { dbg ->
              DebuggingChallengeItem(challenge = dbg)
            }
          }
        }
      }
    }
  }
}

@Composable
fun OutputQuizItem(quiz: OutputPredictionQuiz) {
  var selectedOption by remember { mutableStateOf<Int?>(null) }
  var isSubmitted by remember { mutableStateOf(false) }

  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = CppSurfaceCard),
    border = androidx.compose.foundation.BorderStroke(1.dp, CppBorderDark)
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = quiz.title,
          fontWeight = FontWeight.Bold,
          color = Color.White,
          fontSize = 14.sp
        )
        Surface(
          color = CppBluePrimary.copy(alpha = 0.2f),
          shape = RoundedCornerShape(6.dp)
        ) {
          Text(
            text = quiz.category,
            color = CppBlueLight,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Code snippet
      Surface(
        color = CodeBackground,
        shape = RoundedCornerShape(8.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CppBorderDark),
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(
          text = quiz.code,
          fontFamily = FontFamily.Monospace,
          fontSize = 11.sp,
          color = Color(0xFFCBD5E1),
          modifier = Modifier.padding(10.dp)
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Options
      quiz.options.forEachIndexed { optIndex, optText ->
        val isSel = selectedOption == optIndex
        val isCorrect = optIndex == quiz.correctIndex
        val showResult = isSubmitted

        val bgColor = when {
          showResult && isCorrect -> CppAccentEmerald.copy(alpha = 0.2f)
          showResult && isSel && !isCorrect -> CppAccentRose.copy(alpha = 0.2f)
          isSel -> CppBluePrimary.copy(alpha = 0.25f)
          else -> CppSurfaceDark
        }

        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(bgColor)
            .border(
              1.dp,
              if (showResult && isCorrect) CppAccentEmerald else if (isSel) CppAccentCyan else CppBorderDark,
              RoundedCornerShape(8.dp)
            )
            .clickable(enabled = !isSubmitted) { selectedOption = optIndex }
            .padding(10.dp)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
              selected = isSel,
              onClick = { if (!isSubmitted) selectedOption = optIndex },
              colors = RadioButtonDefaults.colors(selectedColor = CppAccentCyan)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = optText,
              fontFamily = FontFamily.Monospace,
              color = Color.White,
              fontSize = 12.sp
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      if (!isSubmitted) {
        Button(
          onClick = { isSubmitted = true },
          enabled = selectedOption != null,
          shape = RoundedCornerShape(8.dp),
          colors = ButtonDefaults.buttonColors(containerColor = CppBluePrimary),
          modifier = Modifier.fillMaxWidth()
        ) {
          Text("Verify Output", fontWeight = FontWeight.Bold)
        }
      } else {
        Surface(
          color = if (selectedOption == quiz.correctIndex) CppAccentEmerald.copy(alpha = 0.15f) else CppAccentRose.copy(alpha = 0.15f),
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(10.dp)) {
            Text(
              text = if (selectedOption == quiz.correctIndex) "Correct Output!" else "Incorrect Output!",
              fontWeight = FontWeight.Bold,
              color = if (selectedOption == quiz.correctIndex) CppAccentEmerald else CppAccentRose,
              fontSize = 13.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = quiz.explanation, fontSize = 12.sp, color = Color.White)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Common Pitfall: ${quiz.pitfall}", fontSize = 11.sp, color = CppAccentAmber)
          }
        }
      }
    }
  }
}

@Composable
fun DebuggingChallengeItem(challenge: DebuggingChallenge) {
  var isFixedVisible by remember { mutableStateOf(false) }

  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = CppSurfaceCard),
    border = androidx.compose.foundation.BorderStroke(1.dp, CppBorderDark)
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = challenge.title,
          fontWeight = FontWeight.Bold,
          color = Color.White,
          fontSize = 14.sp
        )
        Surface(
          color = CppAccentRose.copy(alpha = 0.2f),
          shape = RoundedCornerShape(6.dp)
        ) {
          Text(
            text = challenge.category,
            color = CppAccentRose,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(8.dp))
      Text(
        text = challenge.errorDescription,
        color = Color(0xFFCBD5E1),
        fontSize = 12.sp
      )

      Spacer(modifier = Modifier.height(10.dp))
      Text(
        text = "BUGGY CODE:",
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        color = CppAccentRose
      )
      Surface(
        color = CodeBackground,
        shape = RoundedCornerShape(8.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CppAccentRose.copy(alpha = 0.4f)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(
          text = challenge.buggyCode,
          fontFamily = FontFamily.Monospace,
          fontSize = 11.sp,
          color = Color(0xFFFCA5A5),
          modifier = Modifier.padding(10.dp)
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      Button(
        onClick = { isFixedVisible = !isFixedVisible },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = if (isFixedVisible) CppSurfaceHighlight else CppAccentEmerald
        ),
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(if (isFixedVisible) "Hide Solution" else "Reveal Fixed Safe Code")
      }

      if (isFixedVisible) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
          text = "SAFE FIXED CODE (MODERN C++ IDIOM):",
          fontSize = 10.sp,
          fontWeight = FontWeight.Bold,
          color = CppAccentEmerald
        )
        Surface(
          color = CodeBackground,
          shape = RoundedCornerShape(8.dp),
          border = androidx.compose.foundation.BorderStroke(1.dp, CppAccentEmerald.copy(alpha = 0.4f)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Text(
            text = challenge.fixedCode,
            fontFamily = FontFamily.Monospace,
            fontSize = 11.sp,
            color = Color(0xFF6EE7B7),
            modifier = Modifier.padding(10.dp)
          )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
          text = challenge.solutionWalkthrough,
          fontSize = 12.sp,
          color = Color(0xFFCBD5E1)
        )
      }
    }
  }
}
