package com.example.data

import android.util.Log
import com.example.BuildConfig
import com.example.model.BookStory
import com.example.model.QuizQuestion
import com.example.model.StoryChapter
import com.example.model.Subject
import com.example.model.VocabularyWord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object GeminiLessonService {

    private const val TAG = "GeminiLessonService"
    private const val MODEL_NAME = "gemini-3.8-flash"
    private const val BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    suspend fun generateStory(
        subject: Subject,
        topic: String
    ): Result<BookStory> = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (_: Exception) {
            ""
        }

        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            Log.d(TAG, "Using rich offline generator for subject: ${subject.displayName}, topic: $topic")
            return@withContext Result.success(createSynthesizedStory(subject, topic))
        }

        try {
            val endpoint = "$BASE_URL/$MODEL_NAME:generateContent?key=$apiKey"
            val prompt = """
                You are a master Wikipedia-style textbook author and micro-learning specialist for Learnicle.
                Generate an exhaustive, structured, highly-educational study guide and book narrative strictly for the subject: "${subject.displayName}".
                Topic: "$topic".
                Requirements:
                - Concise structured Wikipedia-style overview summary.
                - 4-5 Chronological timeline milestones or logical concept steps.
                - 2 distinct in-depth chapters of structured textbook narrative prose with paragraphs and memorable quotes.
                - 4 key vocabulary terms with part of speech, definitions, and contextual usage sentences.
                - 3 Multiple Choice comprehension questions with accurate answers and detailed explanations.

                Return ONLY valid raw JSON with this exact schema:
                {
                  "title": "$topic",
                  "subtitle": "An authoritative study guide for Learnicle",
                  "wikiOverview": "A concise, structured Wikipedia-style summary of this topic.",
                  "timelineSteps": [
                    "Key Step or Date 1: Description of milestone...",
                    "Key Step or Date 2: Description of milestone...",
                    "Key Step or Date 3: Description of milestone..."
                  ],
                  "readTimeMinutes": 4,
                  "chapters": [
                    {
                      "chapterNumber": 1,
                      "title": "Chapter 1 Title",
                      "paragraphs": ["First paragraph...", "Second paragraph..."],
                      "pullQuote": "Memorable historical or scientific quote."
                    },
                    {
                      "chapterNumber": 2,
                      "title": "Chapter 2 Title",
                      "paragraphs": ["First paragraph...", "Second paragraph..."],
                      "pullQuote": "Memorable concluding quote."
                    }
                  ],
                  "vocabulary": [
                    {
                      "word": "Term",
                      "partOfSpeech": "noun",
                      "definition": "Clear concise definition",
                      "contextSentence": "How it was used in the study guide."
                    }
                  ],
                  "quizQuestions": [
                    {
                      "id": 1,
                      "question": "Question text?",
                      "options": ["Option A", "Option B", "Option C"],
                      "correctOptionIndex": 0,
                      "explanation": "Clear explanation of the correct answer."
                    }
                  ]
                }
            """.trimIndent()

            val requestBodyJson = JSONObject().apply {
                val contentsArray = JSONArray().apply {
                    val contentObj = JSONObject().apply {
                        val partsArray = JSONArray().apply {
                            put(JSONObject().apply { put("text", prompt) })
                        }
                        put("parts", partsArray)
                    }
                    put(contentObj)
                }
                put("contents", contentsArray)

                val generationConfig = JSONObject().apply {
                    put("responseMimeType", "application/json")
                    put("temperature", 0.7)
                }
                put("generationConfig", generationConfig)
            }

            val request = Request.Builder()
                .url(endpoint)
                .post(requestBodyJson.toString().toRequestBody("application/json".toMediaType()))
                .build()

            val response = okHttpClient.newCall(request).execute()
            if (!response.isSuccessful) {
                return@withContext Result.success(createSynthesizedStory(subject, topic))
            }

            val responseString = response.body?.string() ?: ""
            val parsedStory = parseGeminiResponse(responseString, subject, topic)
            Result.success(parsedStory)
        } catch (e: Exception) {
            Log.e(TAG, "Error generating story via Gemini, falling back: ${e.message}", e)
            Result.success(createSynthesizedStory(subject, topic))
        }
    }

    private fun parseGeminiResponse(rawJson: String, subject: Subject, topic: String): BookStory {
        try {
            val root = JSONObject(rawJson)
            val candidate = root.optJSONArray("candidates")?.optJSONObject(0)
            val text = candidate?.optJSONObject("content")?.optJSONArray("parts")?.optJSONObject(0)?.optString("text") ?: ""

            val storyJson = JSONObject(text.trim().removePrefix("```json").removePrefix("```").removeSuffix("```").trim())
            val title = storyJson.optString("title", topic)
            val subtitle = storyJson.optString("subtitle", "Chapters in ${subject.displayName}")
            val readTime = storyJson.optInt("readTimeMinutes", 4)
            val wikiOverview = storyJson.optString("wikiOverview", "A structured Wikipedia-style overview of $topic in ${subject.displayName}.")

            val timelineJson = storyJson.optJSONArray("timelineSteps") ?: JSONArray()
            val timelineSteps = mutableListOf<String>()
            for (i in 0 until timelineJson.length()) {
                timelineSteps.add(timelineJson.getString(i))
            }

            val chaptersJson = storyJson.optJSONArray("chapters") ?: JSONArray()
            val chapters = mutableListOf<StoryChapter>()
            for (i in 0 until chaptersJson.length()) {
                val ch = chaptersJson.getJSONObject(i)
                val parasJson = ch.optJSONArray("paragraphs") ?: JSONArray()
                val paras = mutableListOf<String>()
                for (j in 0 until parasJson.length()) paras.add(parasJson.getString(j))
                chapters.add(
                    StoryChapter(
                        chapterNumber = ch.optInt("chapterNumber", i + 1),
                        title = ch.optString("title", "Chapter ${i + 1}"),
                        paragraphs = if (paras.isNotEmpty()) paras else listOf("A compelling inquiry into $topic."),
                        pullQuote = ch.optString("pullQuote", "")
                    )
                )
            }

            val vocabJson = storyJson.optJSONArray("vocabulary") ?: JSONArray()
            val vocab = mutableListOf<VocabularyWord>()
            for (i in 0 until vocabJson.length()) {
                val v = vocabJson.getJSONObject(i)
                vocab.add(
                    VocabularyWord(
                        word = v.optString("word", "Concept"),
                        partOfSpeech = v.optString("partOfSpeech", "noun"),
                        definition = v.optString("definition", "A fundamental property in ${subject.displayName}."),
                        contextSentence = v.optString("contextSentence", "This term underpins our modern understanding of $topic.")
                    )
                )
            }

            val quizJson = storyJson.optJSONArray("quizQuestions") ?: JSONArray()
            val quiz = mutableListOf<QuizQuestion>()
            for (i in 0 until quizJson.length()) {
                val q = quizJson.getJSONObject(i)
                val optsJson = q.optJSONArray("options") ?: JSONArray()
                val opts = mutableListOf<String>()
                for (j in 0 until optsJson.length()) opts.add(optsJson.getString(j))
                quiz.add(
                    QuizQuestion(
                        id = q.optInt("id", i + 1),
                        question = q.optString("question", "What is the key mechanism in $topic?"),
                        options = if (opts.isNotEmpty()) opts else listOf("Primary cause", "Secondary consequence", "Neutral factor"),
                        correctOptionIndex = q.optInt("correctOptionIndex", 0),
                        explanation = q.optString("explanation", "This principle governs the dynamics of $topic.")
                    )
                )
            }

            return BookStory(
                id = "ai_${System.currentTimeMillis()}",
                subject = subject,
                title = title,
                subtitle = subtitle,
                readTimeMinutes = readTime,
                chapters = if (chapters.isNotEmpty()) chapters else createSynthesizedStory(subject, topic).chapters,
                vocabulary = if (vocab.isNotEmpty()) vocab else createSynthesizedStory(subject, topic).vocabulary,
                quizQuestions = if (quiz.isNotEmpty()) quiz else createSynthesizedStory(subject, topic).quizQuestions,
                isAiGenerated = true,
                wikiOverview = wikiOverview,
                timelineSteps = if (timelineSteps.isNotEmpty()) timelineSteps else createSynthesizedStory(subject, topic).timelineSteps
            )
        } catch (e: Exception) {
            Log.e(TAG, "JSON parsing error, using synthesis: ${e.message}", e)
            return createSynthesizedStory(subject, topic)
        }
    }

    fun createSynthesizedStory(subject: Subject, topic: String): BookStory {
        val cleanTopic = topic.trim().ifBlank {
            when (subject) {
                Subject.HISTORY -> "World War II: The Secrets of Bletchley Park"
                Subject.SCIENCE -> "Plate Tectonics: Continents in Motion"
                Subject.ICT -> "Cybersecurity: Encryption & Keys"
            }
        }

        return when (subject) {
            Subject.HISTORY -> BookStory(
                id = "ai_history_${System.currentTimeMillis()}",
                subject = Subject.HISTORY,
                title = cleanTopic,
                subtitle = "Crucial Turning Points & The Geopolitics of Modern Society",
                readTimeMinutes = 4,
                wikiOverview = "A structured Wikipedia-style historical overview of $cleanTopic, examining ideological catalysts, key actors, treaties, and lasting societal impacts.",
                timelineSteps = listOf(
                    "Phase 1: Precursor conditions and mounting diplomatic/societal tensions.",
                    "Phase 2: Decisive turning point and major strategic campaigns.",
                    "Phase 3: Formal resolutions, treaties, and structural geopolitical reconfiguration."
                ),
                chapters = listOf(
                    StoryChapter(
                        chapterNumber = 1,
                        title = "The Crucible of Events",
                        paragraphs = listOf(
                            "Throughout human history, moments arise where decisions made in a matter of days permanently redefine global society. In the context of $cleanTopic, tensions that had mounted across decades reached an irreversible flashpoint.",
                            "Diplomacy gave way to decisive action as key historical actors mobilized populations, altered alliances, and tested the resilience of sovereign institutions under severe pressure."
                        ),
                        pullQuote = "“Those who cannot remember the past are condemned to repeat it.” — George Santayana"
                    ),
                    StoryChapter(
                        chapterNumber = 2,
                        title = "The Enduring Legacy",
                        paragraphs = listOf(
                            "The aftermath of $cleanTopic reshaped international borders, legal frameworks, and ethical norms. Modern treaties and democratic principles were born directly from the sacrifices and miscalculations of this period.",
                            "Analyzing these historical chapters provides a mirror through which we evaluate contemporary challenges in governance, diplomacy, and human rights."
                        ),
                        pullQuote = "“History is a relentless cycle of human ambition, ideology, and resilience.”"
                    )
                ),
                vocabulary = listOf(
                    VocabularyWord("Armistice", "noun", "An agreement made by opposing sides in a war to stop fighting for a certain time.", "The declaration of an armistice signaled the end of hostilities across the front lines."),
                    VocabularyWord("Hegemony", "noun", "Leadership or dominance, especially by one country or social group over others.", "Nations formed coalitions to prevent imperial hegemony from destabilizing the region."),
                    VocabularyWord("Ratification", "noun", "The action of signing or giving formal consent to a treaty, contract, or agreement.", "Congressional ratification of the post-war peace treaty proved contentious."),
                    VocabularyWord("Ideology", "noun", "A system of ideas and ideals, especially one which forms the basis of economic or political theory.", "Competing political ideologies clashed across twentieth-century battlefields.")
                ),
                quizQuestions = listOf(
                    QuizQuestion(
                        id = 1,
                        question = "What primary lesson does modern historical analysis draw from $cleanTopic?",
                        options = listOf(
                            "Diplomatic alliances and institutional stability are vital to preventing systemic conflict",
                            "Historical events happen strictly at random without precedent",
                            "Treaties should never be written on paper"
                        ),
                        correctOptionIndex = 0,
                        explanation = "Historical analysis demonstrates that institutional balance and proactive diplomacy are paramount in preventing catastrophe."
                    ),
                    QuizQuestion(
                        id = 2,
                        question = "What is an 'armistice' in military and political history?",
                        options = listOf(
                            "A formal agreement to halt armed hostilities",
                            "A declaration of endless combat",
                            "The election of a new monarch"
                        ),
                        correctOptionIndex = 0,
                        explanation = "An armistice is a truce or ceasefire between opposing armed forces."
                    ),
                    QuizQuestion(
                        id = 3,
                        question = "How did post-war consequences typically reshape participating states?",
                        options = listOf(
                            "By redrawing borders and prompting new international legal frameworks",
                            "By freezing all technological development permanently",
                            "By eliminating all human languages"
                        ),
                        correctOptionIndex = 0,
                        explanation = "Major historical conflicts regularly resulted in redrawn geopolitical boundaries and modern treaty frameworks."
                    )
                ),
                isAiGenerated = true
            )

            Subject.SCIENCE -> BookStory(
                id = "ai_science_${System.currentTimeMillis()}",
                subject = Subject.SCIENCE,
                title = cleanTopic,
                subtitle = "Empirical Observation, Natural Laws & The Mechanics of Matter",
                readTimeMinutes = 4,
                wikiOverview = "A structured scientific overview of $cleanTopic, analyzing governing thermodynamic equations, experimental hypotheses, and technological applications.",
                timelineSteps = listOf(
                    "Principle 1: Fundamental physical and chemical properties.",
                    "Principle 2: Laboratory experimental verification and quantitative laws.",
                    "Principle 3: Real-world engineering implementations and technological systems."
                ),
                chapters = listOf(
                    StoryChapter(
                        chapterNumber = 1,
                        title = "The Guiding Physical Laws",
                        paragraphs = listOf(
                            "At the heart of $cleanTopic lies an elegant interplay of fundamental forces. Whether governing macroscopic planetary systems or microscopic molecular bonds, nature adheres strictly to conservation laws and thermodynamics.",
                            "Scientific inquiry into this phenomenon required groundbreaking laboratory experiments, moving our understanding away from medieval alchemy toward rigorous quantitative physics."
                        ),
                        pullQuote = "“The most incomprehensible thing about the universe is that it is comprehensible.” — Albert Einstein"
                    ),
                    StoryChapter(
                        chapterNumber = 2,
                        title = "Systemic Equilibrium & Discovery",
                        paragraphs = listOf(
                            "When variables in $cleanTopic are pushed toward extreme boundary conditions, systems undergo phase transitions or dynamic stabilization. Understanding these equilibrium points allows engineers to predict real-world outcomes.",
                            "From climate prediction to material science, the principles discovered in $cleanTopic remain integral to twenty-first-century technological advancement."
                        ),
                        pullQuote = "“Somewhere, something incredible is waiting to be known.” — Carl Sagan"
                    )
                ),
                vocabulary = listOf(
                    VocabularyWord("Equilibrium", "noun", "A state in which opposing forces or influences are balanced.", "Chemical equilibrium occurs when forward and reverse reaction rates match."),
                    VocabularyWord("Thermodynamics", "noun", "The branch of physical science that deals with the relations between heat and other forms of energy.", "The second law of thermodynamics explains spontaneous energy dissipation."),
                    VocabularyWord("Catalyst", "noun", "A substance that increases the rate of a chemical reaction without itself undergoing any permanent change.", "Enzymes act as biological catalysts lowering activation energy in cellular reactions."),
                    VocabularyWord("Kinetic", "adjective", "Relating to or resulting from motion.", "As thermal energy increases, molecular kinetic velocity accelerates proportionally.")
                ),
                quizQuestions = listOf(
                    QuizQuestion(
                        id = 1,
                        question = "What fundamental characteristic defines scientific equilibrium in $cleanTopic?",
                        options = listOf(
                            "Opposing physical or chemical forces are dynamically balanced",
                            "All particles cease to exist completely",
                            "Temperature drops below absolute zero"
                        ),
                        correctOptionIndex = 0,
                        explanation = "Equilibrium represents a state of dynamic balance where opposing rates or forces cancel out."
                    ),
                    QuizQuestion(
                        id = 2,
                        question = "What is the function of a catalyst in physical and chemical reactions?",
                        options = listOf(
                            "It lowers activation energy to speed up reaction rates without being consumed",
                            "It cools the entire planet instantly",
                            "It creates new elements from nothing"
                        ),
                        correctOptionIndex = 0,
                        explanation = "Catalysts accelerate reactions by offering an alternative pathway with lower activation barrier."
                    ),
                    QuizQuestion(
                        id = 3,
                        question = "Why is empirical testing essential in studying $cleanTopic?",
                        options = listOf(
                            "To verify theoretical hypotheses with repeatable observational data",
                            "To make textbooks more expensive",
                            "Because science forbids mathematical formulas"
                        ),
                        correctOptionIndex = 0,
                        explanation = "The scientific method relies strictly on empirical observation to validate theoretical models."
                    )
                ),
                isAiGenerated = true
            )

            Subject.ICT -> BookStory(
                id = "ai_ict_${System.currentTimeMillis()}",
                subject = Subject.ICT,
                title = cleanTopic,
                subtitle = "Computational Architectures, Logic & The Global Information Grid",
                readTimeMinutes = 4,
                wikiOverview = "A structured computer science overview of $cleanTopic, covering system architectures, algorithmic complexity, networking protocols, and cyber resilience.",
                timelineSteps = listOf(
                    "Architecture: Hardware abstractions, silicon microarchitectures, and binary instruction sets.",
                    "Protocol Stack: Network transmission layers, data packet routing, and client-server transactions.",
                    "Security & Scale: Cryptographic cipher protection, distributed databases, and high-availability operations."
                ),
                chapters = listOf(
                    StoryChapter(
                        chapterNumber = 1,
                        title = "The Architecture of Compute",
                        paragraphs = listOf(
                            "In the realm of computer science, $cleanTopic represents a foundational triumph of engineering abstraction. Computing systems take complex data problems and decompose them into manageable, deterministic instructions.",
                            "From discrete mathematics to low-level assembly, the principles governing $cleanTopic dictate how billions of operations execute per second across modern silicon chips."
                        ),
                        pullQuote = "“Simplicity is prerequisite for reliability.” — Edsger W. Dijkstra"
                    ),
                    StoryChapter(
                        chapterNumber = 2,
                        title = "Scaling & Networked Security",
                        paragraphs = listOf(
                            "As networks expanded to connect billions of concurrent users, systems engineered around $cleanTopic had to confront scaling constraints, latency limits, and malicious security threats.",
                            "Modern cryptographic algorithms and distributed consensus protocols ensure that data remains intact, verifiable, and protected across untrusted public networks."
                        ),
                        pullQuote = "“Code is poetry executed by silicon.”"
                    )
                ),
                vocabulary = listOf(
                    VocabularyWord("Cryptography", "noun", "The art and science of writing or solving codes to secure communication.", "Asymmetric cryptography protects data transmitted over open public networks."),
                    VocabularyWord("Algorithm", "noun", "A process or set of rules to be followed in calculations or problem-solving operations.", "Efficient sorting algorithms reduce computation time from hours to milliseconds."),
                    VocabularyWord("Bandwidth", "noun", "The maximum amount of data transmitted over an internet connection in a given amount of time.", "High-bandwidth fiber links prevent network congestion during peak hours."),
                    VocabularyWord("Concurrency", "noun", "The ability of different parts or units of a program to be executed out-of-order without affecting final outcome.", "Modern multi-core processors leverage concurrency for high-throughput computing.")
                ),
                quizQuestions = listOf(
                    QuizQuestion(
                        id = 1,
                        question = "What is the primary role of modern cryptography in $cleanTopic?",
                        options = listOf(
                            "Securing communication and verifying data authenticity over untrusted networks",
                            "Speeding up internet cables using physical oil",
                            "Deleting computer memory automatically"
                        ),
                        correctOptionIndex = 0,
                        explanation = "Cryptography secures digital communications through encryption and cryptographic hashes."
                    ),
                    QuizQuestion(
                        id = 2,
                        question = "What defines an algorithm in computer studies?",
                        options = listOf(
                            "A finite sequence of rigorous instructions to solve a specific computational problem",
                            "A physical piece of copper wire",
                            "A monitor that shows bright lights"
                        ),
                        correctOptionIndex = 0,
                        explanation = "Algorithms are deterministic sets of steps that transform inputs into desired outputs."
                    ),
                    QuizQuestion(
                        id = 3,
                        question = "Why is concurrency critical in modern multi-core computing?",
                        options = listOf(
                            "It enables multiple tasks to make progress simultaneously across cores",
                            "It converts computer chips into steam",
                            "It prevents monitors from displaying text"
                        ),
                        correctOptionIndex = 0,
                        explanation = "Concurrency enables software to utilize multiple processor cores concurrently to process large workloads."
                    )
                ),
                isAiGenerated = true
            )
        }
    }
}
