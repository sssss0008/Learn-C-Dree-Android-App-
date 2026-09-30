package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.CppLogoBadge
import com.example.ui.components.FreeBadge
import com.example.ui.theme.*

@Composable
fun OnboardingScreen(
  onComplete: (name: String, experience: String, goals: Set<String>) -> Unit,
  modifier: Modifier = Modifier
) {
  var step by remember { mutableIntStateOf(0) }
  var name by remember { mutableStateOf("Awiskar") }
  var selectedExperience by remember { mutableStateOf("Intermediate") }
  val selectedGoals = remember {
    mutableStateListOf("Data Structures", "Algorithms", "Competitive Programming", "System Programming")
  }

  val experienceOptions = listOf(
    "Complete Beginner" to "Never written code before",
    "Beginner" to "Know syntax basics (loops, if/else)",
    "Familiar with C" to "Understand pointers, structs, and memory",
    "Familiar with another language" to "Experience in Python, Java, or JS",
    "Intermediate" to "Comfortable with OOP, arrays, functions",
    "Advanced" to "Looking to master Modern C++ & low-level design"
  )

  val goalOptions = listOf(
    "Learn Programming", "College Course", "Data Structures", "Algorithms",
    "Competitive Programming", "Software Development", "Game Development",
    "System Programming", "Embedded Programming", "Interview Preparation",
    "Problem Solving", "Learn Modern C++"
  )

  Surface(
    modifier = modifier.fillMaxSize(),
    color = CppBackgroundDark
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .navigationBarsPadding()
        .padding(20.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // Progress indicators
      Row(
        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        repeat(5) { i ->
          Box(
            modifier = Modifier
              .weight(1f)
              .height(4.dp)
              .clip(CircleShape)
              .background(if (i <= step) CppBlueLight else CppBorderDark)
          )
        }
      }

      Spacer(modifier = Modifier.height(24.dp))

      AnimatedContent(
        targetState = step,
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        modifier = Modifier.weight(1f),
        label = "onboarding_steps"
      ) { currentStep ->
        when (currentStep) {
          0 -> {
            // Screen 1: Welcome
            Column(
              modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.Center
            ) {
              CppLogoBadge(size = 72)
              Spacer(modifier = Modifier.height(16.dp))
              FreeBadge()
              Spacer(modifier = Modifier.height(16.dp))

              Text(
                text = "Welcome to\nLearn C++",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
              )
              Spacer(modifier = Modifier.height(8.dp))
              Text(
                text = "by Awiskar Acharya",
                style = MaterialTheme.typography.titleMedium,
                color = CppBlueLight,
                fontWeight = FontWeight.SemiBold
              )
              Spacer(modifier = Modifier.height(12.dp))
              Text(
                text = "Build powerful programming skills from the fundamentals to advanced modern C++ with interactive IDE, memory visualizers, STL laboratories, and competitive coding.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF94A3B8),
                textAlign = TextAlign.Center,
                lineHeight = 20.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
              )
              Spacer(modifier = Modifier.height(24.dp))

              // Feature highlight pills
              Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Surface(
                  color = CppSurfaceCard,
                  shape = RoundedCornerShape(8.dp),
                  border = androidx.compose.foundation.BorderStroke(1.dp, CppBorderDark)
                ) {
                  Text(
                    text = "Interactive IDE",
                    color = Color(0xFFE2E8F0),
                    fontSize = 11.sp,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                  )
                }
                Surface(
                  color = CppSurfaceCard,
                  shape = RoundedCornerShape(8.dp),
                  border = androidx.compose.foundation.BorderStroke(1.dp, CppBorderDark)
                ) {
                  Text(
                    text = "Memory Lab",
                    color = Color(0xFFE2E8F0),
                    fontSize = 11.sp,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                  )
                }
                Surface(
                  color = CppSurfaceCard,
                  shape = RoundedCornerShape(8.dp),
                  border = androidx.compose.foundation.BorderStroke(1.dp, CppBorderDark)
                ) {
                  Text(
                    text = "Modern C++20",
                    color = Color(0xFFE2E8F0),
                    fontSize = 11.sp,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                  )
                }
              }
            }
          }

          1 -> {
            // Screen 2: Name
            Column(
              modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
              verticalArrangement = Arrangement.Center
            ) {
              Text(
                text = "What should we call you?",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
              )
              Spacer(modifier = Modifier.height(8.dp))
              Text(
                text = "Your name will be used across your dashboard and completion certificate.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF94A3B8)
              )
              Spacer(modifier = Modifier.height(24.dp))

              OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Your Name") },
                placeholder = { Text("e.g. Awiskar") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                  focusedBorderColor = CppBlueLight,
                  unfocusedBorderColor = CppBorderDark,
                  focusedTextColor = Color.White,
                  unfocusedTextColor = Color.White,
                  focusedLabelColor = CppBlueLight,
                  unfocusedLabelColor = Color(0xFF94A3B8)
                ),
                modifier = Modifier.fillMaxWidth().testTag("name_input")
              )
            }
          }

          2 -> {
            // Screen 3: Experience Level
            Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
              Text(
                text = "What is your programming experience?",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
              )
              Spacer(modifier = Modifier.height(6.dp))
              Text(
                text = "We will adapt lessons and challenge difficulty accordingly.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF94A3B8)
              )
              Spacer(modifier = Modifier.height(16.dp))

              experienceOptions.forEach { (title, subtitle) ->
                val isSelected = selectedExperience == title
                Box(
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (isSelected) CppBlueDark.copy(alpha = 0.5f) else CppSurfaceCard)
                    .border(
                      1.dp,
                      if (isSelected) CppAccentCyan else CppBorderDark,
                      RoundedCornerShape(12.dp)
                    )
                    .clickable { selectedExperience = title }
                    .padding(14.dp)
                ) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                      selected = isSelected,
                      onClick = { selectedExperience = title },
                      colors = RadioButtonDefaults.colors(selectedColor = CppAccentCyan)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                      Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 14.sp
                      )
                      Text(
                        text = subtitle,
                        color = Color(0xFF94A3B8),
                        fontSize = 12.sp
                      )
                    }
                  }
                }
              }
            }
          }

          3 -> {
            // Screen 4: Learning Goals
            Column(modifier = Modifier.fillMaxSize()) {
              Text(
                text = "What do you want to use C++ for?",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
              )
              Spacer(modifier = Modifier.height(6.dp))
              Text(
                text = "Select all that apply to calibrate your curriculum roadmap.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF94A3B8)
              )
              Spacer(modifier = Modifier.height(16.dp))

              LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
              ) {
                items(goalOptions) { goal ->
                  val isSelected = selectedGoals.contains(goal)
                  Box(
                    modifier = Modifier
                      .clip(RoundedCornerShape(10.dp))
                      .background(if (isSelected) CppBluePrimary else CppSurfaceCard)
                      .border(
                        1.dp,
                        if (isSelected) CppAccentCyan else CppBorderDark,
                        RoundedCornerShape(10.dp)
                      )
                      .clickable {
                        if (isSelected) selectedGoals.remove(goal)
                        else selectedGoals.add(goal)
                      }
                      .padding(12.dp),
                    contentAlignment = Alignment.Center
                  ) {
                    Text(
                      text = goal,
                      color = if (isSelected) Color.White else Color(0xFFE2E8F0),
                      fontSize = 12.sp,
                      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                      textAlign = TextAlign.Center
                    )
                  }
                }
              }
            }
          }

          4 -> {
            // Screen 5: Personalized Roadmap Preview
            Column(
              modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Text(
                text = "Your C++ Journey",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = "Tailored for $name based on your goals",
                style = MaterialTheme.typography.bodyMedium,
                color = CppBlueLight
              )
              Spacer(modifier = Modifier.height(16.dp))

              val roadmapSteps = listOf(
                "C++ Fundamentals & Build Pipeline",
                "Control Flow & Functions",
                "Pointers, References & Addresses",
                "Object-Oriented Programming (OOP)",
                "Stack, Heap & RAII Memory",
                "Templates & Generic Code",
                "Standard Template Library (STL)",
                "Data Structures & Algorithms",
                "Modern C++ (C++17 / C++20 / C++23)",
                "Real Projects & Interview Mastery"
              )

              roadmapSteps.forEachIndexed { idx, st ->
                Row(
                  modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Box(
                    modifier = Modifier
                      .size(24.dp)
                      .clip(CircleShape)
                      .background(CppBlueDark),
                    contentAlignment = Alignment.Center
                  ) {
                    Text(
                      text = "${idx + 1}",
                      fontSize = 11.sp,
                      fontWeight = FontWeight.Bold,
                      color = CppAccentCyan
                    )
                  }
                  Spacer(modifier = Modifier.width(10.dp))
                  Text(
                    text = st,
                    fontSize = 13.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                  )
                }
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Navigation button row
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        if (step > 0) {
          OutlinedButton(
            onClick = { step-- },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFE2E8F0)),
            modifier = Modifier.weight(1f)
          ) {
            Text("Back")
          }
        }

        Button(
          onClick = {
            if (step < 4) {
              step++
            } else {
              onComplete(name, selectedExperience, selectedGoals.toSet())
            }
          },
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.buttonColors(containerColor = CppBluePrimary),
          modifier = Modifier.weight(1.5f).testTag("onboarding_continue_button")
        ) {
          Text(
            text = if (step == 0) "Start Learning" else if (step == 4) "Enter C++ Academy" else "Continue",
            fontWeight = FontWeight.Bold,
            color = Color.White
          )
        }
      }
    }
  }
}
