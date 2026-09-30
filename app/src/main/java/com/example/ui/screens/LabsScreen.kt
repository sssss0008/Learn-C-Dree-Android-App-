package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.CppContentRepository
import com.example.ui.components.CompilerPipelineView
import com.example.ui.components.MemoryVisualizerView
import com.example.ui.theme.*

@Composable
fun LabsScreen(
  modifier: Modifier = Modifier
) {
  var selectedLabCategory by remember { mutableIntStateOf(0) } // 0: STL Lab, 1: OOP Lab, 2: Memory Lab, 3: DSA & Sorting, 4: Big-O Lab

  // Vector Lab state
  var vectorElements by remember { mutableStateOf(listOf(10, 20, 30, 40, 50)) }
  var vectorCapacity by remember { mutableIntStateOf(8) }

  // Map Lab state
  var mapPairs by remember {
    mutableStateOf(
      listOf("Alice" to 95, "Bob" to 88, "Charlie" to 92, "Awiskar" to 99)
    )
  }

  // Sorting Visualizer state
  var sortArray by remember { mutableStateOf(listOf(64, 25, 12, 22, 11, 90)) }
  var sortStep by remember { mutableIntStateOf(0) }
  var isSorted by remember { mutableStateOf(false) }

  // Class Builder state
  var className by remember { mutableStateOf("Student") }
  var hasNameMember by remember { mutableStateOf(true) }
  var hasGpaMember by remember { mutableStateOf(true) }
  var hasStudyMethod by remember { mutableStateOf(true) }

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
              imageVector = Icons.Default.Science,
              contentDescription = null,
              tint = CppAccentCyan,
              modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "INTERACTIVE C++ LABORATORIES",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = Color.White
            )
          }

          Surface(
            color = CppBluePrimary.copy(alpha = 0.2f),
            shape = RoundedCornerShape(6.dp)
          ) {
            Text(
              text = "Live Sandbox",
              color = CppBlueLight,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Lab Selector Pills
        val labTabs = listOf("STL Lab", "OOP Lab", "Memory Lab", "Sorting & DSA", "Big-O Lab")
        LazyRow(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          items(labTabs.indices.toList()) { idx ->
            val isSel = selectedLabCategory == idx
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(if (isSel) CppBluePrimary else CppSurfaceCard)
                .border(1.dp, if (isSel) CppAccentCyan else CppBorderDark, RoundedCornerShape(8.dp))
                .clickable { selectedLabCategory = idx }
                .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
              Text(
                text = labTabs[idx],
                fontSize = 12.sp,
                fontWeight = if (isSel) FontWeight.Bold else FontWeight.Medium,
                color = if (isSel) Color.White else Color(0xFFCBD5E1)
              )
            }
          }
        }
      }
    }

    // Lab Body
    Box(
      modifier = Modifier
        .weight(1f)
        .padding(14.dp)
    ) {
      when (selectedLabCategory) {
        0 -> {
          // STL Lab: std::vector & std::map
          Column(
            modifier = Modifier
              .fillMaxSize()
              .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
          ) {
            // Vector Visualizer
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
                    text = "std::vector<int> Visualizer",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                  )
                  Surface(
                    color = CppAccentEmerald.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(6.dp)
                  ) {
                    Text(
                      text = "Size: ${vectorElements.size} | Capacity: $vectorCapacity",
                      color = CppAccentEmerald,
                      fontFamily = FontFamily.Monospace,
                      fontWeight = FontWeight.Bold,
                      fontSize = 11.sp,
                      modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                  }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                  text = "Contiguous dynamic array. If size exceeds capacity, vector reallocates with 1.5x or 2x geometric growth.",
                  fontSize = 12.sp,
                  color = Color(0xFF94A3B8)
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Buffer slots
                Row(
                  modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                  horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                  repeat(vectorCapacity) { idx ->
                    val hasItem = idx < vectorElements.size
                    Box(
                      modifier = Modifier
                        .size(46.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (hasItem) CppBlueDark else CodeBackground)
                        .border(
                          1.dp,
                          if (hasItem) CppBlueLight else CppBorderDark,
                          RoundedCornerShape(8.dp)
                        ),
                      contentAlignment = Alignment.Center
                    ) {
                      Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                          text = if (hasItem) "${vectorElements[idx]}" else "",
                          fontFamily = FontFamily.Monospace,
                          fontWeight = FontWeight.Bold,
                          color = if (hasItem) Color.White else Color(0xFF475569),
                          fontSize = 13.sp
                        )
                        Text(
                          text = "[$idx]",
                          fontFamily = FontFamily.Monospace,
                          fontSize = 9.sp,
                          color = Color(0xFF64748B)
                        )
                      }
                    }
                  }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Operations buttons
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                  Button(
                    onClick = {
                      val nextVal = (vectorElements.size + 1) * 10
                      var newCap = vectorCapacity
                      if (vectorElements.size >= vectorCapacity) {
                        newCap = if (vectorCapacity == 0) 1 else vectorCapacity * 2
                      }
                      vectorCapacity = newCap
                      vectorElements = vectorElements + nextVal
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = CppBluePrimary),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                  ) {
                    Text("+ push_back()", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                  }

                  Button(
                    onClick = {
                      if (vectorElements.isNotEmpty()) {
                        vectorElements = vectorElements.dropLast(1)
                      }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = CppSurfaceHighlight),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                  ) {
                    Text("- pop_back()", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                  }

                  OutlinedButton(
                    onClick = {
                      vectorElements = listOf(10, 20, 30)
                      vectorCapacity = 4
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(0.8f)
                  ) {
                    Text("Reset", fontSize = 11.sp, color = Color(0xFFCBD5E1))
                  }
                }
              }
            }

            // Map Visualizer
            Card(
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(14.dp),
              colors = CardDefaults.cardColors(containerColor = CppSurfaceCard),
              border = androidx.compose.foundation.BorderStroke(1.dp, CppBorderDark)
            ) {
              Column(modifier = Modifier.padding(14.dp)) {
                Text(
                  text = "std::map<std::string, int> (Red-Black Tree)",
                  style = MaterialTheme.typography.titleMedium,
                  fontWeight = FontWeight.Bold,
                  color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                  text = "Keys are always strictly ordered lexicographically in O(log n) time.",
                  fontSize = 12.sp,
                  color = Color(0xFF94A3B8)
                )

                Spacer(modifier = Modifier.height(10.dp))

                val sortedPairs = mapPairs.sortedBy { it.first }
                sortedPairs.forEach { (k, v) ->
                  Surface(
                    color = CodeBackground,
                    shape = RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CppBorderDark),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp)
                  ) {
                    Row(
                      modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                      horizontalArrangement = Arrangement.SpaceBetween,
                      verticalAlignment = Alignment.CenterVertically
                    ) {
                      Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                          imageVector = Icons.Default.Key,
                          contentDescription = null,
                          tint = CppAccentCyan,
                          modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                          text = "\"$k\"",
                          color = CodeString,
                          fontFamily = FontFamily.Monospace,
                          fontWeight = FontWeight.Bold,
                          fontSize = 13.sp
                        )
                      }
                      Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                          text = "->",
                          color = CodeOperator,
                          fontFamily = FontFamily.Monospace,
                          fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                          text = "$v",
                          color = CodeNumber,
                          fontFamily = FontFamily.Monospace,
                          fontWeight = FontWeight.Bold,
                          fontSize = 13.sp
                        )
                      }
                    }
                  }
                }
              }
            }
          }
        }

        1 -> {
          // OOP Lab: Interactive Class Builder & Hierarchy
          Column(
            modifier = Modifier
              .fillMaxSize()
              .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(14.dp)
          ) {
            Card(
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(14.dp),
              colors = CardDefaults.cardColors(containerColor = CppSurfaceCard),
              border = androidx.compose.foundation.BorderStroke(1.dp, CppBorderDark)
            ) {
              Column(modifier = Modifier.padding(14.dp)) {
                Text(
                  text = "Interactive C++ Class Builder",
                  style = MaterialTheme.typography.titleMedium,
                  fontWeight = FontWeight.Bold,
                  color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                  text = "Toggle member variables and methods to generate modern class declarations:",
                  fontSize = 12.sp,
                  color = Color(0xFF94A3B8)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                  FilterChip(
                    selected = hasNameMember,
                    onClick = { hasNameMember = !hasNameMember },
                    label = { Text("std::string name") }
                  )
                  FilterChip(
                    selected = hasGpaMember,
                    onClick = { hasGpaMember = !hasGpaMember },
                    label = { Text("double gpa") }
                  )
                  FilterChip(
                    selected = hasStudyMethod,
                    onClick = { hasStudyMethod = !hasStudyMethod },
                    label = { Text("void study()") }
                  )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Generated C++ Code
                val generatedClassCode = buildString {
                  append("class $className {\n")
                  append("private:\n")
                  if (hasNameMember) append("    std::string name;\n")
                  if (hasGpaMember) append("    double gpa;\n")
                  append("public:\n")
                  append("    $className() = default;\n")
                  if (hasStudyMethod) append("    void study() { /* execution logic */ }\n")
                  append("};")
                }

                Surface(
                  color = CodeBackground,
                  shape = RoundedCornerShape(8.dp),
                  border = androidx.compose.foundation.BorderStroke(1.dp, CppAccentCyan.copy(alpha = 0.5f)),
                  modifier = Modifier.fillMaxWidth()
                ) {
                  Text(
                    text = generatedClassCode,
                    color = Color(0xFFCBD5E1),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                    modifier = Modifier.padding(12.dp)
                  )
                }
              }
            }

            // Object Lifecycle diagram
            Card(
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(14.dp),
              colors = CardDefaults.cardColors(containerColor = CppSurfaceCard),
              border = androidx.compose.foundation.BorderStroke(1.dp, CppBorderDark)
            ) {
              Column(modifier = Modifier.padding(14.dp)) {
                Text(
                  text = "C++ Object Lifecycle Visualizer",
                  style = MaterialTheme.typography.titleMedium,
                  fontWeight = FontWeight.Bold,
                  color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))

                val lifecycleSteps = listOf(
                  "1. Memory Allocation" to "Stack slot or Heap malloc",
                  "2. Member Initializer List" to "Members initialized before body",
                  "3. Constructor Body" to "Constructor logic executes",
                  "4. Active Object State" to "Object methods invoked",
                  "5. Destructor Execution" to "RAII deterministic cleanup"
                )

                lifecycleSteps.forEachIndexed { i, (st, desc) ->
                  Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Box(
                      modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(if (i == 4) CppAccentRose.copy(alpha = 0.3f) else CppBlueDark),
                      contentAlignment = Alignment.Center
                    ) {
                      Text(
                        text = "${i + 1}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (i == 4) CppAccentRose else CppAccentCyan
                      )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                      Text(st, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 12.sp)
                      Text(desc, color = Color(0xFF94A3B8), fontSize = 11.sp)
                    }
                  }
                }
              }
            }
          }
        }

        2 -> {
          // Dedicated Memory Visualizer
          Column(
            modifier = Modifier
              .fillMaxSize()
              .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(14.dp)
          ) {
            MemoryVisualizerView()
            CompilerPipelineView()
          }
        }

        3 -> {
          // Sorting & Data Structures Visualizer
          Column(
            modifier = Modifier
              .fillMaxSize()
              .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(14.dp)
          ) {
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
                    text = "Bubble Sort Step-by-Step",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                  )
                  Surface(
                    color = CppAccentAmber.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(6.dp)
                  ) {
                    Text(
                      text = "Step $sortStep",
                      color = CppAccentAmber,
                      fontSize = 11.sp,
                      fontWeight = FontWeight.Bold,
                      modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                  }
                }
                Spacer(modifier = Modifier.height(10.dp))

                // Bar Visualization
                Row(
                  modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                  horizontalArrangement = Arrangement.SpaceEvenly,
                  verticalAlignment = Alignment.Bottom
                ) {
                  sortArray.forEachIndexed { i, valNum ->
                    Column(
                      horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                      Text(
                        text = "$valNum",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        color = Color.White
                      )
                      Spacer(modifier = Modifier.height(4.dp))
                      Box(
                        modifier = Modifier
                          .width(36.dp)
                          .height((valNum * 0.9).dp)
                          .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                          .background(
                            if (isSorted) CppAccentEmerald else CppBluePrimary
                          )
                      )
                    }
                  }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                  Button(
                    onClick = {
                      val mutable = sortArray.toMutableList()
                      // Perform one bubble swap pass
                      var swapped = false
                      for (i in 0 until mutable.size - 1) {
                        if (mutable[i] > mutable[i + 1]) {
                          val temp = mutable[i]
                          mutable[i] = mutable[i + 1]
                          mutable[i + 1] = temp
                          swapped = true
                          break
                        }
                      }
                      sortArray = mutable
                      sortStep++
                      if (!swapped) isSorted = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = CppBluePrimary),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                  ) {
                    Text("Execute Next Swap Step", fontSize = 11.sp)
                  }

                  OutlinedButton(
                    onClick = {
                      sortArray = listOf(64, 25, 12, 22, 11, 90)
                      sortStep = 0
                      isSorted = false
                    },
                    shape = RoundedCornerShape(8.dp)
                  ) {
                    Text("Reset", fontSize = 11.sp, color = Color(0xFFCBD5E1))
                  }
                }
              }
            }
          }
        }

        4 -> {
          // Big-O Analyzer Lab
          Column(
            modifier = Modifier
              .fillMaxSize()
              .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Text(
              text = "C++ Algorithm Complexity Analyzer",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = Color.White
            )

            CppContentRepository.bigOCurves.forEach { curve ->
              Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
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
                      text = "${curve.notation} — ${curve.name}",
                      fontWeight = FontWeight.Bold,
                      color = Color(curve.colorHex),
                      fontSize = 14.sp
                    )
                    Text(
                      text = curve.formula,
                      fontFamily = FontFamily.Monospace,
                      fontSize = 11.sp,
                      color = Color(0xFF94A3B8)
                    )
                  }
                  Spacer(modifier = Modifier.height(6.dp))
                  Text(
                    text = curve.description,
                    fontSize = 12.sp,
                    color = Color(0xFFE2E8F0)
                  )
                  Spacer(modifier = Modifier.height(4.dp))
                  Text(
                    text = "Examples: ${curve.exampleAlgorithms}",
                    fontSize = 11.sp,
                    color = Color(0xFF94A3B8)
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
