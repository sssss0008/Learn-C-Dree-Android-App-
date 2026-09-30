package com.example.data.model

data class CppLevel(
  val id: Int,
  val title: String,
  val subtitle: String,
  val tag: String,
  val icon: String,
  val modules: List<CppModule>
)

data class CppModule(
  val id: String,
  val title: String,
  val description: String,
  val lessons: List<CppLesson>
)

enum class VisualizerType {
  NONE,
  COMPILER_PIPELINE,
  VARIABLE_MEMORY,
  CONDITION_BRANCH,
  LOOP_ANIMATION,
  FUNCTION_STACK,
  ARRAY_MEMORY,
  STRING_BUFFER,
  POINTER_BOXES,
  POINTER_VS_REFERENCE,
  STACK_VS_HEAP,
  CLASS_OBJECT,
  CONSTRUCTOR_LIFECYCLE,
  INHERITANCE_TREE,
  POLYMORPHISM_DISPATCH,
  TEMPLATE_INSTANTIATION,
  VECTOR_BUFFER,
  MAP_TREE,
  SMART_POINTER_OWNERSHIP,
  RAII_RESOURCE_CYCLE,
  MOVE_SEMANTICS_TRANSFER,
  EXCEPTION_FLOW,
  MULTITHREADING_CHANNELS,
  BIG_O_CURVES
}

data class QuizQuestion(
  val id: String,
  val question: String,
  val options: List<String>,
  val correctOptionIndex: Int,
  val explanation: String
)

data class CppLesson(
  val id: String,
  val levelId: Int,
  val moduleId: String,
  val title: String,
  val durationMinutes: Int,
  val overview: String,
  val visualizerType: VisualizerType = VisualizerType.NONE,
  val explanation: String,
  val deepDive: String = "",
  val codeExample: String,
  val expectedOutput: String,
  val quiz: List<QuizQuestion> = emptyList()
)
