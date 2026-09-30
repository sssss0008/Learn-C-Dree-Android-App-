package com.example.data.model

data class TestCase(
  val input: String,
  val expectedOutput: String,
  val isHidden: Boolean = false
)

data class CodingChallenge(
  val id: String,
  val title: String,
  val difficulty: String, // "Easy", "Medium", "Hard"
  val category: String,   // "Arrays", "Pointers", "STL", "OOP", "Strings", "Algorithms"
  val description: String,
  val starterCode: String,
  val solutionCode: String,
  val testCases: List<TestCase>,
  val conceptHint: String,
  val logicHint: String,
  val strongHint: String,
  val fullExplanation: String,
  val timeComplexity: String = "O(n)",
  val spaceComplexity: String = "O(1)"
)

data class OutputPredictionQuiz(
  val id: String,
  val title: String,
  val category: String,
  val code: String,
  val options: List<String>,
  val correctIndex: Int,
  val explanation: String,
  val pitfall: String
)

data class DebuggingChallenge(
  val id: String,
  val title: String,
  val category: String, // "Dangling Pointer", "Memory Leak", "Iterator Invalidation", "Object Slicing", "Undefined Behavior"
  val buggyCode: String,
  val errorDescription: String,
  val fixedCode: String,
  val solutionWalkthrough: String
)

data class InterviewTopic(
  val id: String,
  val title: String,
  val questionCount: Int,
  val questions: List<InterviewQuestion>
)

data class InterviewQuestion(
  val id: String,
  val question: String,
  val difficulty: String,
  val answer: String,
  val codeExample: String = ""
)
