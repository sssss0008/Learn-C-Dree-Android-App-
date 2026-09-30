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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CppProject
import com.example.data.repository.CppContentRepository
import com.example.ui.components.CppCodeEditor
import com.example.ui.components.FreeBadge
import com.example.ui.theme.*

@Composable
fun ProjectsScreen(
  modifier: Modifier = Modifier
) {
  var selectedProject by remember { mutableStateOf<CppProject?>(null) }
  var selectedFileIndex by remember { mutableIntStateOf(0) }

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
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.FolderSpecial,
            contentDescription = null,
            tint = CppAccentPurple,
            modifier = Modifier.size(22.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "REAL C++ PROJECTS",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White
          )
        }
        FreeBadge()
      }
    }

    if (selectedProject == null) {
      // Projects Catalog
      LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 90.dp)
      ) {
        item {
          Text(
            text = "Multi-File Modular Software Projects",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White
          )
          Text(
            text = "Explore architecture with header files (.hpp), implementations (.cpp), and build tests:",
            fontSize = 12.sp,
            color = Color(0xFF94A3B8)
          )
        }

        items(CppContentRepository.projects) { proj ->
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .clickable {
                selectedProject = proj
                selectedFileIndex = 0
              },
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
                  text = proj.title,
                  fontWeight = FontWeight.Bold,
                  color = Color.White,
                  fontSize = 15.sp
                )
                Surface(
                  color = CppAccentPurple.copy(alpha = 0.2f),
                  shape = RoundedCornerShape(6.dp)
                ) {
                  Text(
                    text = proj.level,
                    color = CppAccentPurple,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                  )
                }
              }

              Spacer(modifier = Modifier.height(6.dp))
              Text(
                text = proj.description,
                fontSize = 12.sp,
                color = Color(0xFFCBD5E1)
              )

              Spacer(modifier = Modifier.height(10.dp))
              LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                items(proj.conceptsUsed) { concept ->
                  Surface(
                    color = CppSurfaceDark,
                    shape = RoundedCornerShape(6.dp)
                  ) {
                    Text(
                      text = concept,
                      fontSize = 10.sp,
                      color = CppBlueLight,
                      modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                  }
                }
              }

              Spacer(modifier = Modifier.height(12.dp))
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = "${proj.files.size} Source & Header Files",
                  fontSize = 11.sp,
                  color = Color(0xFF94A3B8)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(
                    text = "Open Workspace",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = CppAccentCyan
                  )
                  Spacer(modifier = Modifier.width(4.dp))
                  Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint = CppAccentCyan,
                    modifier = Modifier.size(14.dp)
                  )
                }
              }
            }
          }
        }
      }
    } else {
      // Project Workspace
      val proj = selectedProject!!
      val currentFile = proj.files.getOrNull(selectedFileIndex) ?: proj.files.first()

      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(horizontal = 14.dp, vertical = 8.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically
        ) {
          IconButton(onClick = { selectedProject = null }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
          }
          Column {
            Text(proj.title, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 15.sp)
            Text("Workspace • ${proj.category}", fontSize = 11.sp, color = CppAccentCyan)
          }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // File tabs
        LazyRow(
          horizontalArrangement = Arrangement.spacedBy(6.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          items(proj.files.indices.toList()) { idx ->
            val file = proj.files[idx]
            val isSel = selectedFileIndex == idx
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(if (isSel) CppBluePrimary else CppSurfaceCard)
                .border(1.dp, if (isSel) CppAccentCyan else CppBorderDark, RoundedCornerShape(8.dp))
                .clickable { selectedFileIndex = idx }
                .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = if (file.isHeader) Icons.Default.DataObject else Icons.Default.Code,
                  contentDescription = null,
                  tint = if (isSel) Color.White else CppBlueLight,
                  modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = file.name,
                  fontFamily = FontFamily.Monospace,
                  fontSize = 11.sp,
                  fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                  color = if (isSel) Color.White else Color(0xFFCBD5E1)
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        CppCodeEditor(
          initialCode = currentFile.content,
          expectedOutput = proj.expectedOutput,
          modifier = Modifier.weight(1f)
        )
      }
    }
  }
}
