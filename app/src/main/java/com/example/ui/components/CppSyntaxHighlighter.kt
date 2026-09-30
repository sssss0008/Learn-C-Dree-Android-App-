package com.example.ui.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import com.example.ui.theme.*

object CppSyntaxHighlighter {

  private val KEYWORDS = setOf(
    "int", "void", "double", "float", "char", "bool", "auto", "class", "struct",
    "public", "private", "protected", "return", "if", "else", "while", "for",
    "switch", "case", "default", "break", "continue", "const", "constexpr",
    "virtual", "override", "template", "typename", "nullptr", "new", "delete",
    "namespace", "using", "concept", "requires", "explicit", "mutable", "true", "false",
    "static", "inline", "friend", "try", "catch", "throw", "sizeof"
  )

  private val PREPROCESSORS = setOf(
    "#include", "#define", "#pragma", "#ifdef", "#ifndef", "#endif", "#if"
  )

  private val STD_TYPES = setOf(
    "std::cout", "std::cin", "std::cerr", "std::endl", "std::vector",
    "std::string", "std::map", "std::unordered_map", "std::unique_ptr",
    "std::shared_ptr", "std::weak_ptr", "std::make_unique", "std::make_shared",
    "std::move", "std::swap", "std::accumulate", "std::sort", "std::optional",
    "std::nullopt", "std::pair", "std::set", "std::deque", "std::thread", "std::mutex",
    "std::lock_guard", "std::string_view"
  )

  fun highlight(code: String): AnnotatedString {
    return buildAnnotatedString {
      append(code)

      // Regex matching for various tokens
      // 1. Comments
      val commentRegex = Regex("//.*|/\\*[\\s\\S]*?\\*/")
      for (match in commentRegex.findAll(code)) {
        addStyle(SpanStyle(color = CodeComment, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic), match.range.first, match.range.last + 1)
      }

      // 2. Strings
      val stringRegex = Regex("\"(\\\\.|[^\"\\\\])*\"|'(\\\\.|[^'\\\\])*'")
      for (match in stringRegex.findAll(code)) {
        addStyle(SpanStyle(color = CodeString), match.range.first, match.range.last + 1)
      }

      // 3. Preprocessor directives
      val preprocRegex = Regex("#[a-zA-Z]+")
      for (match in preprocRegex.findAll(code)) {
        addStyle(SpanStyle(color = CodePreprocessor, fontWeight = FontWeight.Bold), match.range.first, match.range.last + 1)
      }

      // 4. Header includes like <iostream>
      val includeHeaderRegex = Regex("<[a-zA-Z0-9_.]+>")
      for (match in includeHeaderRegex.findAll(code)) {
        addStyle(SpanStyle(color = CodeString), match.range.first, match.range.last + 1)
      }

      // 5. Standard library symbols
      for (stdSymbol in STD_TYPES) {
        val symRegex = Regex(Regex.escape(stdSymbol))
        for (match in symRegex.findAll(code)) {
          addStyle(SpanStyle(color = CodeType, fontWeight = FontWeight.SemiBold), match.range.first, match.range.last + 1)
        }
      }

      // 6. Keywords
      val wordRegex = Regex("\\b[a-zA-Z_][a-zA-Z0-9_]*\\b")
      for (match in wordRegex.findAll(code)) {
        val word = match.value
        if (KEYWORDS.contains(word)) {
          addStyle(SpanStyle(color = CodeKeyword, fontWeight = FontWeight.Bold), match.range.first, match.range.last + 1)
        }
      }

      // 7. Numbers
      val numberRegex = Regex("\\b\\d+(\\.\\d+)?f?\\b")
      for (match in numberRegex.findAll(code)) {
        addStyle(SpanStyle(color = CodeNumber), match.range.first, match.range.last + 1)
      }
    }
  }
}
