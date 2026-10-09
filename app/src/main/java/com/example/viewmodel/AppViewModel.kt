package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.CuratedLessonsRepository
import com.example.data.GeminiLessonService
import com.example.model.BookStory
import com.example.model.ReaderTheme
import com.example.model.Subject
import com.example.model.UserSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class AppScreen {
    SPLASH,
    GOOGLE_SIGNIN,
    DISPLAY_NAME_SETUP,
    GRADE_SELECTION,
    DASHBOARD,
    BOOK_READER
}

data class AppUiState(
    val currentScreen: AppScreen = AppScreen.SPLASH,
    val userSession: UserSession = UserSession(isLoggedIn = false, streakDays = 0, xp = 0),
    val selectedSubject: Subject = Subject.HISTORY,
    val showSettingsSheet: Boolean = false,

    // AI Generation within subject
    val isGeneratingStory: Boolean = false,
    val generationError: String? = null,

    // Active Book Reading
    val activeStory: BookStory? = null,
    val readerTheme: ReaderTheme = ReaderTheme.MIDNIGHT,
    val readerFontSizeSp: Int = 16,
    val answeredQuestionIds: Set<Int> = emptySet()
)

class AppViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    // --- Splash Screen ---

    fun onSplashFinished() {
        _uiState.update { current ->
            if (current.currentScreen == AppScreen.SPLASH) {
                current.copy(
                    currentScreen = if (current.userSession.isLoggedIn) AppScreen.DASHBOARD else AppScreen.GOOGLE_SIGNIN
                )
            } else {
                current
            }
        }
    }

    // --- Google Sign-In Flow ---

    fun onGoogleSignInSuccess(email: String, allowNotifications: Boolean) {
        val cleanName = email.substringBefore("@")
            .split(".", "_", "-")
            .joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } }
            .ifBlank { "Student" }

        _uiState.update { current ->
            current.copy(
                userSession = current.userSession.copy(
                    isLoggedIn = true,
                    email = email.ifBlank { "student@learnicle.app" },
                    displayName = cleanName,
                    streakDays = 0, // Reset strictly to 0 Days
                    xp = 0,         // Reset strictly to 0 XP
                    notificationsEnabled = allowNotifications
                ),
                currentScreen = AppScreen.DISPLAY_NAME_SETUP // User Display Name Setup Step
            )
        }
    }

    // --- Display Name Setup Step ---

    fun onDisplayNameConfirmed(displayName: String) {
        _uiState.update { current ->
            current.copy(
                userSession = current.userSession.copy(
                    displayName = displayName.ifBlank { "Student" }
                ),
                currentScreen = AppScreen.GRADE_SELECTION // Next: Grade Selection Step
            )
        }
    }

    // --- Grade Selection & SSC Preparation Mode ---

    fun onGradeConfirmed(grade: Int, isSscMode: Boolean) {
        _uiState.update { current ->
            current.copy(
                userSession = current.userSession.copy(
                    gradeLevel = grade,
                    isSscMode = isSscMode
                ),
                currentScreen = AppScreen.DASHBOARD
            )
        }
    }

    fun onChangeGradeRequest() {
        _uiState.update { it.copy(currentScreen = AppScreen.GRADE_SELECTION) }
    }

    fun onSignOut() {
        _uiState.update {
            it.copy(
                userSession = it.userSession.copy(isLoggedIn = false, xp = 0, streakDays = 0),
                currentScreen = AppScreen.GOOGLE_SIGNIN,
                showSettingsSheet = false
            )
        }
    }

    // --- Subject Navigation ---

    fun onSelectSubject(subject: Subject) {
        _uiState.update { it.copy(selectedSubject = subject) }
    }

    // --- Book Reading Engine ---

    fun onOpenStory(story: BookStory) {
        _uiState.update {
            it.copy(
                activeStory = story,
                currentScreen = AppScreen.BOOK_READER,
                answeredQuestionIds = emptySet()
            )
        }
    }

    fun onZoomIn() {
        _uiState.update {
            val newSize = (it.readerFontSizeSp + 1).coerceAtMost(24)
            it.copy(readerFontSizeSp = newSize)
        }
    }

    fun onZoomOut() {
        _uiState.update {
            val newSize = (it.readerFontSizeSp - 1).coerceAtLeast(13)
            it.copy(readerFontSizeSp = newSize)
        }
    }

    fun onCycleReaderTheme(theme: ReaderTheme) {
        _uiState.update { it.copy(readerTheme = theme) }
    }

    fun onQuizAnswerSubmitted(questionId: Int, isCorrect: Boolean) {
        if (_uiState.value.answeredQuestionIds.contains(questionId)) return

        _uiState.update { current ->
            val updatedAnswered = current.answeredQuestionIds + questionId
            val addedXp = if (isCorrect) 10 else 0
            current.copy(
                answeredQuestionIds = updatedAnswered,
                userSession = current.userSession.copy(
                    xp = current.userSession.xp + addedXp
                )
            )
        }
    }

    fun onFinishStory() {
        val currentStory = _uiState.value.activeStory
        _uiState.update { current ->
            val completed = if (currentStory != null) current.userSession.completedStoryIds + currentStory.id else current.userSession.completedStoryIds
            current.copy(
                activeStory = null,
                currentScreen = AppScreen.DASHBOARD,
                userSession = current.userSession.copy(
                    completedStoryIds = completed,
                    streakDays = if (current.userSession.streakDays == 0) 1 else current.userSession.streakDays
                )
            )
        }
    }

    fun onExitReading() {
        _uiState.update {
            it.copy(activeStory = null, currentScreen = AppScreen.DASHBOARD)
        }
    }

    // --- AI Story Generation ---

    fun onGenerateStory(subject: Subject, topic: String) {
        _uiState.update { it.copy(isGeneratingStory = true, generationError = null) }
        viewModelScope.launch {
            val result = GeminiLessonService.generateStory(subject, topic)
            result.onSuccess { story ->
                _uiState.update {
                    it.copy(
                        isGeneratingStory = false,
                        activeStory = story,
                        currentScreen = AppScreen.BOOK_READER,
                        answeredQuestionIds = emptySet()
                    )
                }
            }.onFailure { err ->
                _uiState.update {
                    it.copy(
                        isGeneratingStory = false,
                        generationError = err.message ?: "Could not author chapter. Please try again."
                    )
                }
            }
        }
    }

    // --- Settings Overlay ---

    fun onToggleSettingsSheet(show: Boolean) {
        _uiState.update { it.copy(showSettingsSheet = show) }
    }

    fun onToggleNotifications(enabled: Boolean) {
        _uiState.update {
            it.copy(userSession = it.userSession.copy(notificationsEnabled = enabled))
        }
    }
}
