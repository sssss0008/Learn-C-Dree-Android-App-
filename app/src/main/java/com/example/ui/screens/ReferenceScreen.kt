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
import com.example.data.repository.CppContentRepository
import com.example.ui.components.FreeBadge
import com.example.ui.theme.*

@Composable
fun ReferenceScreen(
  modifier: Modifier = Modifier
) {
  var searchQuery by remember { mutableStateOf("") }
  var selectedTab by remember { mutableIntStateOf(0) } // 0: Reference Library, 1: Cheat Sheets, 2: Glossary

  val glossaryTerms = remember {
    listOf(
      "RAII" to "Resource Acquisition Is Initialization: binds resource lifetime to object scope; releases in destructor.",
      "Vtable" to "Virtual Method Table: array of function pointers used by C++ runtime for dynamic dispatch.",
      "Move Semantics" to "Transfers ownership of resources from temporary rvalue objects without copying deep memory buffers.",
      "Smart Pointer" to "Class template (unique_ptr, shared_ptr) that manages dynamic memory lifetime safely via RAII.",
      "Lvalue vs Rvalue" to "Lvalue has an identifiable memory address; Rvalue is a temporary value expiring at expression end.",
      "constexpr" to "Specifier indicating that the value or function can be evaluated at compile time.",
      "Lambda" to "Anonymous function object capturing variables by value [=] or reference [&].",
      "Deadlock" to "Concurrency condition where two or more threads wait indefinitely on locks held by each other."
    )
  }

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
              imageVector = Icons.Default.MenuBook,
              contentDescription = null,
              tint = CppBlueLight,
              modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "C++ REFERENCE & CHEAT SHEETS",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = Color.White
            )
          }
          FreeBadge()
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Search Input
        OutlinedTextField(
          value = searchQuery,
          onValueChange = { searchQuery = it },
          placeholder = { Text("Search keywords, containers, smart pointers...", fontSize = 12.sp) },
          leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = Color(0xFF94A3B8))
          },
          singleLine = true,
          shape = RoundedCornerShape(12.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = CppBlueLight,
            unfocusedBorderColor = CppBorderDark,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedPlaceholderColor = Color(0xFF64748B),
            unfocusedPlaceholderColor = Color(0xFF64748B)
          ),
          modifier = Modifier.fillMaxWidth().testTag("reference_search")
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Tabs
        val tabs = listOf("Reference API", "Cheat Sheets", "Glossary")
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(CppSurfaceCard)
            .padding(3.dp)
        ) {
          tabs.forEachIndexed { i, title ->
            val isSel = selectedTab == i
            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(6.dp))
                .background(if (isSel) CppBluePrimary else Color.Transparent)
                .clickable { selectedTab = i }
                .padding(vertical = 7.dp),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = title,
                fontSize = 11.sp,
                fontWeight = if (isSel) FontWeight.Bold else FontWeight.Medium,
                color = if (isSel) Color.White else Color(0xFF94A3B8)
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
      when (selectedTab) {
        0 -> {
          // Reference API Items
          val filteredRefs = if (searchQuery.isBlank()) {
            CppContentRepository.references
          } else {
            CppContentRepository.references.filter {
              it.title.contains(searchQuery, ignoreCase = true) ||
              it.category.contains(searchQuery, ignoreCase = true) ||
              it.explanation.contains(searchQuery, ignoreCase = true)
            }
          }

          LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(top = 14.dp, bottom = 90.dp)
          ) {
            items(filteredRefs) { ref ->
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
                      text = ref.title,
                      fontWeight = FontWeight.Bold,
                      color = Color.White,
                      fontSize = 15.sp
                    )
                    Surface(
                      color = CppBluePrimary.copy(alpha = 0.2f),
                      shape = RoundedCornerShape(6.dp)
                    ) {
                      Text(
                        text = ref.category,
                        color = CppBlueLight,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                      )
                    }
                  }

                  Spacer(modifier = Modifier.height(6.dp))
                  Text(text = ref.explanation, fontSize = 12.sp, color = Color(0xFFCBD5E1))

                  Spacer(modifier = Modifier.height(10.dp))
                  Text(text = "SYNTAX & USAGE:", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = CppAccentCyan)
                  Surface(
                    color = CodeBackground,
                    shape = RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CppBorderDark),
                    modifier = Modifier.fillMaxWidth()
                  ) {
                    Text(
                      text = ref.codeExample,
                      fontFamily = FontFamily.Monospace,
                      fontSize = 11.sp,
                      color = Color(0xFFCBD5E1),
                      modifier = Modifier.padding(10.dp)
                    )
                  }

                  Spacer(modifier = Modifier.height(8.dp))
                  Text(
                    text = "⚠️ Common Pitfall: ${ref.commonMistakes}",
                    fontSize = 11.sp,
                    color = CppAccentAmber
                  )
                  Spacer(modifier = Modifier.height(4.dp))
                  Text(
                    text = "✅ Best Practice: ${ref.bestPractice}",
                    fontSize = 11.sp,
                    color = CppAccentEmerald
                  )
                }
              }
            }
          }
        }

        1 -> {
          // Cheat Sheets
          LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(top = 14.dp, bottom = 90.dp)
          ) {
            items(CppContentRepository.cheatSheets) { sheet ->
              Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = CppSurfaceCard),
                border = androidx.compose.foundation.BorderStroke(1.dp, CppBorderDark)
              ) {
                Column(modifier = Modifier.padding(14.dp)) {
                  Text(
                    text = sheet.title,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 15.sp
                  )
                  Spacer(modifier = Modifier.height(4.dp))
                  Text(text = sheet.description, fontSize = 11.sp, color = Color(0xFF94A3B8))

                  Spacer(modifier = Modifier.height(10.dp))

                  sheet.sections.forEach { sec ->
                    Surface(
                      color = CodeBackground,
                      shape = RoundedCornerShape(8.dp),
                      border = androidx.compose.foundation.BorderStroke(1.dp, CppBorderDark),
                      modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp)
                    ) {
                      Column(modifier = Modifier.padding(8.dp)) {
                        Text(sec.title, fontWeight = FontWeight.Bold, color = CppAccentCyan, fontSize = 11.sp)
                        Text(sec.syntax, fontFamily = FontFamily.Monospace, color = Color.White, fontSize = 11.sp)
                        Text(sec.note, color = Color(0xFF94A3B8), fontSize = 10.sp)
                      }
                    }
                  }
                }
              }
            }
          }
        }

        2 -> {
          // Glossary
          LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(top = 14.dp, bottom = 90.dp)
          ) {
            items(glossaryTerms) { (term, definition) ->
              Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = CppSurfaceCard),
                border = androidx.compose.foundation.BorderStroke(1.dp, CppBorderDark)
              ) {
                Column(modifier = Modifier.padding(12.dp)) {
                  Text(
                    text = term,
                    fontWeight = FontWeight.Bold,
                    color = CppAccentCyan,
                    fontSize = 13.sp
                  )
                  Spacer(modifier = Modifier.height(3.dp))
                  Text(
                    text = definition,
                    color = Color(0xFFCBD5E1),
                    fontSize = 12.sp,
                    lineHeight = 17.sp
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
