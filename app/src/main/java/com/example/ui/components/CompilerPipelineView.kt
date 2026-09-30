package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

data class PipelineStage(
  val id: Int,
  val name: String,
  val outputArtifact: String,
  val icon: ImageVector,
  val summary: String,
  val technicalDetails: String,
  val commandEquivalent: String
)

@Composable
fun CompilerPipelineView(
  modifier: Modifier = Modifier,
  initialActiveStage: Int = 1
) {
  var selectedStageId by remember { mutableStateOf(initialActiveStage) }

  val stages = remember {
    listOf(
      PipelineStage(
        id = 1,
        name = "1. C++ Source",
        outputArtifact = "main.cpp",
        icon = Icons.Default.Description,
        summary = "Human-readable C++ code with directives, types, and logic.",
        technicalDetails = "Raw ASCII/UTF-8 source text. Contains #include directives, templates, classes, and namespace definitions.",
        commandEquivalent = "cat main.cpp"
      ),
      PipelineStage(
        id = 2,
        name = "2. Preprocessor",
        outputArtifact = "main.i",
        icon = Icons.Default.Transform,
        summary = "Expands macros and copies full included headers into one file.",
        technicalDetails = "Replaces #include <iostream> with thousands of lines of actual header content. Strips all comments and evaluates conditional macros (#ifdef, #if).",
        commandEquivalent = "g++ -E main.cpp -o main.i"
      ),
      PipelineStage(
        id = 3,
        name = "3. Compiler",
        outputArtifact = "main.s",
        icon = Icons.Default.Architecture,
        summary = "Parses AST, performs optimizations, and produces Assembly.",
        technicalDetails = "Performs lexical analysis, syntactic parsing (AST), semantic analysis (type checking), template instantiations, and register allocation. Emits CPU assembly code.",
        commandEquivalent = "g++ -S main.i -o main.s"
      ),
      PipelineStage(
        id = 4,
        name = "4. Assembler",
        outputArtifact = "main.o",
        icon = Icons.Default.Memory,
        summary = "Translates assembly into raw binary machine instructions.",
        technicalDetails = "Generates Relocatable Object File (ELF on Linux, Mach-O on macOS, PE on Windows). Code is now binary opcodes with unresolved external symbols.",
        commandEquivalent = "as main.s -o main.o"
      ),
      PipelineStage(
        id = 5,
        name = "5. Linker",
        outputArtifact = "a.out / prog.exe",
        icon = Icons.Default.AccountTree,
        summary = "Resolves symbols, combines libraries into executable binary.",
        technicalDetails = "Connects function references (e.g. std::cout) to C++ runtime library (libstdc++/libc++). Calculates relative memory jumps and produces the executable.",
        commandEquivalent = "g++ main.o -o my_app"
      ),
      PipelineStage(
        id = 6,
        name = "6. CPU Execution",
        outputArtifact = "Runtime RAM",
        icon = Icons.Default.PlayArrow,
        summary = "OS loader maps binary segments into memory; CPU executes instructions.",
        technicalDetails = "OS maps .text (code), .rodata (constants), .data/.bss (globals) into virtual memory space. Sets up Stack and Heap. Instruction Pointer jumps to main().",
        commandEquivalent = "./my_app"
      )
    )
  }

  val activeStage = stages.firstOrNull { it.id == selectedStageId } ?: stages.first()

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
            imageVector = Icons.Default.Build,
            contentDescription = null,
            tint = CppBlueLight,
            modifier = Modifier.size(20.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "C++ BUILD PIPELINE",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White
          )
        }
        Text(
          text = "Tap stage to inspect",
          fontSize = 11.sp,
          color = Color(0xFF94A3B8)
        )
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Horizontal flow of stages
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically
      ) {
        stages.forEachIndexed { index, stage ->
          val isSelected = stage.id == selectedStageId
          val bgColor by animateColorAsState(
            if (isSelected) CppBluePrimary else CppSurfaceCard,
            label = "stageBg"
          )
          val borderColor by animateColorAsState(
            if (isSelected) CppAccentCyan else CppBorderDark,
            label = "stageBorder"
          )

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(12.dp))
              .background(bgColor)
              .border(1.dp, borderColor, RoundedCornerShape(12.dp))
              .clickable { selectedStageId = stage.id }
              .padding(horizontal = 12.dp, vertical = 8.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = stage.icon,
                contentDescription = null,
                tint = if (isSelected) Color.White else CppBlueLight,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Column {
                Text(
                  text = stage.name,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold,
                  color = if (isSelected) Color.White else Color(0xFFE2E8F0)
                )
                Text(
                  text = stage.outputArtifact,
                  fontFamily = FontFamily.Monospace,
                  fontSize = 10.sp,
                  color = if (isSelected) Color(0xFFE0F2FE) else Color(0xFF94A3B8)
                )
              }
            }
          }

          if (index < stages.size - 1) {
            Icon(
              imageVector = Icons.Default.ArrowForward,
              contentDescription = "Arrow",
              tint = Color(0xFF64748B),
              modifier = Modifier
                .padding(horizontal = 6.dp)
                .size(16.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Active Stage Detail Box
      Surface(
        color = CppSurfaceCard,
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CppBorderDark.copy(alpha = 0.8f))
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
          ) {
            Box(
              modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(CppBluePrimary.copy(alpha = 0.2f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = activeStage.icon,
                contentDescription = null,
                tint = CppAccentCyan,
                modifier = Modifier.size(18.dp)
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = activeStage.name + " -> " + activeStage.outputArtifact,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 14.sp
              )
              Text(
                text = activeStage.summary,
                color = Color(0xFFCBD5E1),
                fontSize = 12.sp
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))
          Text(
            text = activeStage.technicalDetails,
            color = Color(0xFF94A3B8),
            fontSize = 12.sp,
            lineHeight = 17.sp
          )

          Spacer(modifier = Modifier.height(10.dp))
          Surface(
            color = CodeBackground,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "$ ",
                color = CppAccentEmerald,
                fontFamily = FontFamily.Monospace,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = activeStage.commandEquivalent,
                color = Color.White,
                fontFamily = FontFamily.Monospace,
                fontSize = 11.sp
              )
            }
          }
        }
      }
    }
  }
}
