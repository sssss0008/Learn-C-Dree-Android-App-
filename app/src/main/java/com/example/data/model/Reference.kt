package com.example.data.model

data class CppReferenceItem(
  val id: String,
  val title: String,
  val category: String, // "Types", "Keywords", "STL Containers", "Algorithms", "Smart Pointers", "Modern C++"
  val syntax: String,
  val explanation: String,
  val codeExample: String,
  val commonMistakes: String,
  val bestPractice: String
)

data class CheatSheetSection(
  val title: String,
  val syntax: String,
  val note: String
)

data class CheatSheet(
  val id: String,
  val title: String,
  val description: String,
  val sections: List<CheatSheetSection>
)

data class GlossaryTerm(
  val term: String,
  val category: String,
  val definition: String,
  val example: String
)

data class CppErrorGuide(
  val errorName: String,
  val category: String, // "Compiler", "Linker", "Runtime", "Undefined Behavior"
  val typicalMessage: String,
  val rootCause: String,
  val diagnosisSteps: String,
  val fixCodeSnippet: String
)
