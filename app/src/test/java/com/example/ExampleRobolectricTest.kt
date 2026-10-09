package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.CuratedLessonsRepository
import com.example.model.Subject
import com.example.viewmodel.AppScreen
import com.example.viewmodel.AppViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Learnicle", appName)
    }

    @Test
    fun `verify strict three subjects curriculum with 5 MCQs`() {
        val historyStories = CuratedLessonsRepository.getStories(Subject.HISTORY, gradeLevel = 10, isSscMode = true)
        val scienceStories = CuratedLessonsRepository.getStories(Subject.SCIENCE, gradeLevel = 10, isSscMode = true)
        val ictStories = CuratedLessonsRepository.getStories(Subject.ICT, gradeLevel = 10, isSscMode = true)

        assertTrue(historyStories.isNotEmpty())
        assertTrue(scienceStories.isNotEmpty())
        assertTrue(ictStories.isNotEmpty())

        val civilWar = CuratedLessonsRepository.getStoryById("history_civil_war")
        assertNotNull(civilWar)
        assertEquals(Subject.HISTORY, civilWar?.subject)
        assertTrue((civilWar?.vocabulary?.size ?: 0) >= 3)
        // Strictly 5 board exam MCQs
        assertEquals(5, civilWar?.quizQuestions?.size)
    }

    @Test
    fun `verify Google Sign-In and Grade Selection initializes at zero XP and zero streak`() {
        val viewModel = AppViewModel()
        assertEquals(AppScreen.GOOGLE_SIGNIN, viewModel.uiState.value.currentScreen)
        assertFalse(viewModel.uiState.value.userSession.isLoggedIn)

        // 1. Google Sign-In triggers Grade Selection step
        viewModel.onGoogleSignInSuccess("azrafbinm@gmail.com", allowNotifications = true)
        assertTrue(viewModel.uiState.value.userSession.isLoggedIn)
        assertEquals(AppScreen.GRADE_SELECTION, viewModel.uiState.value.currentScreen)

        // Strictly verified: fresh account starts at 0 XP and 0 Streak days
        assertEquals(0, viewModel.uiState.value.userSession.xp)
        assertEquals(0, viewModel.uiState.value.userSession.streakDays)
        assertTrue(viewModel.uiState.value.userSession.notificationsEnabled)

        // 2. Select Grade 10 with SSC Exam Preparation Mode
        viewModel.onGradeConfirmed(grade = 10, isSscMode = true)
        assertEquals(AppScreen.DASHBOARD, viewModel.uiState.value.currentScreen)
        assertEquals(10, viewModel.uiState.value.userSession.gradeLevel)
        assertTrue(viewModel.uiState.value.userSession.isSscMode)
    }

    @Test
    fun `test book reader font zoom and 5-MCQ scoring awards 10 XP per question`() {
        val viewModel = AppViewModel()
        viewModel.onGoogleSignInSuccess("azrafbinm@gmail.com", allowNotifications = true)
        viewModel.onGradeConfirmed(grade = 10, isSscMode = true)
        assertEquals(0, viewModel.uiState.value.userSession.xp)

        val story = CuratedLessonsRepository.getStoryById("history_civil_war")!!
        viewModel.onOpenStory(story)
        assertEquals(AppScreen.BOOK_READER, viewModel.uiState.value.currentScreen)

        // Test Zoom In and Zoom Out
        val initialFontSize = viewModel.uiState.value.readerFontSizeSp
        viewModel.onZoomIn()
        assertEquals(initialFontSize + 1, viewModel.uiState.value.readerFontSizeSp)
        viewModel.onZoomOut()
        assertEquals(initialFontSize, viewModel.uiState.value.readerFontSizeSp)

        // Answer Question 1 correctly: award +10 XP (0 -> 10)
        viewModel.onQuizAnswerSubmitted(1, isCorrect = true)
        assertEquals(10, viewModel.uiState.value.userSession.xp)

        // Answer Question 2 correctly: award +10 XP (10 -> 20)
        viewModel.onQuizAnswerSubmitted(2, isCorrect = true)
        assertEquals(20, viewModel.uiState.value.userSession.xp)

        // Answer Question 3 incorrectly: 0 XP added (still 20)
        viewModel.onQuizAnswerSubmitted(3, isCorrect = false)
        assertEquals(20, viewModel.uiState.value.userSession.xp)

        // Answer Question 4 correctly: award +10 XP (20 -> 30)
        viewModel.onQuizAnswerSubmitted(4, isCorrect = true)
        assertEquals(30, viewModel.uiState.value.userSession.xp)

        // Complete story
        viewModel.onFinishStory()
        assertEquals(AppScreen.DASHBOARD, viewModel.uiState.value.currentScreen)
        assertTrue(viewModel.uiState.value.userSession.completedStoryIds.contains(story.id))
    }
}
