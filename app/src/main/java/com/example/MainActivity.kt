package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.PersistentTopHeaderBar
import com.example.ui.components.SettingsBottomSheet
import com.example.ui.screens.BookReaderScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.GoogleSignInScreen
import com.example.ui.screens.GradeSelectionScreen
import com.example.ui.theme.LearnicleTheme
import com.example.ui.theme.ObsidianBackground
import com.example.viewmodel.AppScreen
import com.example.viewmodel.AppViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LearnicleTheme {
                LearnicleApp()
            }
        }
    }
}

@Composable
fun LearnicleApp(
    viewModel: AppViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.generationError) {
        uiState.generationError?.let { err ->
            snackbarHostState.showSnackbar(err)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = ObsidianBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(ObsidianBackground)
        ) {
            // Persistent Top Header Bar with v1.0 subscript and live user stats
            PersistentTopHeaderBar(
                streakDays = uiState.userSession.streakDays,
                xp = uiState.userSession.xp,
                onHeaderClick = { viewModel.onToggleSettingsSheet(true) }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(ObsidianBackground)
            ) {
                Crossfade(
                    targetState = uiState.currentScreen,
                    animationSpec = tween(300),
                    label = "ScreenTransition"
                ) { screen ->
                    when (screen) {
                        AppScreen.GOOGLE_SIGNIN -> {
                            GoogleSignInScreen(
                                onSignInSuccess = { email, allowNotifications ->
                                    viewModel.onGoogleSignInSuccess(email, allowNotifications)
                                }
                            )
                        }

                        AppScreen.GRADE_SELECTION -> {
                            GradeSelectionScreen(
                                initialGrade = uiState.userSession.gradeLevel,
                                initialSscMode = uiState.userSession.isSscMode,
                                onGradeConfirmed = { grade, isSscMode ->
                                    viewModel.onGradeConfirmed(grade, isSscMode)
                                }
                            )
                        }

                        AppScreen.DASHBOARD -> {
                            DashboardScreen(
                                userSession = uiState.userSession,
                                selectedSubject = uiState.selectedSubject,
                                isGenerating = uiState.isGeneratingStory,
                                onSelectSubject = { viewModel.onSelectSubject(it) },
                                onOpenStory = { viewModel.onOpenStory(it) },
                                onGenerateStory = { subject, topic -> viewModel.onGenerateStory(subject, topic) },
                                onOpenSettings = { viewModel.onToggleSettingsSheet(true) },
                                onChangeGradeClicked = { viewModel.onChangeGradeRequest() }
                            )
                        }

                        AppScreen.BOOK_READER -> {
                            val story = uiState.activeStory
                            if (story != null) {
                                BookReaderScreen(
                                    story = story,
                                    currentXp = uiState.userSession.xp,
                                    streakDays = uiState.userSession.streakDays,
                                    readerTheme = uiState.readerTheme,
                                    fontSizeSp = uiState.readerFontSizeSp,
                                    onZoomIn = { viewModel.onZoomIn() },
                                    onZoomOut = { viewModel.onZoomOut() },
                                    onCycleTheme = { viewModel.onCycleReaderTheme(it) },
                                    onQuizAnswerSubmitted = { qId, isCorrect ->
                                        viewModel.onQuizAnswerSubmitted(qId, isCorrect)
                                    },
                                    onFinishStory = { viewModel.onFinishStory() },
                                    onBack = { viewModel.onExitReading() }
                                )
                            }
                        }
                    }
                }
            }

            // Quick Settings Overlay
            if (uiState.showSettingsSheet) {
                SettingsBottomSheet(
                    userSession = uiState.userSession,
                    onDismiss = { viewModel.onToggleSettingsSheet(false) },
                    onUpdateReminderTime = { /* no-op */ },
                    onToggleNotifications = { viewModel.onToggleNotifications(it) },
                    onSignOut = { viewModel.onSignOut() }
                )
            }
        }
    }
}
