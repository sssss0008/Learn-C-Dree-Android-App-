package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.screens.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppViewModel
import com.example.ui.viewmodel.MainTab

class MainActivity : ComponentActivity() {

  private val viewModel: AppViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme(darkTheme = true) {
        MainAppContent(viewModel = viewModel)
      }
    }
  }
}

@Composable
fun MainAppContent(viewModel: AppViewModel) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  var moreSubSection by remember { mutableIntStateOf(0) } // 0: AI Tutor, 1: Projects, 2: Reference, 3: Profile

  // If user is not yet onboarded, show Onboarding
  if (!uiState.isOnboarded) {
    OnboardingScreen(
      onComplete = { name, exp, goals ->
        viewModel.finishOnboarding(name, exp, goals)
      }
    )
    return
  }

  // If user selected a lesson, drill down to LessonDetailScreen
  if (uiState.selectedLesson != null) {
    val lesson = uiState.selectedLesson!!
    val isDone = uiState.completedLessonIds.contains(lesson.id)
    LessonDetailScreen(
      lesson = lesson,
      isCompleted = isDone,
      onComplete = { viewModel.completeLesson(lesson.id) },
      onBack = { viewModel.selectLesson(null) },
      onSaveNote = { title, content, topic -> viewModel.addNote(title, content, topic) }
    )
    return
  }

  // Main App Scaffold
  Scaffold(
    modifier = Modifier.fillMaxSize(),
    containerColor = CppBackgroundDark,
    bottomBar = {
      NavigationBar(
        containerColor = CppSurfaceDark,
        tonalElevation = 8.dp
      ) {
        val navItems = listOf(
          NavigationItem(MainTab.HOME, "Home", Icons.Default.Home),
          NavigationItem(MainTab.LEARN, "Learn", Icons.Default.School),
          NavigationItem(MainTab.CODE, "Code", Icons.Default.Code),
          NavigationItem(MainTab.LABS, "Labs", Icons.Default.Science),
          NavigationItem(MainTab.PRACTICE, "Practice", Icons.Default.SportsEsports),
          NavigationItem(MainTab.MORE, "Hub", Icons.Default.Apps)
        )

        navItems.forEach { item ->
          val selected = uiState.selectedTab == item.tab
          NavigationBarItem(
            selected = selected,
            onClick = { viewModel.setTab(item.tab) },
            icon = {
              Icon(
                imageVector = item.icon,
                contentDescription = item.label,
                modifier = Modifier.size(20.dp)
              )
            },
            label = {
              Text(
                text = item.label,
                fontSize = 10.sp,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
              )
            },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = Color.White,
              selectedTextColor = CppAccentCyan,
              indicatorColor = CppBluePrimary,
              unselectedIconColor = Color(0xFF64748B),
              unselectedTextColor = Color(0xFF64748B)
            ),
            modifier = Modifier.testTag("nav_tab_${item.label.lowercase()}")
          )
        }
      }
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      when (uiState.selectedTab) {
        MainTab.HOME -> {
          HomeScreen(
            uiState = uiState,
            onNavigateTab = { tab -> viewModel.setTab(tab) },
            onSelectLesson = { lesson -> viewModel.selectLesson(lesson) }
          )
        }

        MainTab.LEARN -> {
          LearnScreen(
            uiState = uiState,
            onSelectLesson = { lesson -> viewModel.selectLesson(lesson) }
          )
        }

        MainTab.CODE -> {
          CodePlaygroundScreen()
        }

        MainTab.LABS -> {
          LabsScreen()
        }

        MainTab.PRACTICE -> {
          PracticeScreen(
            uiState = uiState,
            onSolveChallenge = { chId -> viewModel.solveChallenge(chId) }
          )
        }

        MainTab.MORE -> {
          // Hub Screen with Sub-Tabs: AI Tutor, Projects, Reference, Profile
          Column(modifier = Modifier.fillMaxSize()) {
            Surface(
              color = CppSurfaceDark,
              border = androidx.compose.foundation.BorderStroke(1.dp, CppBorderDark)
            ) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
              ) {
                val hubSections = listOf("AI Tutor", "Projects", "Reference", "Profile")
                hubSections.forEachIndexed { i, name ->
                  val isSel = moreSubSection == i
                  Box(
                    modifier = Modifier
                      .weight(1f)
                      .clip(RoundedCornerShape(8.dp))
                      .background(if (isSel) CppBluePrimary else CppSurfaceCard)
                      .border(
                        1.dp,
                        if (isSel) CppAccentCyan else CppBorderDark,
                        RoundedCornerShape(8.dp)
                      )
                      .clickable { moreSubSection = i }
                      .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                  ) {
                    Text(
                      text = name,
                      fontSize = 11.sp,
                      fontWeight = if (isSel) FontWeight.Bold else FontWeight.Medium,
                      color = if (isSel) Color.White else Color(0xFF94A3B8)
                    )
                  }
                }
              }
            }

            Box(modifier = Modifier.weight(1f)) {
              when (moreSubSection) {
                0 -> AiTutorScreen(
                  chatMessages = uiState.tutorChat,
                  onSendMessage = { prompt -> viewModel.sendTutorMessage(prompt) }
                )
                1 -> ProjectsScreen()
                2 -> ReferenceScreen()
                3 -> ProfileScreen(
                  uiState = uiState,
                  onShowCertificate = { viewModel.setCertificateVisible(true) },
                  onDeleteNote = { id -> viewModel.deleteNote(id) }
                )
              }
            }
          }
        }
      }

      // Certificate Popup Dialog
      if (uiState.isCertificateVisible) {
        CertificateDialog(
          userName = uiState.userName,
          onDismiss = { viewModel.setCertificateVisible(false) }
        )
      }
    }
  }
}

data class NavigationItem(
  val tab: MainTab,
  val label: String,
  val icon: ImageVector
)
