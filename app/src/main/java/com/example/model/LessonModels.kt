package com.example.model

enum class Subject(val displayName: String, val emoji: String, val tagline: String) {
    HISTORY("History", "🏛️", "Epochs, civilizations & national struggles"),
    SCIENCE("Science", "🔬", "Natural laws, matter, biology & physics"),
    ICT("ICT / Computer Studies", "💻", "Hardware, coding, networks & cybersecurity")
}

enum class GradeTier(val label: String, val grades: List<Int>) {
    PRIMARY("Grades 1–5 (Foundations)", listOf(1, 2, 3, 4, 5)),
    MIDDLE("Grades 6–8 (Intermediate)", listOf(6, 7, 8)),
    SECONDARY_SSC("Grades 9–12 (SSC Board Focus)", listOf(9, 10, 11, 12))
}

data class StoryChapter(
    val chapterNumber: Int,
    val title: String,
    val paragraphs: List<String>,
    val pullQuote: String = ""
)

data class VocabularyWord(
    val word: String,
    val partOfSpeech: String,
    val definition: String,
    val contextSentence: String
)

data class QuizQuestion(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctOptionIndex: Int,
    val explanation: String
)

data class BookStory(
    val id: String,
    val subject: Subject,
    val gradeTier: GradeTier = GradeTier.SECONDARY_SSC,
    val isSscSpecial: Boolean = false,
    val title: String,
    val subtitle: String,
    val readTimeMinutes: Int,
    val chapters: List<StoryChapter>,
    val vocabulary: List<VocabularyWord>,
    val quizQuestions: List<QuizQuestion>,
    val isAiGenerated: Boolean = false,
    val wikiOverview: String = "",
    val timelineSteps: List<String> = emptyList()
)
