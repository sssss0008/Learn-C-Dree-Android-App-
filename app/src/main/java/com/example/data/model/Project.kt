package com.example.data.model

data class ProjectFile(
  val name: String,
  val path: String,
  val content: String,
  val isHeader: Boolean = false
)

data class CppProject(
  val id: String,
  val title: String,
  val level: String, // "Beginner", "Intermediate", "Advanced"
  val category: String,
  val description: String,
  val conceptsUsed: List<String>,
  val files: List<ProjectFile>,
  val expectedOutput: String
)
