package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun MemoryVisualizerView(
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableStateOf(0) } // 0: Stack & Heap, 1: Pointer Dereference, 2: Smart Pointer RefCount
  var pointerMutated by remember { mutableStateOf(false) }
  var sharedRefCount by remember { mutableIntStateOf(2) }

  Card(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = CppSurfaceDark),
    border = androidx.compose.foundation.BorderStroke(1.dp, CppBorderDark)
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Layers,
            contentDescription = null,
            tint = CppAccentCyan,
            modifier = Modifier.size(20.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "C++ MEMORY VISUALIZER",
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
            text = "Interactive",
            color = CppBlueLight,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Tab selector
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(8.dp))
          .background(CppSurfaceCard)
          .padding(3.dp)
      ) {
        val tabTitles = listOf("Stack vs Heap", "Pointer & Target", "Smart Pointers")
        tabTitles.forEachIndexed { index, title ->
          val isSel = selectedTab == index
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(6.dp))
              .background(if (isSel) CppBluePrimary else Color.Transparent)
              .clickable { selectedTab = index }
              .padding(vertical = 6.dp),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = title,
              fontSize = 11.sp,
              fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
              color = if (isSel) Color.White else Color(0xFF94A3B8)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      when (selectedTab) {
        0 -> {
          // Stack vs Heap visualization
          Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            // Stack Segment
            Surface(
              color = CppSurfaceCard,
              shape = RoundedCornerShape(12.dp),
              border = androidx.compose.foundation.BorderStroke(1.dp, CppAccentCyan.copy(alpha = 0.4f)),
              modifier = Modifier.fillMaxWidth()
            ) {
              Column(modifier = Modifier.padding(12.dp)) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.SpaceBetween,
                  modifier = Modifier.fillMaxWidth()
                ) {
                  Text(
                    text = "STACK FRAME [main()] (Fast LIFO)",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = CppAccentCyan
                  )
                  Text(
                    text = "High Memory -> Decreasing",
                    fontSize = 10.sp,
                    color = Color(0xFF64748B)
                  )
                }
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                  MemoryBlockView(
                    name = "score",
                    type = "int",
                    value = "100",
                    address = "0x7ffd9a2c",
                    modifier = Modifier.weight(1f)
                  )
                  MemoryBlockView(
                    name = "ptr",
                    type = "int*",
                    value = "0x00a4f810",
                    address = "0x7ffd9a20",
                    highlight = true,
                    modifier = Modifier.weight(1f)
                  )
                }
              }
            }

            // Arrow down to Heap
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.Center,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.ArrowDownward,
                contentDescription = null,
                tint = CppAccentAmber,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "ptr stores address of dynamic heap object",
                fontSize = 11.sp,
                color = CppAccentAmber
              )
            }

            // Heap Segment
            Surface(
              color = CppSurfaceCard,
              shape = RoundedCornerShape(12.dp),
              border = androidx.compose.foundation.BorderStroke(1.dp, CppAccentAmber.copy(alpha = 0.4f)),
              modifier = Modifier.fillMaxWidth()
            ) {
              Column(modifier = Modifier.padding(12.dp)) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.SpaceBetween,
                  modifier = Modifier.fillMaxWidth()
                ) {
                  Text(
                    text = "HEAP SEGMENT (Dynamic, Manual Lifetime)",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = CppAccentAmber
                  )
                  Text(
                    text = "Allocated with 'new'",
                    fontSize = 10.sp,
                    color = Color(0xFF64748B)
                  )
                }
                Spacer(modifier = Modifier.height(8.dp))
                MemoryBlockView(
                  name = "*ptr (new int)",
                  type = "int",
                  value = "999",
                  address = "0x00a4f810",
                  highlight = false,
                  borderColor = CppAccentAmber,
                  modifier = Modifier.fillMaxWidth()
                )
              }
            }
          }
        }

        1 -> {
          // Pointer & Target Dereference Lab
          Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
              text = "Tap button below to mutate target through '*ptr = 88'",
              fontSize = 12.sp,
              color = Color(0xFFCBD5E1)
            )

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(12.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              // Pointer Variable Box
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = "POINTER VARIABLE",
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  color = CppBlueLight
                )
                Spacer(modifier = Modifier.height(4.dp))
                MemoryBlockView(
                  name = "ptr",
                  type = "int*",
                  value = "0x7ffd100",
                  address = "0x7ffd108",
                  highlight = true
                )
              }

              Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Points to",
                tint = CppAccentEmerald,
                modifier = Modifier.size(24.dp)
              )

              // Target Variable Box
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = "TARGET VARIABLE",
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  color = CppAccentEmerald
                )
                Spacer(modifier = Modifier.height(4.dp))
                MemoryBlockView(
                  name = "target",
                  type = "int",
                  value = if (pointerMutated) "88" else "42",
                  address = "0x7ffd100",
                  borderColor = if (pointerMutated) CppAccentEmerald else CppBorderDark
                )
              }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Button(
              onClick = { pointerMutated = !pointerMutated },
              colors = ButtonDefaults.buttonColors(
                containerColor = if (pointerMutated) CppAccentRose else CppAccentEmerald
              ),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.fillMaxWidth()
            ) {
              Text(
                text = if (pointerMutated) "Reset: *ptr = 42" else "Execute: *ptr = 88",
                fontWeight = FontWeight.Bold,
                color = Color.White
              )
            }
          }
        }

        2 -> {
          // Smart Pointer Ref Count
          Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
              text = "std::shared_ptr maintains an atomic reference count. When count drops to 0, memory is automatically freed.",
              fontSize = 12.sp,
              color = Color(0xFFCBD5E1)
            )

            Surface(
              color = CodeBackground,
              shape = RoundedCornerShape(10.dp),
              border = androidx.compose.foundation.BorderStroke(1.dp, CppAccentPurple.copy(alpha = 0.5f)),
              modifier = Modifier.fillMaxWidth()
            ) {
              Column(modifier = Modifier.padding(12.dp)) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.SpaceBetween,
                  modifier = Modifier.fillMaxWidth()
                ) {
                  Text(
                    text = "Control Block & Managed Object",
                    color = CppAccentPurple,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                  )
                  Surface(
                    color = CppAccentPurple.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(6.dp)
                  ) {
                    Text(
                      text = "ref_count: $sharedRefCount",
                      color = Color.White,
                      fontFamily = FontFamily.Monospace,
                      fontWeight = FontWeight.Bold,
                      fontSize = 11.sp,
                      modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                  }
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                  text = "Object: Widget{id: 42} at Heap Address 0x00f89b",
                  fontFamily = FontFamily.Monospace,
                  fontSize = 11.sp,
                  color = Color(0xFF94A3B8)
                )
              }
            }

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Button(
                onClick = { if (sharedRefCount < 4) sharedRefCount++ },
                colors = ButtonDefaults.buttonColors(containerColor = CppBluePrimary),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f)
              ) {
                Text("+ Copy ptr (Add Owner)", fontSize = 11.sp)
              }

              Button(
                onClick = { if (sharedRefCount > 0) sharedRefCount-- },
                colors = ButtonDefaults.buttonColors(containerColor = CppSurfaceHighlight),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f)
              ) {
                Text("- Scope Exit (Release)", fontSize = 11.sp)
              }
            }

            if (sharedRefCount == 0) {
              Surface(
                color = CppAccentRose.copy(alpha = 0.15f),
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CppAccentRose)
              ) {
                Text(
                  text = "[~Widget Destructor Executed] Memory cleanly freed without leaks!",
                  color = CppAccentRose,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold,
                  modifier = Modifier.padding(10.dp)
                )
              }
            }
          }
        }
      }
    }
  }
}

@Composable
fun MemoryBlockView(
  name: String,
  type: String,
  value: String,
  address: String,
  highlight: Boolean = false,
  borderColor: Color = CppBorderDark,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(8.dp))
      .background(if (highlight) CppBlueDark.copy(alpha = 0.4f) else CodeBackground)
      .border(1.dp, borderColor, RoundedCornerShape(8.dp))
      .padding(8.dp)
  ) {
    Column {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(
          text = name,
          fontWeight = FontWeight.Bold,
          color = Color.White,
          fontSize = 12.sp
        )
        Text(
          text = type,
          color = CodeKeyword,
          fontFamily = FontFamily.Monospace,
          fontSize = 10.sp
        )
      }
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = "Val: $value",
        color = CodeNumber,
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold,
        fontSize = 13.sp
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = "@ $address",
        color = Color(0xFF64748B),
        fontFamily = FontFamily.Monospace,
        fontSize = 10.sp
      )
    }
  }
}
