package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.example.ui.components.CppCodeEditor
import com.example.ui.components.MemoryVisualizerView
import com.example.ui.theme.*

@Composable
fun CodePlaygroundScreen(
  modifier: Modifier = Modifier
) {
  var selectedSubMode by remember { mutableIntStateOf(0) } // 0: Editor & Run, 1: Debugger & Inspector, 2: Templates / Presets
  var debugStep by remember { mutableIntStateOf(1) }

  val codeTemplates = listOf(
    "Hello Modern C++" to """#include <iostream>
#include <vector>
#include <string>

int main() {
    std::cout << "Learn C++ by Awiskar Acharya" << std::endl;
    std::vector<std::string> tools = {"Compiler", "Debugger", "Memory Lab"};
    for (const auto& t : tools) {
        std::cout << "Ready: " << t << '\n';
    }
    return 0;
}""",
    "Smart Pointers (unique_ptr)" to """#include <iostream>
#include <memory>

class Sensor {
public:
    Sensor(int id) : id(id) { std::cout << "Sensor " << id << " ON\n"; }
    ~Sensor() { std::cout << "Sensor " << id << " OFF\n"; }
    void ping() { std::cout << "Sensor " << id << " reading: 98.6\n"; }
private:
    int id;
};

int main() {
    auto sensor = std::make_unique<Sensor>(404);
    sensor->ping();
    return 0; // Automatically deleted!
}""",
    "Operator Overloading" to """#include <iostream>

class Complex {
public:
    double r, i;
    Complex(double r, double i) : r(r), i(i) {}
    Complex operator+(const Complex& other) const {
        return Complex(r + other.r, i + other.i);
    }
};

int main() {
    Complex c1(3.0, 4.0);
    Complex c2(1.5, 2.5);
    Complex sum = c1 + c2;
    std::cout << "Sum: " << sum.r << " + " << sum.i << "i\n";
    return 0;
}"""
  )

  var activeCode by remember { mutableStateOf(codeTemplates[0].second) }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(CppBackgroundDark)
  ) {
    // Mode Switcher Header
    Surface(
      color = CppSurfaceDark,
      border = androidx.compose.foundation.BorderStroke(1.dp, CppBorderDark)
    ) {
      Column(modifier = Modifier.padding(12.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Terminal,
              contentDescription = null,
              tint = CppBlueLight,
              modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "C++ IDE & DEBUGGER",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = Color.White
            )
          }

          Surface(
            color = CppAccentEmerald.copy(alpha = 0.15f),
            shape = RoundedCornerShape(6.dp)
          ) {
            Text(
              text = "C++20 GCC",
              color = CppAccentEmerald,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Tabs
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(CppSurfaceCard)
            .padding(3.dp)
        ) {
          listOf("Editor & Runner", "Debugger & Variables", "Sample Snippets").forEachIndexed { idx, title ->
            val isSel = selectedSubMode == idx
            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(6.dp))
                .background(if (isSel) CppBluePrimary else Color.Transparent)
                .clickable { selectedSubMode = idx }
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
        .padding(14.dp)
    ) {
      when (selectedSubMode) {
        0 -> {
          // Standard IDE Editor & Runner
          Column(
            modifier = Modifier
              .fillMaxSize()
              .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
          ) {
            CppCodeEditor(
              initialCode = activeCode,
              onCodeChange = { activeCode = it }
            )
          }
        }

        1 -> {
          // Debugger Mode
          Column(
            modifier = Modifier
              .fillMaxSize()
              .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
          ) {
            // Debugger Toolbar
            Surface(
              color = CppSurfaceCard,
              shape = RoundedCornerShape(12.dp),
              border = androidx.compose.foundation.BorderStroke(1.dp, CppBorderDark)
            ) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Box(
                    modifier = Modifier
                      .size(10.dp)
                      .clip(CircleShape)
                      .background(CppAccentRose)
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(
                    text = "Breakpoint at line $debugStep",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                  )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                  Button(
                    onClick = { if (debugStep < 5) debugStep++ else debugStep = 1 },
                    colors = ButtonDefaults.buttonColors(containerColor = CppBluePrimary),
                    shape = RoundedCornerShape(6.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                  ) {
                    Icon(
                      imageVector = Icons.Default.PlayArrow,
                      contentDescription = "Step",
                      modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Step Over", fontSize = 11.sp)
                  }

                  OutlinedButton(
                    onClick = { debugStep = 1 },
                    shape = RoundedCornerShape(6.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                  ) {
                    Text("Restart", fontSize = 11.sp, color = Color(0xFFCBD5E1))
                  }
                }
              }
            }

            // Variable Inspector Table
            Surface(
              color = CppSurfaceCard,
              shape = RoundedCornerShape(12.dp),
              border = androidx.compose.foundation.BorderStroke(1.dp, CppBorderDark)
            ) {
              Column(modifier = Modifier.padding(12.dp)) {
                Text(
                  text = "VARIABLE INSPECTOR (WATCH WINDOW)",
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = CppAccentCyan
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Table Header
                Row(
                  modifier = Modifier
                    .fillMaxWidth()
                    .background(CppSurfaceDark)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                  Text("Name", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF94A3B8), modifier = Modifier.weight(1f))
                  Text("Type", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF94A3B8), modifier = Modifier.weight(1f))
                  Text("Value", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF94A3B8), modifier = Modifier.weight(1f))
                  Text("Address", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF94A3B8), modifier = Modifier.weight(1.2f))
                }

                // Table Rows
                val debugVars = listOf(
                  Quad("count", "int", "${debugStep * 10}", "0x7ffd9a00"),
                  Quad("name", "std::string", "\"Awiskar\"", "0x7ffd9a08"),
                  Quad("ptr", "int*", "0x00f810", "0x7ffd9a20"),
                  Quad("isReady", "bool", "true", "0x7ffd9a28")
                )

                debugVars.forEachIndexed { i, (name, type, valStr, addr) ->
                  Row(
                    modifier = Modifier
                      .fillMaxWidth()
                      .background(if (i % 2 == 0) Color.Transparent else CppSurfaceDark.copy(alpha = 0.5f))
                      .padding(horizontal = 8.dp, vertical = 6.dp)
                  ) {
                    Text(name, fontSize = 11.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.weight(1f))
                    Text(type, fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = CodeKeyword, modifier = Modifier.weight(1f))
                    Text(valStr, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = CodeNumber, modifier = Modifier.weight(1f))
                    Text(addr, fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = Color(0xFF64748B), modifier = Modifier.weight(1.2f))
                  }
                }
              }
            }

            // Live Memory View in Debugger
            MemoryVisualizerView()
          }
        }

        2 -> {
          // Preset Templates
          Column(
            modifier = Modifier
              .fillMaxSize()
              .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Text(
              text = "Modern C++ Code Templates",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = Color.White
            )
            Text(
              text = "Tap any template to load it into the interactive IDE and execute:",
              fontSize = 12.sp,
              color = Color(0xFF94A3B8)
            )

            codeTemplates.forEach { (title, code) ->
              Card(
                modifier = Modifier
                  .fillMaxWidth()
                  .clickable {
                    activeCode = code
                    selectedSubMode = 0
                  },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = CppSurfaceCard),
                border = androidx.compose.foundation.BorderStroke(1.dp, CppBorderDark)
              ) {
                Column(modifier = Modifier.padding(14.dp)) {
                  Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                  ) {
                    Text(
                      text = title,
                      fontWeight = FontWeight.Bold,
                      color = Color.White,
                      fontSize = 13.sp
                    )
                    Icon(
                      imageVector = Icons.Default.ArrowForward,
                      contentDescription = null,
                      tint = CppBlueLight,
                      modifier = Modifier.size(16.dp)
                    )
                  }
                  Spacer(modifier = Modifier.height(6.dp))
                  Surface(
                    color = CodeBackground,
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.fillMaxWidth()
                  ) {
                    Text(
                      text = code.lines().take(4).joinToString("\n") + "\n...",
                      fontFamily = FontFamily.Monospace,
                      fontSize = 10.sp,
                      color = Color(0xFF94A3B8),
                      modifier = Modifier.padding(8.dp)
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

data class Quad<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)
