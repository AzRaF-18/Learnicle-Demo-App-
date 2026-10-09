package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOut
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BookStory
import com.example.model.QuizQuestion
import com.example.model.ReaderTheme
import com.example.model.VocabularyWord
import com.example.ui.components.ConfettiCelebration
import com.example.ui.components.StreakBadge
import com.example.ui.components.XpBadge
import com.example.ui.theme.AccentGold
import com.example.ui.theme.AccentGreen
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CrimsonAccent

@Composable
fun BookReaderScreen(
    story: BookStory,
    currentXp: Int,
    streakDays: Int = 0,
    readerTheme: ReaderTheme,
    fontSizeSp: Int,
    onZoomIn: () -> Unit,
    onZoomOut: () -> Unit,
    onCycleTheme: (ReaderTheme) -> Unit,
    onQuizAnswerSubmitted: (questionId: Int, isCorrect: Boolean) -> Unit,
    onFinishStory: () -> Unit,
    onBack: () -> Unit
) {
    BackHandler { onBack() }

    val bgColor = Color(readerTheme.bgHex)
    val textColor = Color(readerTheme.textHex)
    val cardBg = Color(readerTheme.cardHex)

    // Quiz states: Map of questionId -> selectedOptionIndex
    val selectedAnswers = remember { mutableStateMapOf<Int, Int>() }
    // Map of questionId -> isChecked
    val checkedQuestions = remember { mutableStateMapOf<Int, Boolean>() }
    // Vocabulary accordion expansion state
    var expandedVocabIndex by remember { mutableStateOf<Int?>(0) }
    // Gamification pop & confetti trigger counter
    var correctCelebrationTrigger by remember { mutableStateOf<Long?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .testTag("book_reader_screen")
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            // 1. Digital Book Top Navigation & Reading Controls
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("reader_back_button")) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = textColor
                        )
                    }

                    // Reading Controls: Zoom Out, Font Indicator, Zoom In, Theme Switcher
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(cardBg)
                            .border(BorderStroke(1.dp, BorderSubtle), RoundedCornerShape(20.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        IconButton(
                            onClick = onZoomOut,
                            enabled = fontSizeSp > 13,
                            modifier = Modifier
                                .size(32.dp)
                                .testTag("zoom_out_button")
                        ) {
                            Icon(Icons.Default.ZoomOut, contentDescription = "Zoom Out", tint = textColor, modifier = Modifier.size(16.dp))
                        }

                        Text(
                            text = "${fontSizeSp}sp",
                            style = MaterialTheme.typography.labelSmall,
                            color = textColor,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )

                        IconButton(
                            onClick = onZoomIn,
                            enabled = fontSizeSp < 24,
                            modifier = Modifier
                                .size(32.dp)
                                .testTag("zoom_in_button")
                        ) {
                            Icon(Icons.Default.ZoomIn, contentDescription = "Zoom In", tint = textColor, modifier = Modifier.size(16.dp))
                        }

                        Box(
                            modifier = Modifier
                                .height(16.dp)
                                .width(1.dp)
                                .background(BorderSubtle)
                        )

                        // Theme Cycle Button
                        Box(
                            modifier = Modifier
                                .clickable {
                                    val next = when (readerTheme) {
                                        ReaderTheme.MIDNIGHT -> ReaderTheme.WARM_PARCHMENT
                                        ReaderTheme.WARM_PARCHMENT -> ReaderTheme.SEPIA
                                        ReaderTheme.SEPIA -> ReaderTheme.MIDNIGHT
                                    }
                                    onCycleTheme(next)
                                }
                                .padding(horizontal = 8.dp, vertical = 6.dp)
                                .testTag("cycle_theme_button"),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Palette, contentDescription = null, tint = CrimsonAccent, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = readerTheme.displayName,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = textColor,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }

                    // Stats: Streak & Live XP Badges with pop animation on correct quiz answers
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        StreakBadge(
                            streakDays = streakDays,
                            popTrigger = correctCelebrationTrigger
                        )
                        XpBadge(
                            xp = currentXp,
                            popTrigger = correctCelebrationTrigger
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }

            // 2. Book Title & Header
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(cardBg)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = story.subject.displayName.uppercase(),
                            style = MaterialTheme.typography.labelSmall,
                            color = CrimsonAccent,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = story.title,
                        style = MaterialTheme.typography.displayLarge.copy(fontFamily = FontFamily.Serif),
                        color = textColor,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        lineHeight = 36.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = story.subtitle,
                        style = MaterialTheme.typography.titleMedium,
                        color = textColor.copy(alpha = 0.75f),
                        textAlign = TextAlign.Center,
                        lineHeight = 22.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Estimated Read: ${story.readTimeMinutes} minutes • ${story.chapters.size} Chapters",
                        style = MaterialTheme.typography.bodySmall,
                        color = textColor.copy(alpha = 0.5f),
                        fontSize = 12.sp
                    )
                }

                // Wikipedia Minimalist-Style Structured Summary Card
                val overviewText = story.wikiOverview.ifBlank {
                    "An authoritative, structured curriculum study guide covering core concepts, chronological progression, and foundational principles for ${story.title}."
                }
                var showWikiOverview by remember { mutableStateOf(true) }
                var showTimelineSteps by remember { mutableStateOf(true) }

                Spacer(modifier = Modifier.height(20.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(cardBg)
                        .border(BorderStroke(1.dp, BorderSubtle), RoundedCornerShape(14.dp))
                        .padding(16.dp)
                        .testTag("wiki_summary_section")
                ) {
                    // Header row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showWikiOverview = !showWikiOverview },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color(0xFF2C1016)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Info, contentDescription = null, tint = CrimsonAccent, modifier = Modifier.size(16.dp))
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Wikipedia Minimalist Summary",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = textColor,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                                Text(
                                    text = "Encyclopedic Overview & Core Synopsis",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = textColor.copy(alpha = 0.6f),
                                    fontSize = 11.sp
                                )
                            }
                        }

                        Icon(
                            imageVector = if (showWikiOverview) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = "Toggle Overview",
                            tint = textColor.copy(alpha = 0.7f),
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    AnimatedVisibility(visible = showWikiOverview) {
                        Column(modifier = Modifier.padding(top = 12.dp)) {
                            Text(
                                text = overviewText,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = (fontSizeSp - 1).sp,
                                    lineHeight = ((fontSizeSp - 1) * 1.55f).sp
                                ),
                                color = textColor.copy(alpha = 0.9f)
                            )
                        }
                    }

                    // Timeline & Key Steps
                    if (story.timelineSteps.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(14.dp))
                        HorizontalDivider(color = BorderSubtle.copy(alpha = 0.5f), thickness = 1.dp)
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { showTimelineSteps = !showTimelineSteps },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Timeline, contentDescription = null, tint = CrimsonAccent, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Chronology / Concept Milestones",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = textColor,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Icon(
                                imageVector = if (showTimelineSteps) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = "Toggle Timeline",
                                tint = textColor.copy(alpha = 0.7f),
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        AnimatedVisibility(visible = showTimelineSteps) {
                            Column(modifier = Modifier.padding(top = 10.dp)) {
                                story.timelineSteps.forEachIndexed { sIdx, step ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp),
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .padding(top = 2.dp)
                                                .size(18.dp)
                                                .clip(CircleShape)
                                                .background(CrimsonAccent.copy(alpha = 0.2f))
                                                .border(1.dp, CrimsonAccent, CircleShape),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = "${sIdx + 1}",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = Color.White,
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(
                                            text = step,
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                fontSize = 12.sp,
                                                lineHeight = 18.sp
                                            ),
                                            color = textColor.copy(alpha = 0.85f)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider(color = BorderSubtle, thickness = 1.dp)
                Spacer(modifier = Modifier.height(24.dp))
            }

            // 3. Chapter-Based Story Content
            story.chapters.forEach { chapter ->
                item {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        // Chapter Heading
                        Text(
                            text = "CHAPTER ${chapter.chapterNumber}",
                            style = MaterialTheme.typography.labelSmall,
                            color = CrimsonAccent,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.5.sp
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = chapter.title,
                            style = MaterialTheme.typography.headlineMedium.copy(fontFamily = FontFamily.Serif),
                            color = textColor,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Paragraphs formatted with custom zoom font size and generous line height
                        chapter.paragraphs.forEachIndexed { pIdx, paragraph ->
                            Text(
                                text = paragraph,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = fontSizeSp.sp,
                                    lineHeight = (fontSizeSp * 1.6f).sp,
                                    fontFamily = FontFamily.Serif
                                ),
                                color = textColor,
                                modifier = Modifier.padding(bottom = 14.dp)
                            )
                        }

                        // Pull Quote Card
                        if (chapter.pullQuote.isNotBlank()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(cardBg)
                                    .border(BorderStroke(1.dp, CrimsonAccent.copy(alpha = 0.35f)), RoundedCornerShape(12.dp))
                                    .padding(18.dp)
                            ) {
                                Text(
                                    text = chapter.pullQuote,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontStyle = FontStyle.Italic,
                                        fontFamily = FontFamily.Serif,
                                        fontSize = (fontSizeSp - 1).sp,
                                        lineHeight = ((fontSizeSp - 1) * 1.5f).sp
                                    ),
                                    color = textColor.copy(alpha = 0.9f)
                                )
                            }
                            Spacer(modifier = Modifier.height(22.dp))
                        }
                    }
                }
            }

            // Divider before post-reading section
            item {
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = BorderSubtle, thickness = 1.dp)
                Spacer(modifier = Modifier.height(28.dp))
            }

            // 4. POST-READING SECTION 1: "Key Vocabulary & Word Meanings"
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(cardBg)
                        .border(BorderStroke(1.dp, BorderSubtle), RoundedCornerShape(16.dp))
                        .padding(18.dp)
                        .testTag("vocabulary_section")
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFF2C1016)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Translate, contentDescription = null, tint = CrimsonAccent, modifier = Modifier.size(16.dp))
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Key Terms & Definitions",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = textColor,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Master sophisticated vocabulary & conceptual terms",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = textColor.copy(alpha = 0.6f),
                                    fontSize = 11.sp
                                )
                            }
                        }

                        Text(
                            text = "${story.vocabulary.size} Terms",
                            style = MaterialTheme.typography.labelSmall,
                            color = CrimsonAccent,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Vocabulary Accordion Cards
                    story.vocabulary.forEachIndexed { vIdx, vocab ->
                        val isExpanded = expandedVocabIndex == vIdx
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isExpanded) bgColor else Color(0x33000000))
                                .border(BorderStroke(1.dp, if (isExpanded) CrimsonAccent.copy(alpha = 0.5f) else BorderSubtle), RoundedCornerShape(10.dp))
                                .clickable {
                                    expandedVocabIndex = if (isExpanded) null else vIdx
                                }
                                .padding(12.dp)
                                .testTag("vocab_card_$vIdx")
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = vocab.word,
                                            style = MaterialTheme.typography.titleMedium,
                                            color = textColor,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "(${vocab.partOfSpeech})",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = textColor.copy(alpha = 0.5f),
                                            fontSize = 11.sp,
                                            fontStyle = FontStyle.Italic
                                        )
                                    }

                                    Icon(
                                        imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                        contentDescription = "Expand",
                                        tint = textColor.copy(alpha = 0.7f),
                                        modifier = Modifier.size(18.dp)
                                    )
                                }

                                AnimatedVisibility(visible = isExpanded) {
                                    Column(modifier = Modifier.padding(top = 8.dp)) {
                                        Text(
                                            text = vocab.definition,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = textColor.copy(alpha = 0.9f),
                                            lineHeight = 20.sp
                                        )
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(
                                            text = "“${vocab.contextSentence}”",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = AccentGold,
                                            fontSize = 12.sp,
                                            fontStyle = FontStyle.Italic
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))
            }

            // 5. POST-READING SECTION 2: "Story Comprehension Quiz"
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(cardBg)
                        .border(BorderStroke(1.dp, BorderSubtle), RoundedCornerShape(16.dp))
                        .padding(18.dp)
                        .testTag("quiz_section")
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFF2C1016)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Quiz, contentDescription = null, tint = CrimsonAccent, modifier = Modifier.size(16.dp))
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Board Exam Practice / MCQs",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = textColor,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "5-Question Quiz • +10 XP awarded per correct answer!",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = CrimsonAccent,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFF261016))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "${story.quizQuestions.size} Questions",
                                style = MaterialTheme.typography.labelSmall,
                                color = CrimsonAccent,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Questions List
                    story.quizQuestions.forEachIndexed { qIdx, question ->
                        val selectedOpt = selectedAnswers[question.id]
                        val isChecked = checkedQuestions[question.id] == true
                        val isCorrect = selectedOpt == question.correctOptionIndex

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(bgColor)
                                .border(
                                    BorderStroke(
                                        1.dp,
                                        when {
                                            isChecked && isCorrect -> AccentGreen
                                            isChecked && !isCorrect -> CrimsonAccent
                                            else -> BorderSubtle
                                        }
                                    ),
                                    RoundedCornerShape(12.dp)
                                )
                                .padding(14.dp)
                                .testTag("quiz_question_${question.id}")
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(22.dp)
                                        .clip(CircleShape)
                                        .background(CrimsonAccent),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "${qIdx + 1}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = question.question,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = textColor,
                                    fontWeight = FontWeight.SemiBold,
                                    lineHeight = 20.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Options
                            question.options.forEachIndexed { optIdx, optText ->
                                val isOptSelected = selectedOpt == optIdx
                                val isOptCorrectAnswer = optIdx == question.correctOptionIndex

                                val optBorderColor by animateColorAsState(
                                    targetValue = when {
                                        isChecked && isOptCorrectAnswer -> AccentGreen
                                        isChecked && isOptSelected && !isCorrect -> CrimsonAccent
                                        isOptSelected -> CrimsonAccent
                                        else -> BorderSubtle
                                    },
                                    animationSpec = tween(200),
                                    label = "optBorder"
                                )

                                val optBgColor by animateColorAsState(
                                    targetValue = when {
                                        isChecked && isOptCorrectAnswer -> Color(0xFF0F311C)
                                        isChecked && isOptSelected && !isCorrect -> Color(0xFF2C1016)
                                        isOptSelected -> Color(0xFF261016)
                                        else -> cardBg
                                    },
                                    animationSpec = tween(200),
                                    label = "optBg"
                                )

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(optBgColor)
                                        .border(
                                            BorderStroke(
                                                if (isOptSelected || (isChecked && isOptCorrectAnswer)) 1.5.dp else 1.dp,
                                                optBorderColor
                                            ),
                                            RoundedCornerShape(8.dp)
                                        )
                                        .clickable(enabled = !isChecked) {
                                            selectedAnswers[question.id] = optIdx
                                        }
                                        .padding(10.dp)
                                        .testTag("quiz_${question.id}_opt_$optIdx")
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(20.dp)
                                                .clip(CircleShape)
                                                .background(
                                                    when {
                                                        isChecked && isOptCorrectAnswer -> AccentGreen
                                                        isChecked && isOptSelected -> CrimsonAccent
                                                        isOptSelected -> CrimsonAccent
                                                        else -> Color(0xFF141419)
                                                    }
                                                ),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = ('A'.code + optIdx).toChar().toString(),
                                                style = MaterialTheme.typography.labelSmall,
                                                color = if (isOptSelected || (isChecked && isOptCorrectAnswer)) Color.Black else textColor.copy(alpha = 0.6f),
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 10.sp
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(
                                            text = optText,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = textColor,
                                            fontSize = 13.sp
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                            }

                            // Check Answer / Feedback action
                            if (!isChecked) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Button(
                                    onClick = {
                                        checkedQuestions[question.id] = true
                                        val correct = selectedOpt == question.correctOptionIndex
                                        if (correct) {
                                            correctCelebrationTrigger = System.currentTimeMillis()
                                        }
                                        onQuizAnswerSubmitted(question.id, correct)
                                    },
                                    enabled = selectedOpt != null,
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = CrimsonAccent,
                                        contentColor = Color.White
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(38.dp)
                                        .testTag("check_quiz_${question.id}")
                                ) {
                                    Text("Check Answer", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                                }
                            } else {
                                // Feedback banner
                                Spacer(modifier = Modifier.height(8.dp))
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (isCorrect) Color(0xFF0F311C) else Color(0xFF2C1016))
                                        .padding(10.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = if (isCorrect) "✓ Correct! (+10 XP Earned)" else "✗ Incorrect",
                                            style = MaterialTheme.typography.labelMedium,
                                            color = if (isCorrect) AccentGreen else CrimsonAccent,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = question.explanation,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = textColor.copy(alpha = 0.85f),
                                        fontSize = 11.sp,
                                        lineHeight = 16.sp
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }

            // 6. Complete Story & Return Button
            item {
                Button(
                    onClick = onFinishStory,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CrimsonAccent,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("finish_reading_button")
                ) {
                    Icon(Icons.Default.WorkspacePremium, contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Complete Story & Return to Hub",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(36.dp))
            }
        }

        // Gamification Confetti Overlay
        ConfettiCelebration(
            triggerKey = correctCelebrationTrigger,
            modifier = Modifier.fillMaxSize()
        )
    }
}
