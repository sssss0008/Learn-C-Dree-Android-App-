package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.NoteEntity
import com.example.data.local.ProgressEntity
import com.example.data.model.*
import com.example.data.repository.CppContentRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class MainTab {
  HOME,
  LEARN,
  CODE,
  LABS,
  PRACTICE,
  MORE
}

data class ChatMessage(
  val id: String,
  val sender: String, // "user" or "tutor"
  val text: String,
  val codeSnippet: String? = null,
  val timestamp: Long = System.currentTimeMillis()
)

data class AppUiState(
  val isOnboarded: Boolean = true, // Default to ready, user can re-open onboarding or customize
  val userName: String = "Awiskar",
  val experienceLevel: String = "Intermediate",
  val learningGoals: Set<String> = setOf("Data Structures", "Algorithms", "Competitive Programming", "System Programming"),
  val streakDays: Int = 7,
  val longestStreak: Int = 14,
  val completedLessonIds: Set<String> = setOf("les_01_01", "les_01_02", "les_02_01", "les_04_01"),
  val solvedChallengeIds: Set<String> = setOf("chal_01"),
  val bookmarkedIds: Set<String> = setOf("les_04_01", "ref_vector"),
  val selectedTab: MainTab = MainTab.HOME,
  val selectedLesson: CppLesson? = null,
  val selectedChallenge: CodingChallenge? = null,
  val selectedProject: CppProject? = null,
  val isCertificateVisible: Boolean = false,
  val notes: List<NoteEntity> = emptyList(),
  val tutorChat: List<ChatMessage> = listOf(
    ChatMessage(
      id = "m1",
      sender = "tutor",
      text = "Hello! I am your C++ AI Mentor. Ask me anything about pointers, memory layouts, virtual tables, move semantics, templates, or debugging your C++ code!"
    )
  )
)

class AppViewModel(application: Application) : AndroidViewModel(application) {

  private val database = AppDatabase.getDatabase(application)
  private val noteDao = database.noteDao()
  private val progressDao = database.progressDao()

  private val _uiState = MutableStateFlow(AppUiState())
  val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

  init {
    loadPersistedData()
  }

  private fun loadPersistedData() {
    viewModelScope.launch {
      // Observe notes from Room DB
      noteDao.getAllNotes().collect { notesList ->
        _uiState.update { it.copy(notes = notesList) }
      }
    }
    viewModelScope.launch {
      val savedName = progressDao.getValue("user_name")
      val savedOnboarded = progressDao.getValue("is_onboarded")
      if (savedName != null) {
        _uiState.update { it.copy(userName = savedName) }
      }
      if (savedOnboarded != null) {
        _uiState.update { it.copy(isOnboarded = savedOnboarded.toBoolean()) }
      }
    }
  }

  fun setTab(tab: MainTab) {
    _uiState.update { it.copy(selectedTab = tab, selectedLesson = null, selectedChallenge = null, selectedProject = null) }
  }

  fun selectLesson(lesson: CppLesson?) {
    _uiState.update { it.copy(selectedLesson = lesson) }
  }

  fun selectChallenge(challenge: CodingChallenge?) {
    _uiState.update { it.copy(selectedChallenge = challenge) }
  }

  fun selectProject(project: CppProject?) {
    _uiState.update { it.copy(selectedProject = project) }
  }

  fun completeLesson(lessonId: String) {
    _uiState.update { current ->
      current.copy(completedLessonIds = current.completedLessonIds + lessonId)
    }
    viewModelScope.launch {
      progressDao.setProgress(ProgressEntity("completed_lessons", _uiState.value.completedLessonIds.joinToString(",")))
    }
  }

  fun solveChallenge(challengeId: String) {
    _uiState.update { current ->
      current.copy(solvedChallengeIds = current.solvedChallengeIds + challengeId)
    }
    viewModelScope.launch {
      progressDao.setProgress(ProgressEntity("solved_challenges", _uiState.value.solvedChallengeIds.joinToString(",")))
    }
  }

  fun toggleBookmark(id: String) {
    _uiState.update { current ->
      val newBookmarks = if (current.bookmarkedIds.contains(id)) {
        current.bookmarkedIds - id
      } else {
        current.bookmarkedIds + id
      }
      current.copy(bookmarkedIds = newBookmarks)
    }
  }

  fun finishOnboarding(name: String, experience: String, goals: Set<String>) {
    _uiState.update {
      it.copy(
        userName = name.ifBlank { "Awiskar" },
        experienceLevel = experience,
        learningGoals = goals,
        isOnboarded = true
      )
    }
    viewModelScope.launch {
      progressDao.setProgress(ProgressEntity("user_name", name.ifBlank { "Awiskar" }))
      progressDao.setProgress(ProgressEntity("is_onboarded", "true"))
    }
  }

