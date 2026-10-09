package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CuratedLessonsRepository
import com.example.model.BookStory
import com.example.model.Subject
import com.example.model.UserSession
import com.example.ui.components.StreakBadge
import com.example.ui.components.XpBadge
import com.example.ui.theme.AccentGold
import com.example.ui.theme.AccentGreen
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CardElevated
import com.example.ui.theme.CardElevatedHover
import com.example.ui.theme.CrimsonAccent
import com.example.ui.theme.ObsidianBackground
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun DashboardScreen(
    userSession: UserSession,
    selectedSubject: Subject,
    isGenerating: Boolean,
    onSelectSubject: (Subject) -> Unit,
    onOpenStory: (BookStory) -> Unit,
    onGenerateStory: (Subject, String) -> Unit,
    onOpenSettings: () -> Unit,
    onChangeGradeClicked: () -> Unit
) {
    val stories = CuratedLessonsRepository.getStories(selectedSubject, userSession.gradeLevel, userSession.isSscMode)
    var customTopicInput by remember { mutableStateOf("") }
    var showGenerateField by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianBackground)
            .padding(horizontal = 20.dp)
            .testTag("dashboard_screen")
    ) {
        // 1. Header Bar
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // User Profile
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onOpenSettings() }
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    listOf(CrimsonAccent, Color(0xFF4A101C))
                                )
                            )
                            .border(1.5.dp, CrimsonAccent, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = userSession.displayName.take(2).uppercase(),
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = userSession.displayName,
                            style = MaterialTheme.typography.titleMedium,
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Fresh Account",
                            style = MaterialTheme.typography.bodySmall,
                            color = CrimsonAccent,
                            fontSize = 11.sp
                        )
                    }
                }

                // Stats: Streak & Live XP (starts at 0)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    StreakBadge(streakDays = userSession.streakDays, onClick = onOpenSettings)
                    Spacer(modifier = Modifier.width(8.dp))
                    XpBadge(xp = userSession.xp)
                    Spacer(modifier = Modifier.width(4.dp))
                    IconButton(
                        onClick = onOpenSettings,
                        modifier = Modifier.testTag("notification_bell_button")
                    ) {
                        Icon(
                            imageVector = if (userSession.notificationsEnabled) Icons.Default.NotificationsActive else Icons.Default.Notifications,
                            contentDescription = "Notifications Settings",
                            tint = if (userSession.notificationsEnabled) CrimsonAccent else TextSecondary,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Grade & SSC Preparation Badge Pill
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (userSession.isSscMode) Color(0xFF261016) else CardElevated)
                    .border(
                        BorderStroke(
                            1.dp,
                            if (userSession.isSscMode) CrimsonAccent.copy(alpha = 0.5f) else BorderSubtle
                        ),
                        RoundedCornerShape(12.dp)
                    )
                    .clickable { onChangeGradeClicked() }
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "🎓 Grade ${userSession.gradeLevel}",
                        style = MaterialTheme.typography.labelMedium,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    if (userSession.isSscMode) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(CrimsonAccent)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "SSC EXAM PREP MODE",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 9.sp
                            )
                        }
                    }
                }

                Text(
                    text = "Change Grade ❯",
                    style = MaterialTheme.typography.bodySmall,
                    color = CrimsonAccent,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // 2. Strict Subject Hub Tabs (ONLY History, Science, ICT)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(CardElevated)
                    .border(BorderStroke(1.dp, BorderSubtle), RoundedCornerShape(16.dp))
                    .padding(14.dp)
            ) {
                Text(
                    text = "CURRICULUM HUBS",
                    style = MaterialTheme.typography.labelSmall,
                    color = CrimsonAccent,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Subject.values().forEach { subject ->
                        val isSelected = selectedSubject == subject
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isSelected) CrimsonAccent else CardElevatedHover)
                                .border(
                                    BorderStroke(
                                        1.dp,
                                        if (isSelected) CrimsonAccent else BorderSubtle
                                    ),
                                    RoundedCornerShape(12.dp)
                                )
                                .clickable { onSelectSubject(subject) }
                                .padding(horizontal = 8.dp, vertical = 6.dp)
                                .testTag("subject_tab_${subject.name.lowercase()}"),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = subject.emoji,
                                    fontSize = 16.sp
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = if (subject == Subject.ICT) "ICT" else subject.displayName,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = if (isSelected) Color.White else TextSecondary,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Active Subject Tagline
                Text(
                    text = selectedSubject.tagline,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMuted,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        // 3. AI Study Assistant & Dynamic Guide Generator Card
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(CardElevated)
                    .border(
                        BorderStroke(1.dp, CrimsonAccent.copy(alpha = 0.45f)),
                        RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
                    .testTag("ai_study_assistant_card")
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    Brush.linearGradient(
                                        listOf(CrimsonAccent, Color(0xFF4A101C))
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Learnicle AI Study Assistant",
                                style = MaterialTheme.typography.titleMedium,
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Wikipedia-style breakdowns & MCQs for ANY topic",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondary,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xFF261016))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "GEMINI 3.8",
                            style = MaterialTheme.typography.labelSmall,
                            color = CrimsonAccent,
                            fontWeight = FontWeight.Bold,
                            fontSize = 9.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Input field
                OutlinedTextField(
                    value = customTopicInput,
                    onValueChange = { customTopicInput = it },
                    placeholder = {
                        Text(
                            text = "Ask Learnicle AI about any topic...",
                            color = Color(0xFF757575),
                            fontSize = 13.sp
                        )
                    },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(18.dp))
                    },
                    trailingIcon = {
                        if (customTopicInput.isNotBlank()) {
                            IconButton(onClick = { customTopicInput = "" }) {
                                Icon(Icons.Default.Schedule, contentDescription = "Clear", tint = TextSecondary, modifier = Modifier.size(16.dp))
                            }
                        }
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("ai_study_assistant_input"),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = CardElevatedHover,
                        unfocusedContainerColor = CardElevatedHover,
                        focusedBorderColor = CrimsonAccent,
                        unfocusedBorderColor = BorderSubtle,
                        focusedTextColor = Color(0xFFFFFFFF),
                        unfocusedTextColor = Color(0xFFFFFFFF),
                        focusedPlaceholderColor = Color(0xFF757575),
                        unfocusedPlaceholderColor = Color(0xFF757575),
                        cursorColor = CrimsonAccent
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Quick Prompt Pills
                val quickPrompts = when (selectedSubject) {
                    Subject.HISTORY -> listOf(
                        "Explain Bangladesh Liberation War 1971",
                        "Language Movement 1952",
                        "Somapura Mahavihara & Ancient Bengal",
                        "Cold War Space Race & Moon Landing"
                    )
                    Subject.SCIENCE -> listOf(
                        "How do Semiconductor Diodes work?",
                        "Newton's Laws of Motion & Momentum",
                        "Photosynthesis & Calvin Cycle",
                        "Faraday's Law of Electromagnetic Induction"
                    )
                    Subject.ICT -> listOf(
                        "Explain HTML5 & CSS Grid",
                        "Star vs Mesh Network Topologies",
                        "Universal Logic Gates (NAND & NOR)",
                        "SQL Queries & Relational Databases"
                    )
                }

                Text(
                    text = "QUICK PROMPTS",
                    style = MaterialTheme.typography.labelSmall,
                    color = CrimsonAccent,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(6.dp))

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    quickPrompts.take(3).forEach { promptText ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(CardElevatedHover)
                                .border(BorderStroke(1.dp, BorderSubtle), RoundedCornerShape(8.dp))
                                .clickable {
                                    customTopicInput = promptText
                                }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "💡 $promptText",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextPrimary,
                                    fontSize = 11.sp
                                )
                                Text(
                                    text = "Tap to use",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = CrimsonAccent,
                                    fontSize = 9.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        if (customTopicInput.isNotBlank()) {
                            onGenerateStory(selectedSubject, customTopicInput)
                        }
                    },
                    enabled = !isGenerating && customTopicInput.isNotBlank(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CrimsonAccent),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .testTag("submit_ai_assistant_button")
                ) {
                    if (isGenerating) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Authoring Study Guide & Quiz...", style = MaterialTheme.typography.labelMedium)
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Bolt, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                "Generate Wikipedia-Style Guide (+10 XP / Q)",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        // 4. Section Title & Stories List
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "${selectedSubject.displayName} Topic Hub",
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Exhaustive curriculum lessons with vocabulary & board MCQs",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF2C1016))
                        .border(BorderStroke(1.dp, CrimsonAccent.copy(alpha = 0.5f)), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "${stories.size} Lessons",
                        style = MaterialTheme.typography.labelSmall,
                        color = CrimsonAccent,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
        }

        // 5. Stories List for Selected Subject
        items(stories) { story ->
            val isCompleted = userSession.completedStoryIds.contains(story.id)
            StoryBookCard(
                story = story,
                isCompleted = isCompleted,
                onClick = { onOpenStory(story) }
            )
            Spacer(modifier = Modifier.height(14.dp))
        }

        item {
            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

@Composable
private fun StoryBookCard(
    story: BookStory,
    isCompleted: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(CardElevated)
            .border(
                BorderStroke(
                    1.dp,
                    if (isCompleted) AccentGreen.copy(alpha = 0.5f) else BorderSubtle
                ),
                RoundedCornerShape(16.dp)
            )
            .clickable { onClick() }
            .padding(18.dp)
            .testTag("story_card_${story.id}")
    ) {
        Column {
            // Badges row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(CardElevatedHover)
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = story.subject.displayName.uppercase(),
                            style = MaterialTheme.typography.bodySmall,
                            color = TextMuted,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = null,
                            tint = TextMuted,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${story.readTimeMinutes} min read",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextMuted,
                            fontSize = 11.sp
                        )
                    }
                }

                if (isCompleted) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Completed",
                            tint = AccentGreen,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "COMPLETED",
                            style = MaterialTheme.typography.labelSmall,
                            color = AccentGreen,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xFF261016))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "+${story.quizQuestions.size * 10} XP",
                            style = MaterialTheme.typography.labelSmall,
                            color = CrimsonAccent,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Book Title & Subtitle
            Text(
                text = story.title,
                style = MaterialTheme.typography.titleLarge,
                color = TextPrimary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = story.subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
                lineHeight = 19.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Story specs bar: Chapters, Vocabulary count, Questions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.AutoMirrored.Filled.MenuBook, contentDescription = null, tint = TextMuted, modifier = Modifier.size(13.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${story.chapters.size} Chapters",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextMuted,
                        fontSize = 11.sp
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(Icons.Default.Translate, contentDescription = null, tint = TextMuted, modifier = Modifier.size(13.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${story.vocabulary.size} Key Terms",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextMuted,
                        fontSize = 11.sp
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(CardElevatedHover)
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = if (isCompleted) "Re-read Book" else "Read & Quiz",
                        style = MaterialTheme.typography.labelMedium,
                        color = CrimsonAccent,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = CrimsonAccent,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
    }
}
