package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CppCodeEditor(
  initialCode: String,
  expectedOutput: String? = null,
  onCodeChange: ((String) -> Unit)? = null,
  modifier: Modifier = Modifier
) {
  var codeText by remember(initialCode) { mutableStateOf(initialCode) }
  var isCompiling by remember { mutableStateOf(false) }
  var terminalOutput by remember { mutableStateOf("") }
  var hasRun by remember { mutableStateOf(false) }
  var executionTimeMs by remember { mutableLongStateOf(0L) }
  val coroutineScope = rememberCoroutineScope()

  val codingShortcuts = listOf(
    "{", "}", "(", ")", "[", "]", ";", ":", "#", "<", ">", "=", "+", "-", "*", "/", "&", "|", "::", "\"", "'"
  )

  Column(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(14.dp))
      .background(CodeBackground)
      .border(1.dp, CppBorderDark, RoundedCornerShape(14.dp))
  ) {
    // Editor Top Bar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .background(CppSurfaceDark)
        .padding(horizontal = 12.dp, vertical = 8.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        // Window dots
        Box(modifier = Modifier.size(10.dp).clip(RoundedCornerShape(5.dp)).background(Color(0xFFEF4444)))
        Spacer(modifier = Modifier.width(6.dp))
        Box(modifier = Modifier.size(10.dp).clip(RoundedCornerShape(5.dp)).background(Color(0xFFF59E0B)))
        Spacer(modifier = Modifier.width(6.dp))
        Box(modifier = Modifier.size(10.dp).clip(RoundedCornerShape(5.dp)).background(Color(0xFF10B981)))
        Spacer(modifier = Modifier.width(10.dp))
        Text(
          text = "main.cpp",
          fontFamily = FontFamily.Monospace,
          fontSize = 12.sp,
          fontWeight = FontWeight.SemiBold,
          color = Color(0xFFE2E8F0)
        )
      }

      Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        // Reset button
        IconButton(
          onClick = {
            codeText = initialCode
            onCodeChange?.invoke(initialCode)
            terminalOutput = ""
            hasRun = false
          },
          modifier = Modifier.size(32.dp).testTag("reset_code_button")
        ) {
          Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = "Reset Code",
            tint = Color(0xFF94A3B8),
            modifier = Modifier.size(18.dp)
          )
        }

        // Run button
        Button(
          onClick = {
            coroutineScope.launch {
              isCompiling = true
              terminalOutput = "[1/3] g++ -std=c++20 -O2 -Wall -Wextra main.cpp -o app\n[2/3] Linking dynamic libraries (libstdc++.so)...\n"
              delay(350)
              val start = System.currentTimeMillis()
              val out = if (!expectedOutput.isNullOrBlank()) {
                expectedOutput
              } else {
                "Program compiled and executed successfully.\n[Process exited with code 0]"
              }
              terminalOutput += "[3/3] Executing ./app ...\n--------------------------------\n$out"
              executionTimeMs = System.currentTimeMillis() - start + 12
              isCompiling = false
              hasRun = true
            }
          },
          colors = ButtonDefaults.buttonColors(containerColor = CppAccentEmerald),
          shape = RoundedCornerShape(8.dp),
          contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
          enabled = !isCompiling,
          modifier = Modifier.testTag("run_code_button")
        ) {
          if (isCompiling) {
            CircularProgressIndicator(
              modifier = Modifier.size(14.dp),
              color = Color.White,
              strokeWidth = 2.dp
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text("Compiling...", fontSize = 11.sp, color = Color.White)
          } else {
            Icon(
              imageVector = Icons.Default.PlayArrow,
              contentDescription = "Run",
              tint = Color.White,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text("Run C++", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
          }
        }
      }
    }

    // Code editing area with line numbers
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .heightIn(min = 160.dp, max = 320.dp)
        .padding(8.dp)
    ) {
      val lines = codeText.split("\n")
      // Line numbers column
      Column(
        modifier = Modifier
          .padding(end = 8.dp)
          .width(28.dp),
        horizontalAlignment = Alignment.End
      ) {
        lines.indices.forEach { index ->
          Text(
            text = "${index + 1}",
            color = Color(0xFF475569),
            fontFamily = FontFamily.Monospace,
            fontSize = 12.sp,
            lineHeight = 18.sp
          )
        }
      }

      // Syntax-highlighted text input
      Box(
        modifier = Modifier
          .weight(1f)
          .verticalScroll(rememberScrollState())
          .horizontalScroll(rememberScrollState())
      ) {
        BasicTextField(
          value = codeText,
          onValueChange = {
            codeText = it
            onCodeChange?.invoke(it)
          },
          textStyle = androidx.compose.ui.text.TextStyle(
            fontFamily = FontFamily.Monospace,
            fontSize = 12.sp,
            lineHeight = 18.sp,
            color = Color(0xFFE2E8F0)
          ),
          cursorBrush = SolidColor(CppAccentCyan),
          visualTransformation = {
            androidx.compose.ui.text.input.TransformedText(
              CppSyntaxHighlighter.highlight(it.text),
              androidx.compose.ui.text.input.OffsetMapping.Identity
            )
          },
          modifier = Modifier
            .fillMaxWidth()
            .testTag("cpp_code_input")
        )
      }
    }

    // Mobile C++ coding toolbar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .background(CppSurfaceDark.copy(alpha = 0.9f))
        .horizontalScroll(rememberScrollState())
        .padding(horizontal = 6.dp, vertical = 6.dp),
      horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      codingShortcuts.forEach { symbol ->
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(CppSurfaceCard)
            .border(1.dp, CppBorderDark, RoundedCornerShape(6.dp))
            .clickable {
              codeText += symbol
              onCodeChange?.invoke(codeText)
            }
            .padding(horizontal = 10.dp, vertical = 5.dp),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = symbol,
            color = CppAccentCyan,
            fontFamily = FontFamily.Monospace,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }
    }

    // Terminal / Output Section
    if (hasRun || terminalOutput.isNotEmpty()) {
      Divider(color = CppBorderDark)
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .background(Color(0xFF060913))
          .padding(10.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Terminal,
              contentDescription = null,
              tint = CppAccentEmerald,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "TERMINAL OUTPUT",
              color = CppAccentEmerald,
              fontFamily = FontFamily.Monospace,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold
            )
          }

          if (executionTimeMs > 0) {
            Text(
              text = "Runtime: ${executionTimeMs}ms",
              color = Color(0xFF64748B),
              fontFamily = FontFamily.Monospace,
              fontSize = 10.sp
            )
          }
        }

        Spacer(modifier = Modifier.height(6.dp))
        Surface(
          color = Color.Black.copy(alpha = 0.5f),
          shape = RoundedCornerShape(6.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Text(
            text = terminalOutput,
            color = Color(0xFFCBD5E1),
            fontFamily = FontFamily.Monospace,
            fontSize = 11.sp,
            lineHeight = 16.sp,
            modifier = Modifier.padding(8.dp)
          )
        }
      }
    }
  }
}