  fun setCertificateVisible(visible: Boolean) {
    _uiState.update { it.copy(isCertificateVisible = visible) }
  }

  fun addNote(title: String, content: String, topic: String) {
    viewModelScope.launch {
      noteDao.insertNote(NoteEntity(title = title, content = content, topicTag = topic))
    }
  }

  fun deleteNote(id: Long) {
    viewModelScope.launch {
      noteDao.deleteNoteById(id)
    }
  }

  fun sendTutorMessage(prompt: String) {
    val userMsg = ChatMessage(
      id = System.currentTimeMillis().toString(),
      sender = "user",
      text = prompt
    )
    _uiState.update { it.copy(tutorChat = it.tutorChat + userMsg) }

    viewModelScope.launch {
      kotlinx.coroutines.delay(600)
      val tutorResponse = generateTutorAnswer(prompt)
      val botMsg = ChatMessage(
        id = (System.currentTimeMillis() + 1).toString(),
        sender = "tutor",
        text = tutorResponse.text,
        codeSnippet = tutorResponse.code
      )
      _uiState.update { it.copy(tutorChat = it.tutorChat + botMsg) }
    }
  }

  private data class TutorReply(val text: String, val code: String? = null)

  private fun generateTutorAnswer(prompt: String): TutorReply {
    val p = prompt.lowercase()
    return when {
      p.contains("pointer") && p.contains("reference") -> TutorReply(
        text = "Great question! Pointers vs References in C++:\n1. Pointers can be null ('nullptr'); References can NEVER be null.\n2. Pointers can be reseated (point to different addresses); References are bound permanently upon declaration.\n3. Pointers use & to get address and * to dereference; References behave transparently like the object itself.\n\nRule of thumb: Prefer pass-by-const-reference 'const T&' for function arguments to avoid costly copies.",
        code = "int a = 10;\nint* ptr = &a; // Pointer: holds memory address\nint& ref = a;  // Reference: alias to 'a'\n\n*ptr = 20; // modifies a through pointer\nref = 30;  // modifies a directly"
      )
      p.contains("smart pointer") || p.contains("unique_ptr") || p.contains("shared_ptr") -> TutorReply(
        text = "Modern C++ eliminates raw memory leaks using Smart Pointers (C++11):\n- std::unique_ptr: Exclusive ownership. Non-copyable, only movable. Destroys the managed object when going out of scope.\n- std::shared_ptr: Shared ownership. Maintains an atomic reference count. When count reaches 0, object is destroyed.\n- std::weak_ptr: Non-owning observer of a shared_ptr, prevents circular reference memory leaks.",
        code = "#include <memory>\n\n// Exclusive owner:\nauto u = std::make_unique<int>(42);\n\n// Shared owners:\nauto s1 = std::make_shared<int>(100);\nauto s2 = s1; // ref count = 2"
      )
      p.contains("memory leak") || p.contains("raii") -> TutorReply(
        text = "RAII (Resource Acquisition Is Initialization) is the #1 fundamental concept of C++. Resources (heap RAM, file handles, mutex locks) are acquired in the constructor and released in the destructor.\nBecause destructors always execute when an object leaves its scope—even if an exception is thrown—RAII provides leak-free deterministic cleanup.",
        code = "class FileGuard {\n    FILE* f;\npublic:\n    FileGuard(const char* name) { f = fopen(name, \"r\"); }\n    ~FileGuard() { if (f) fclose(f); } // Guarantees close!\n};"
      )
      p.contains("virtual") || p.contains("destructor") || p.contains("vtable") -> TutorReply(
        text = "Why base class destructors MUST be virtual:\nIf you delete a derived class object through a pointer to the base class ('Base* p = new Derived(); delete p;'), only ~Base() will execute if it is non-virtual! The Derived destructor will NOT run, causing resource leaks.\nAlways add 'virtual ~Base() = default;' to any polymorphic base class.",
        code = "class Base {\npublic:\n    virtual ~Base() = default; // Essential!\n    virtual void render() = 0; // Pure virtual\n};"
      )
      p.contains("vector") || p.contains("stl") -> TutorReply(
        text = "std::vector is a contiguous buffer. Key things to remember:\n1. size() is how many elements exist.\n2. capacity() is how much storage is allocated before reallocation.\n3. push_back may reallocate the whole buffer if size == capacity. This invalidates all iterators and references!",
        code = "std::vector<int> v;\nv.reserve(100); // Pre-allocates buffer, preventing iterator invalidation"
      )
      else -> TutorReply(
        text = "C++ emphasizes zero-cost abstractions, deterministic resource management via RAII, and compile-time type safety. How would you like to explore this further?",
        code = "// Modern C++20 idiom\ntemplate<typename T>\nconcept Printable = requires(T a) { std::cout << a; };"
      )
    }
  }
}
