package com.example.data

import com.example.model.BookStory
import com.example.model.GradeTier
import com.example.model.QuizQuestion
import com.example.model.StoryChapter
import com.example.model.Subject
import com.example.model.VocabularyWord

/**
 * Exhaustive SSC & Grade-by-Grade Curriculum Hub for Learnicle:
 * Covers History, Science, and ICT across Grades 1-12 and SSC Board Preparation Mode.
 */
object CurriculumSyllabusData {

    val sscAndGradeStories: List<BookStory> = listOf(
        // =========================================================================
        // HISTORY: SSC / GRADES 9-12 (Exhaustive Topics)
        // Ancient Bengal, Mughal Empire, British Rule & 1947, Language Movement (1952),
        // 6-Point Movement (1966), Liberation War (1971), Industrial Revolution, WWI & WWII, Cold War.
        // =========================================================================
        BookStory(
            id = "history_bangladesh_1971",
            subject = Subject.HISTORY,
            gradeTier = GradeTier.SECONDARY_SSC,
            isSscSpecial = true,
            title = "Bangladesh Liberation War (1971) & National Sovereignty",
            subtitle = "SSC Board Exam Core: Operation Searchlight, Mukti Bahini & Victory on 16 December",
            readTimeMinutes = 5,
            wikiOverview = "The Bangladesh Liberation War in 1971 was an armed conflict sparked by the rise of Bengali nationalist self-determination in East Pakistan, culminating in the birth of the sovereign People's Republic of Bangladesh.",
            timelineSteps = listOf(
                "March 7, 1971: Historic speech at Racecourse Ground declaring the struggle for emancipation and independence.",
                "March 25, 1971: Pakistani military launches brutal Operation Searchlight against civilians and intellectuals.",
                "March 26, 1971: Proclamation of Independence transmitted across the nation.",
                "April 17, 1971: Mujibnagar Government sworn in, coordinating wartime administration and diplomacy.",
                "December 3–16, 1971: Joint command of Mukti Bahini and Indian Armed Forces secures unconditional Pakistani surrender."
            ),
            chapters = listOf(
                StoryChapter(
                    chapterNumber = 1,
                    title = "The Roots of Resistance & The Historic March 7 Declaration",
                    paragraphs = listOf(
                        "Following partition in 1947, political disenfranchisement, economic disparity, and linguistic suppression alienated the Bengali majority of East Pakistan from the West Pakistani ruling elite.",
                        "When the military junta nullified the democratic outcome of the 1970 general elections, millions assembled at the Dhaka Racecourse Ground on March 7, 1971. The proclamation galvanized the entire nation into total civil disobedience.",
                        "On the tragic night of March 25, Pakistani forces initiated Operation Searchlight, unleashing widespread atrocities across universities, student dormitories, and urban centers to crush resistance."
                    ),
                    pullQuote = "“The struggle this time is a struggle for our emancipation. The struggle this time is a struggle for our independence!”"
                ),
                StoryChapter(
                    chapterNumber = 2,
                    title = "The Mukti Bahini Guerrilla Campaign & Ultimate Triumph",
                    paragraphs = listOf(
                        "The Mukti Bahini (Freedom Fighters)—comprising Bengali regular army officers, university students, peasants, and women—established eleven operational war sectors, waging relentless asymmetric guerrilla warfare.",
                        "Through courage and widespread popular support, freedom fighters severed occupation supply lines and liberated vast rural enclaves throughout monsoon seasons.",
                        "On December 16, 1971, the joint command achieved total victory as Lieutenant General A.A.K. Niazi signed the Instrument of Surrender in Dhaka, welcoming Bangladesh into the community of sovereign nations."
                    ),
                    pullQuote = "“December 16 marks the timeless triumph of human dignity, justice, and sovereign self-determination.”"
                )
            ),
            vocabulary = listOf(
                VocabularyWord("Sovereignty", "noun", "The full right and power of a governing body over itself, without external interference.", "The victory in 1971 established sovereign authority for the people of Bangladesh."),
                VocabularyWord("Disenfranchisement", "noun", "The state of being deprived of a right or privilege, especially the right to vote or self-govern.", "Economic disenfranchisement spurred widespread demands for democratic regional autonomy."),
                VocabularyWord("Asymmetric Warfare", "noun", "War between belligerents whose relative military power or strategy differs significantly.", "The Mukti Bahini utilized asymmetric guerrilla strikes to paralyze armored columns."),
                VocabularyWord("Instrument of Surrender", "noun", "A legal written pact signifying unconditional military capitulation.", "The Instrument of Surrender was formally signed in Dhaka on December 16, 1971."),
                VocabularyWord("Emancipation", "noun", "The process of giving people social, political, or economic rights and freedom.", "The March 7 speech articulated a timeless message of national emancipation.")
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    id = 1,
                    question = "On which date was the historic declaration of independence speech delivered at the Racecourse Ground?",
                    options = listOf("March 7, 1971", "February 21, 1952", "December 16, 1971", "March 26, 1970"),
                    correctOptionIndex = 0,
                    explanation = "On March 7, 1971, the historic address called for nationwide preparation for liberation and non-cooperation with military authorities."
                ),
                QuizQuestion(
                    id = 2,
                    question = "What was the codename of the brutal Pakistani military offensive launched on the night of March 25, 1971?",
                    options = listOf("Operation Searchlight", "Operation Desert Storm", "Operation Overlord", "Operation Gibraltar"),
                    correctOptionIndex = 0,
                    explanation = "Operation Searchlight was the planned military crackdown intended to curb Bengali nationalist resistance by force."
                ),
                QuizQuestion(
                    id = 3,
                    question = "Into how many operational military sectors was Bangladesh divided during the 1971 Liberation War?",
                    options = listOf("11 Sectors", "4 Sectors", "25 Sectors", "7 Sectors"),
                    correctOptionIndex = 0,
                    explanation = "The provisional wartime administration organized the country into 11 distinct military sectors led by sector commanders."
                ),
                QuizQuestion(
                    id = 4,
                    question = "Where was the provisional government (Mujibnagar Government) formally sworn in on April 17, 1971?",
                    options = listOf("Baidyanathtala (Meherpur)", "Chittagong Port", "Sylhet Tea Gardens", "Comilla Cantonment"),
                    correctOptionIndex = 0,
                    explanation = "The first sovereign government of Bangladesh took its solemn oath of office at Baidyanathtala, Meherpur, renamed Mujibnagar."
                ),
                QuizQuestion(
                    id = 5,
                    question = "What national milestone is celebrated annually on December 16 in Bangladesh?",
                    options = listOf("Victory Day (Bijoy Dibosh)", "Language Martyrs' Day", "May Day", "Independence Declaration Day"),
                    correctOptionIndex = 0,
                    explanation = "December 16 commemorates the unconditional surrender of Pakistani occupation forces and the ultimate victory in 1971."
                )
            )
        ),

        BookStory(
            id = "history_language_and_6point",
            subject = Subject.HISTORY,
            gradeTier = GradeTier.SECONDARY_SSC,
            isSscSpecial = true,
            title = "Language Movement (1952) & The 6-Point Charter (1966)",
            subtitle = "SSC Board Exam Core: Cultural Identity, Mother Tongue Day & Autonomy Charter",
            readTimeMinutes = 5,
            wikiOverview = "The Language Movement of 1952 established Bengali as a state language, laying the bedrock for cultural nationalism that culminated in the 6-Point Movement of 1966, known as the Magna Carta of Bengali emancipation.",
            timelineSteps = listOf(
                "1948: Muhammad Ali Jinnah declares Urdu alone as the state language of Pakistan, triggering mass student dissent.",
                "February 21, 1952: Police fire upon student demonstrators on Dhaka University campus; Salam, Barkat, Rafiq, Jabbar, and Shafiur are martyred.",
                "1956: The constitution of Pakistan formally recognizes Bengali as a state language alongside Urdu.",
                "1966: Announcement of the 6-Point Movement in Lahore demanding complete provincial autonomy and fiscal self-control.",
                "1999: UNESCO proclaims February 21 as International Mother Language Day worldwide."
            ),
            chapters = listOf(
                StoryChapter(
                    chapterNumber = 1,
                    title = "The Sacrifices of Ekushey February 1952",
                    paragraphs = listOf(
                        "Language is the profound reservoir of human consciousness, cultural dignity, and democratic rights. When state authorities attempted to impose Urdu as the sole national language upon a 56% Bengali majority, students in Dhaka took to the streets in defiance of Section 144 curfew orders.",
                        "On February 21, 1952, police opened direct fire outside the Dhaka Medical College premises. The martyrdom of Barkat, Rafiq, Salam, Jabbar, and countless others galvanized an unbreakable national identity.",
                        "Their supreme sacrifice won Bengali official state language status in 1956 and later inspired the United Nations (UNESCO) to designate February 21 as International Mother Language Day."
                    ),
                    pullQuote = "“Can I forget the twenty-first of February, bathed in the blood of my brothers?” — Abdul Gaffar Choudhury"
                ),
                StoryChapter(
                    chapterNumber = 2,
                    title = "The 6-Point Movement: The Magna Carta of Emancipation",
                    paragraphs = listOf(
                        "By 1966, sustained economic exploitation had drained resources from East Bengal to finance projects in West Pakistan. The 6-Point Charter demanded a federal parliamentary system, two separate currencies or regional fiscal oversight, autonomous tax collection, and regional paramilitary forces.",
                        "The charter became an unstoppable political tsunami, demonstrating that cultural resistance in 1952 had evolved into an articulate constitutional demand for total political and economic liberation."
                    ),
                    pullQuote = "“The 6 points constituted the authentic charter of life and death for our disenfranchised people.”"
                )
            ),
            vocabulary = listOf(
                VocabularyWord("Autonomy", "noun", "The right or condition of self-government, especially in a regional territory.", "The 6-Point charter demanded complete administrative and economic autonomy."),
                VocabularyWord("Linguistic Nationalism", "noun", "A form of nationalism where the shared mother tongue forms the core identity of the people.", "Linguistic nationalism in 1952 challenged religious dogma as the sole basis of nationhood."),
                VocabularyWord("Magna Carta", "noun", "A foundational charter establishing essential civil rights and limiting arbitrary sovereign authority.", "Historians consider the 1966 Six Points the Magna Carta of Bengali self-rule."),
                VocabularyWord("Section 144", "noun", "A legal decree empowering magistrates to prohibit gatherings of four or more people.", "Defying Section 144, courageous students marched toward the legislative assembly in 1952."),
                VocabularyWord("Martyrdom", "noun", "The death or suffering of a martyr who sacrifices life for a sacred cause.", "The martyrdom of Ekushey ignited universal pride in mother tongues.")
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    id = 1,
                    question = "Which international agency proclaimed February 21 as International Mother Language Day in 1999?",
                    options = listOf("UNESCO", "WHO", "UNICEF", "IMF"),
                    correctOptionIndex = 0,
                    explanation = "In November 1999, UNESCO declared February 21 as International Mother Language Day to promote linguistic diversity."
                ),
                QuizQuestion(
                    id = 2,
                    question = "In what year was the historic 6-Point Movement for provincial autonomy formally declared?",
                    options = listOf("1966", "1952", "1971", "1947"),
                    correctOptionIndex = 0,
                    explanation = "The 6-Point program was announced in February 1966 at a national conference of opposition parties in Lahore."
                ),
                QuizQuestion(
                    id = 3,
                    question = "Which constitutional year in Pakistan saw Bengali officially recognized as a joint state language?",
                    options = listOf("1956", "1948", "1969", "1972"),
                    correctOptionIndex = 0,
                    explanation = "Under intense public pressure following the 1952 movement, the 1956 Constitution formalized Bengali alongside Urdu."
                ),
                QuizQuestion(
                    id = 4,
                    question = "What monetary mechanism did Point 3 of the 6-Point charter propose to stop capital flight from East Bengal?",
                    options = listOf("Two separate but freely convertible currencies or separate regional reserve banks", "Replacing all currency with gold coins", "Banning paper money", "Adopting the British Pound"),
                    correctOptionIndex = 0,
                    explanation = "Point 3 proposed either two separate freely convertible currencies or a single currency with distinct regional accounting to halt capital flight."
                ),
                QuizQuestion(
                    id = 5,
                    question = "Who composed the lyrics of the immortal Ekushey anthem 'Amar Bhaier Rokte Rangano'?",
                    options = listOf("Abdul Gaffar Choudhury", "Kazi Nazrul Islam", "Rabindranath Tagore", "Jasimuddin"),
                    correctOptionIndex = 0,
                    explanation = "Journalist and author Abdul Gaffar Choudhury penned the immortal lyrics in the hospital after visiting wounded students."
                )
            )
        ),

        BookStory(
            id = "history_ancient_bengal_mughal",
            subject = Subject.HISTORY,
            gradeTier = GradeTier.SECONDARY_SSC,
            isSscSpecial = true,
            title = "Ancient Bengal, Pala-Sena Dynasties & The Mughal Subah",
            subtitle = "SSC Board Exam Core: Mahasthangarh, Somapura Mahavihara & Subah Bangla",
            readTimeMinutes = 5,
            wikiOverview = "Ancient Bengal (Vanga, Pundra, Gauda) evolved from Buddhist and Hindu classical empires into 'Subah Bangla', the richest province of the Mughal Empire known globally as the Paradise of Nations.",
            timelineSteps = listOf(
                "3rd Century BCE: Pundranagara (Mahasthangarh) thrives as a flourishing regional administrative and trading hub.",
                "8th Century CE: Gopala establishes the Pala Empire, founding Somapura Mahavihara at Paharpur.",
                "12th Century CE: The Sena Dynasty patrons Sanskrit literature and administrative centralization.",
                "1576 CE: Battle of Rajmahal incorporates Bengal into the Mughal Empire as Subah Bangla.",
                "17th Century: Dhaka established as Mughal provincial capital under Subahdar Islam Khan Chisti, famed for exquisite muslin textiles."
            ),
            chapters = listOf(
                StoryChapter(
                    chapterNumber = 1,
                    title = "The Classical Era of Pundra, Pala, and Somapura Mahavihara",
                    paragraphs = listOf(
                        "The fertile delta formed by the Padma, Meghna, and Jamuna rivers nurtured human civilizations from ancient antiquity. Archaeological excavations at Mahasthangarh reveal fortified brick citadels, terracotta plaques, and Brahmi inscriptions dating to the Mauryan epoch.",
                        "Under the four-century rule of the Buddhist Pala Empire, Bengal became a world center of art, maritime commerce, and scholarship. Dharmapala constructed Somapura Mahavihara at Paharpur, a UNESCO World Heritage monument whose monumental cruciform plan influenced temple designs across Southeast Asia.",
                        "By the twelfth century, the Sena dynasty consolidated classical Sanskrit traditions, temple architecture, and administrative standardization across the delta."
                    ),
                    pullQuote = "“The Pala dynasty united the delta under an enlightened golden age of Buddhist art and international scholarship.”"
                ),
                StoryChapter(
                    chapterNumber = 2,
                    title = "Subah Bangla: The Wealthiest Jewel of the Mughal Empire",
                    paragraphs = listOf(
                        "In 1576, following the submission of the independent Sultanate, Emperor Akbar incorporated the delta into the Mughal realm as Subah Bangla. In 1610, Subahdar Islam Khan transferred the capital to Dhaka, christening it Jahangirnagar.",
                        "Subah Bangla generated immense agricultural wealth through irrigated rice paddies and became the global epicenter of fine muslin silk weaving, shipbuilding, and maritime trade across the Indian Ocean.",
                        "European travelers like François Bernier described Bengal as possessing an overflowing abundance of provisions, grains, silks, and cottons surpassing Egypt."
                    ),
                    pullQuote = "“Subah Bangla was hailed across the eastern world as Jannat-ul-Bilad: The Paradise of Nations.”"
                )
            ),
            vocabulary = listOf(
                VocabularyWord("Subah", "noun", "A province or major administrative territory within the Mughal Empire.", "Subah Bangla contributed significant revenue to the central Mughal treasury."),
                VocabularyWord("Mahavihara", "noun", "A great Buddhist university or monastic educational complex.", "Somapura Mahavihara attracted visiting scholars from Tibet, China, and Sri Lanka."),
                VocabularyWord("Cruciform", "adjective", "Having the shape of a cross.", "The central temple at Paharpur features an elaborate four-armed cruciform architectural plan."),
                VocabularyWord("Muslin", "noun", "Delicately woven lightweight cotton fabric of supreme fineness.", "Dhaka muslin was prized in royal European courts for its whisper-light translucency."),
                VocabularyWord("Pundranagara", "noun", "The ancient urban capital of Pundravardhana, located at modern Mahasthangarh, Bogura.", "Pundranagara was one of the earliest fortified urban settlements in the Bengal delta.")
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    id = 1,
                    question = "Which UNESCO World Heritage site was built by the Pala Emperor Dharmapala in modern Naogaon, Bangladesh?",
                    options = listOf("Somapura Mahavihara (Paharpur)", "Lalbagh Fort", "Ahsan Manzil", "Sixty Dome Mosque"),
                    correctOptionIndex = 0,
                    explanation = "Somapura Mahavihara at Paharpur was founded by Emperor Dharmapala and is among the greatest Buddhist viharas in South Asia."
                ),
                QuizQuestion(
                    id = 2,
                    question = "What ancient capital was located at modern Mahasthangarh in Bogura?",
                    options = listOf("Pundranagara", "Sonargaon", "Bikrampur", "Gaur"),
                    correctOptionIndex = 0,
                    explanation = "Mahasthangarh preserves the monumental fortified brick ramparts of ancient Pundranagara."
                ),
                QuizQuestion(
                    id = 3,
                    question = "Which Mughal Subahdar transferred the provincial capital of Bengal to Dhaka in 1610?",
                    options = listOf("Islam Khan Chisti", "Mir Jumla", "Shaista Khan", "Man Singh"),
                    correctOptionIndex = 0,
                    explanation = "Islam Khan Chisti moved the capital from Rajmahal to Dhaka in 1610, establishing administrative control across the delta."
                ),
                QuizQuestion(
                    id = 4,
                    question = "What title did Mughal Emperor Aurangzeb bestow on Bengal due to its immense wealth and fertility?",
                    options = listOf("Jannat-ul-Bilad (Paradise of Nations)", "Land of Snow", "Golden Horn", "Desert Jewel"),
                    correctOptionIndex = 0,
                    explanation = "Subah Bangla was hailed as Jannat-ul-Bilad due to its unmatched agricultural output and artisan manufacturing."
                ),
                QuizQuestion(
                    id = 5,
                    question = "Which famous textile manufactured in Dhaka was celebrated worldwide for its supreme softness and weave?",
                    options = listOf("Muslin", "Denim", "Corduroy", "Tweed"),
                    correctOptionIndex = 0,
                    explanation = "Dhaka muslin woven from local phuti karpas cotton was acclaimed worldwide for its unmatched delicate weave."
                )
            )
        ),

        BookStory(
            id = "history_world_wars_cold_war",
            subject = Subject.HISTORY,
            gradeTier = GradeTier.SECONDARY_SSC,
            isSscSpecial = true,
            title = "World Wars, The Industrial Revolution & The Cold War",
            subtitle = "SSC Board Exam Core: Industrial Modernity, Treaty of Versailles & Nuclear Deterrence",
            readTimeMinutes = 5,
            wikiOverview = "The transition from the 18th-century Industrial Revolution through World War I, World War II, and the Cold War defined modern geopolitics, global institutions, and technological globalization.",
            timelineSteps = listOf(
                "1760–1840: Steam engines and mechanized factories drive the Industrial Revolution in Great Britain.",
                "1914–1918: World War I brings trench warfare, industrial armaments, and collapse of classical European empires.",
                "1939–1945: World War II spans the globe, defeating Axis fascism and prompting the birth of the United Nations (1945).",
                "1947–1991: The Cold War divides the world between NATO and the Warsaw Pact in an ideological superpower standoff.",
                "1989–1991: Fall of the Berlin Wall and dissolution of the Soviet Union mark the end of the Cold War order."
            ),
            chapters = listOf(
                StoryChapter(
                    chapterNumber = 1,
                    title = "The Steam Engine & The Calamity of Total Industrial War",
                    paragraphs = listOf(
                        "The advent of James Watt's efficient steam engine unleashed unprecedented industrial production, transforming agrarian pastoral communities into dense manufacturing metropolises across the 18th and 19th centuries.",
                        "However, industrial technology also modernized weaponry: machine guns, artillery, poison gas, and tanks turned World War I (1914–1918) into a catastrophic war of attrition in European trenches.",
                        "Unresolved grievances codified in the Treaty of Versailles, combined with economic collapse in the Great Depression, catalyzed the rise of totalitarian fascism, precipitating World War II (1939–1945) and the horror of the Holocaust."
                    ),
                    pullQuote = "“The industrial age gifted humanity mastery over nature, but challenged its moral capacity to survive its own weapons.”"
                ),
                StoryChapter(
                    chapterNumber = 2,
                    title = "The Cold War: Bipolarity, Proxy Conflicts & Space Race",
                    paragraphs = listOf(
                        "Following the atomic bombings of Hiroshima and Nagasaki in 1945, the international system consolidated into a bipolar standoff between the capitalist United States and the communist Soviet Union.",
                        "Though Mutually Assured Destruction (MAD) prevented direct nuclear exchange between superpowers, ferocious proxy wars erupted in Korea, Vietnam, and Afghanistan.",
                        "The geopolitical rivalry also catalyzed monumental scientific achievements—propelling human space exploration from Sputnik in 1957 to the Apollo 11 lunar landing in 1969—before the collapse of the Berlin Wall in 1989 concluded the era."
                    ),
                    pullQuote = "“A nuclear war cannot be won and must never be fought.” — Geneva Superpower Accord"
                )
            ),
            vocabulary = listOf(
                VocabularyWord("Bipolarity", "noun", "A distribution of global power in which two superpower states possess preponderant influence.", "The Cold War era was defined by bipolarity between Washington and Moscow."),
                VocabularyWord("Totalitarianism", "noun", "A system of government that is centralized and dictatorial and requires complete subservience to the state.", "Totalitarian regimes utilized state propaganda and terror to repress civilian freedoms."),
                VocabularyWord("Mutually Assured Destruction", "noun", "A doctrine of military strategy in which a full-scale use of nuclear weapons would cause complete annihilation of both attacker and defender.", "MAD ensured strategic nuclear deterrence throughout the mid-twentieth century."),
                VocabularyWord("Attrition", "noun", "The process of gradually reducing the strength or effectiveness of someone through sustained attack.", "Trench warfare on the Western Front devolved into an agonizing stalemate of industrial attrition."),
                VocabularyWord("Treaty of Versailles", "noun", "The 1919 peace document signed at the end of World War I imposing reparations on Germany.", "Punitive economic clauses in the Versailles treaty destabilized European post-war recovery.")
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    id = 1,
                    question = "Which invention served as the prime mechanical catalyst of the 18th-century Industrial Revolution?",
                    options = listOf("Watt's Steam Engine", "The Smartphone", "Solar Panels", "The Jet Engine"),
                    correctOptionIndex = 0,
                    explanation = "James Watt's steam engine converted heat energy into mechanical rotary power, revolutionizing manufacturing and railways."
                ),
                QuizQuestion(
                    id = 2,
                    question = "What international peace organization was founded in 1945 immediately after World War II?",
                    options = listOf("The United Nations (UN)", "The League of Nations", "Interpol", "OPEC"),
                    correctOptionIndex = 0,
                    explanation = "The United Nations was created in 1945 to foster international diplomacy, prevent future world wars, and protect human rights."
                ),
                QuizQuestion(
                    id = 3,
                    question = "What strategic concept prevented direct military combat between the US and the USSR during the Cold War?",
                    options = listOf("Mutually Assured Destruction (MAD)", "A total ban on submarines", "Lack of radio communication", "Desert storms"),
                    correctOptionIndex = 0,
                    explanation = "The realization that a nuclear exchange would guarantee the total annihilation of both adversaries maintained deterrence."
                ),
                QuizQuestion(
                    id = 4,
                    question = "Which landmark event in November 1989 symbolized the beginning of the end of the Cold War in Europe?",
                    options = listOf("The Fall of the Berlin Wall", "The Boston Tea Party", "The storming of the Bastille", "The Battle of Waterloo"),
                    correctOptionIndex = 0,
                    explanation = "The demolition of the Berlin Wall in November 1989 reunited Germany and signaled the collapse of Soviet-backed regimes."
                ),
                QuizQuestion(
                    id = 5,
                    question = "In what year did the Apollo 11 spacecraft land the first humans on the Moon during the Cold War Space Race?",
                    options = listOf("1969", "1957", "1980", "1945"),
                    correctOptionIndex = 0,
                    explanation = "On July 20, 1969, Neil Armstrong and Buzz Aldrin landed the Apollo 11 lunar module on the Moon."
                )
            )
        ),

        // =========================================================================
        // HISTORY: GRADES 1-8 (Foundations & Intermediate)
        // Local Communities, Heritage Sites, Early Civilizations
        // =========================================================================
        BookStory(
            id = "history_grade_1_5_communities",
            subject = Subject.HISTORY,
            gradeTier = GradeTier.PRIMARY,
            isSscSpecial = false,
            title = "Local Communities, Families & Living History",
            subtitle = "Grades 1–5 History Unit: Neighborhoods, Oral Traditions & Cultural Festivals",
            readTimeMinutes = 3,
            wikiOverview = "History begins at home: studying our families, neighborhoods, and traditions helps us understand how daily human life evolved across generations.",
            timelineSteps = listOf(
                "Step 1: Discovering our family tree and listening to stories from grandparents.",
                "Step 2: Exploring how homes, transportation, and markets changed over 100 years.",
                "Step 3: Celebrating local festivals that unite diverse cultural communities."
            ),
            chapters = listOf(
                StoryChapter(
                    chapterNumber = 1,
                    title = "How Communities Grow and Change Over Time",
                    paragraphs = listOf(
                        "Every town, village, and neighborhood has a fascinating story. Long ago, people traveled on foot or by horse carts, drew water from community wells, and gathered in open bazaars.",
                        "Over decades, schools were built, roads were paved with asphalt, and electricity lit up city streets. Preserving historical buildings teaches us to appreciate the hard work of generations before us."
                    ),
                    pullQuote = "“To understand the world, first look closely at the story of your own neighborhood.”"
                )
            ),
            vocabulary = listOf(
                VocabularyWord("Tradition", "noun", "A custom or belief passed down from generation to generation.", "Sharing folk songs during harvest festivals is a cherished cultural tradition."),
                VocabularyWord("Heritage", "noun", "Valued objects and qualities such as historic buildings and culture passed down from previous eras.", "Ancient clay pottery is part of our national artistic heritage.")
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    id = 1,
                    question = "Why do historians study old photographs, letters, and tools from our communities?",
                    options = listOf("To learn how people lived, worked, and solved problems in the past", "To hide them in wooden boxes", "Because they dislike modern books"),
                    correctOptionIndex = 0,
                    explanation = "Artifacts and old photographs provide primary evidence of past daily life."
                ),
                QuizQuestion(
                    id = 2,
                    question = "What does a family tree illustrate?",
                    options = listOf("Relationships across generations of ancestors and children", "How tall pine trees grow in gardens", "A list of food recipes"),
                    correctOptionIndex = 0,
                    explanation = "A family tree charts ancestral relationships connecting parents, grandparents, and children."
                ),
                QuizQuestion(
                    id = 3,
                    question = "Which of the following is an example of intangible cultural heritage?",
                    options = listOf("Folk stories, traditional music, and seasonal celebrations", "A modern plastic toy", "A television set"),
                    correctOptionIndex = 0,
                    explanation = "Folk stories and celebrations represent cultural wisdom passed verbally down generations."
                )
            )
        ),

        // =========================================================================
        // SCIENCE: SSC / GRADES 9-12 (Exhaustive Topics)
        // Motion & Forces, Work & Energy, Wave & Sound, Electricity & Magnetism,
        // Atomic Structure, Chemical Bonding, Acids & Bases, Cell Division, Genetics & DNA
        // =========================================================================
        BookStory(
            id = "science_motion_forces_ssc",
            subject = Subject.SCIENCE,
            gradeTier = GradeTier.SECONDARY_SSC,
            isSscSpecial = true,
            title = "Newtonian Mechanics: Motion, Forces & Momentum",
            subtitle = "SSC Physics Board Core: Equations of Motion, Newton's Laws & Conservation of Momentum",
            readTimeMinutes = 5,
            wikiOverview = "Classical mechanics studies how physical bodies move when subjected to forces, formalized by Sir Isaac Newton's three laws of motion and the principle of conservation of linear momentum.",
            timelineSteps = listOf(
                "Law 1 (Inertia): An object remains at rest or in uniform straight motion unless acted on by an external net force.",
                "Law 2 (F = ma): The acceleration of a body is directly proportional to net force and inversely proportional to its mass.",
                "Law 3 (Action-Reaction): For every action force, there is an equal and opposite reaction force.",
                "Momentum Principle: In an isolated closed system, total linear momentum (p = mv) remains constant over time."
            ),
            chapters = listOf(
                StoryChapter(
                    chapterNumber = 1,
                    title = "Kinematics & The Four Fundamental Equations of Motion",
                    paragraphs = listOf(
                        "In kinematics, motion is described through scalar and vector quantities: displacement (s), initial velocity (u), final velocity (v), uniform acceleration (a), and elapsed time (t).",
                        "For linear motion undergoing constant acceleration, Galileo and Newton established the quintessential mathematical relationships: v = u + at, s = ((u + v)/2)t, s = ut + ½at², and v² = u² + 2as.",
                        "When objects fall under the uniform gravitational pull of the Earth (g ≈ 9.8 m/s²), air resistance is often negligible in introductory analysis, demonstrating that all masses accelerate toward Earth at identical rates."
                    ),
                    pullQuote = "v² = u² + 2as (The Independent Kinematic Energy-Distance Formula)"
                ),
                StoryChapter(
                    chapterNumber = 2,
                    title = "Newton's Three Laws & The Conservation of Momentum",
                    paragraphs = listOf(
                        "Newton's First Law defines inertia—the inherent resistance of mass to changes in its state of motion. The Second Law provides the fundamental equation of classical mechanics: Net Force (F) equals mass times acceleration (F = ma), or the time rate of change of momentum (dp/dt).",
                        "The Third Law states that forces always exist in matched interactive pairs. When a rocket engine expels combustion gases downward at extreme velocities, the escaping gases exert an equal upward thrust propelling the vessel into orbit.",
                        "During elastic and inelastic collisions between bodies in an isolated system, the total linear momentum before impact equals the total linear momentum after impact (m₁u₁ + m₂u₂ = m₁v₁ + m₂v₂)."
                    ),
                    pullQuote = "“To every action there is always opposed an equal reaction.” — Isaac Newton, Principia Mathematica"
                )
            ),
            vocabulary = listOf(
                VocabularyWord("Inertia", "noun", "The property of matter by which it continues in its existing state of rest or uniform motion unless changed by an external force.", "A passenger lurches forward when a bus brakes suddenly due to inertia."),
                VocabularyWord("Momentum", "noun", "The quantity of motion of a moving body, measured as a product of its mass and velocity (p = mv).", "A heavy freight train possesses immense momentum even at slow speeds."),
                VocabularyWord("Acceleration", "noun", "The rate of change of velocity per unit of time (a = Δv/Δt).", "Free-falling objects accelerate downward at approximately 9.8 m/s² near sea level."),
                VocabularyWord("Scalar", "adjective", "Having only magnitude, not direction (e.g. speed, distance, mass).", "Distance is a scalar quantity, whereas displacement is a vector."),
                VocabularyWord("Vector", "noun", "A quantity having both direction and magnitude (e.g. velocity, acceleration, force).", "Force is a vector quantity represented with directional arrows.")
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    id = 1,
                    question = "Which formula represents Newton's Second Law of Motion relating force (F), mass (m), and acceleration (a)?",
                    options = listOf("F = ma", "F = m / a", "F = m + a", "F = ½ mv²"),
                    correctOptionIndex = 0,
                    explanation = "Newton's Second Law proves that force equals mass multiplied by acceleration (F = ma)."
                ),
                QuizQuestion(
                    id = 2,
                    question = "What is the SI unit of measurement for force?",
                    options = listOf("Newton (N)", "Joule (J)", "Watt (W)", "Pascal (Pa)"),
                    correctOptionIndex = 0,
                    explanation = "One Newton (N) is defined as the force required to accelerate a 1-kilogram mass at 1 meter per second squared."
                ),
                QuizQuestion(
                    id = 3,
                    question = "According to Newton's Third Law, if a swimmer pushes water backward with a force of 100 N, what force pushes the swimmer forward?",
                    options = listOf("Exactly 100 N forward", "0 N", "200 N forward", "50 N forward"),
                    correctOptionIndex = 0,
                    explanation = "Newton's Third Law guarantees that action and reaction forces are strictly equal in magnitude and opposite in direction."
                ),
                QuizQuestion(
                    id = 4,
                    question = "Which equation of motion gives displacement (s) when final velocity (v) is unknown?",
                    options = listOf("s = ut + ½ at²", "v = u + at", "v² = u² + 2as", "F = ma"),
                    correctOptionIndex = 0,
                    explanation = "s = ut + ½ at² calculates displacement using initial velocity (u), time (t), and acceleration (a)."
                ),
                QuizQuestion(
                    id = 5,
                    question = "In an isolated closed system with no external forces, what happens to total linear momentum during a collision?",
                    options = listOf("It remains conserved and constant", "It doubles instantly", "It drops to zero", "It transforms entirely into light"),
                    correctOptionIndex = 0,
                    explanation = "The Law of Conservation of Momentum dictates that total momentum before collision equals total momentum after collision."
                )
            )
        ),

        BookStory(
            id = "science_electricity_magnetism_ssc",
            subject = Subject.SCIENCE,
            gradeTier = GradeTier.SECONDARY_SSC,
            isSscSpecial = true,
            title = "Current Electricity, Ohm's Law & Electromagnetic Induction",
            subtitle = "SSC Physics Board Core: Voltage, Resistance, Series/Parallel Circuits & Faraday's Law",
            readTimeMinutes = 5,
            wikiOverview = "Electricity and magnetism are two complementary manifestations of the unified electromagnetic force, governing electrical circuits, resistance, transformers, and electric motors.",
            timelineSteps = listOf(
                "Ohm's Law: Current (I) through a conductor is directly proportional to potential difference (V) and inversely proportional to resistance (R): V = IR.",
                "Joule's Law of Heating: Heat produced in a resistor is proportional to I²Rt.",
                "Oersted's Discovery: An electric current flowing through a conductor creates an encircling magnetic field.",
                "Faraday's Induction Law: A changing magnetic flux induces an electromotive force (EMF) in a closed circuit, powering modern electrical generators."
            ),
            chapters = listOf(
                StoryChapter(
                    chapterNumber = 1,
                    title = "Electric Current, Potential Difference & Ohm's Law",
                    paragraphs = listOf(
                        "An electric current (I) is the organized drift of electric charge carriers (electrons) flowing through a conducting medium, measured in Amperes (1 A = 1 Coulomb/second).",
                        "The driving force behind this flow is potential difference (voltage, V), which represents the electrical potential energy per unit charge. In 1827, Georg Simon Ohm discovered that at constant temperature, current is directly proportional to potential difference: V = IR.",
                        "Electrical resistance (R, measured in Ohms Ω) depends upon the conductor's material resistivity (ρ), length (L), and cross-sectional area (A): R = ρ(L/A)."
                    ),
                    pullQuote = "V = I × R (Ohm's Law of Electrical Resistance)"
                ),
                StoryChapter(
                    chapterNumber = 2,
                    title = "Electromagnetic Induction: Powering the Modern Grid",
                    paragraphs = listOf(
                        "In 1831, Michael Faraday performed a groundbreaking experiment: moving a permanent bar magnet in and out of a coiled wire induced a measurable electric current without any battery connected.",
                        "Faraday's Law of Induction proves that any change in magnetic flux through a conducting loop induces an electromotive force (EMF): ε = -dΦ/dt. The negative sign (Lenz's Law) dictates that induced currents generate opposing magnetic fields.",
                        "This electromagnetic principle underpins electric generators, transformers, wind turbines, and hydroelectric plants that supply power to modern civilization."
                    ),
                    pullQuote = "“Convert magnetism into electricity, and you illuminate the world.” — Michael Faraday"
                )
            ),
            vocabulary = listOf(
                VocabularyWord("Potential Difference", "noun", "The difference in electric potential between two points, driving electric current (measured in Volts).", "A 220V household supply provides potential difference to run appliances."),
                VocabularyWord("Electromagnetic Induction", "noun", "The production of an electromotive force across an electrical conductor in a changing magnetic field.", "Power station turbines rely on electromagnetic induction to generate alternating current."),
                VocabularyWord("Resistivity", "noun", "A material-specific property measuring how strongly it opposes the flow of electric current.", "Copper has low resistivity, making it an excellent electrical wire material."),
                VocabularyWord("Lenz's Law", "noun", "A law stating that the direction of an induced current always opposes the change in magnetic flux that produced it.", "Lenz's law is a direct consequence of the conservation of energy."),
                VocabularyWord("Transformer", "noun", "An electrical device that steps up or steps down alternating voltages using mutual electromagnetic induction.", "Substation step-down transformers reduce high-transmission voltages to household levels.")
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    id = 1,
                    question = "According to Ohm's Law, what happens to the electric current (I) if voltage (V) is doubled while resistance (R) stays constant?",
                    options = listOf("The current doubles", "The current halves", "The current drops to zero", "The current remains unchanged"),
                    correctOptionIndex = 0,
                    explanation = "Because I = V / R, doubling voltage across constant resistance doubles the resulting current."
                ),
                QuizQuestion(
                    id = 2,
                    question = "What is the SI unit of electrical resistance?",
                    options = listOf("Ohm (Ω)", "Ampere (A)", "Volt (V)", "Coulomb (C)"),
                    correctOptionIndex = 0,
                    explanation = "Resistance is measured in Ohms, represented by the Greek capital omega symbol (Ω)."
                ),
                QuizQuestion(
                    id = 3,
                    question = "Which scientist discovered that moving a magnet near a coil of wire induces an electric current?",
                    options = listOf("Michael Faraday", "Albert Einstein", "Gregor Mendel", "Charles Darwin"),
                    correctOptionIndex = 0,
                    explanation = "Michael Faraday discovered electromagnetic induction in 1831, enabling electrical dynamos and generators."
                ),
                QuizQuestion(
                    id = 4,
                    question = "In a series circuit with two resistors of 4 Ω and 6 Ω, what is the total equivalent resistance?",
                    options = listOf("10 Ω", "2.4 Ω", "24 Ω", "2 Ω"),
                    correctOptionIndex = 0,
                    explanation = "In series circuits, equivalent resistance is the direct algebraic sum: R_total = R₁ + R₂ = 4 + 6 = 10 Ω."
                ),
                QuizQuestion(
                    id = 5,
                    question = "What device steps up or steps down alternating voltage in the electrical grid?",
                    options = listOf("A Transformer", "A Thermometer", "A Barometer", "A Compass"),
                    correctOptionIndex = 0,
                    explanation = "Transformers utilize mutual induction across two coils to alter AC voltages safely and efficiently."
                )
            )
        ),

        BookStory(
            id = "science_atomic_structure_bonding_ssc",
            subject = Subject.SCIENCE,
            gradeTier = GradeTier.SECONDARY_SSC,
            isSscSpecial = true,
            title = "Atomic Structure, Periodic Trends & Chemical Bonding",
            subtitle = "SSC Chemistry Board Core: Bohr Model, Covalent vs. Ionic Bonds & Octet Rule",
            readTimeMinutes = 5,
            wikiOverview = "Chemistry is the science of matter, examining how subatomic particles (protons, neutrons, electrons) organize into atoms and form ionic and covalent bonds to achieve thermodynamic stability.",
            timelineSteps = listOf(
                "Subatomic Structure: Protons (+1) and Neutrons (0) in the dense nucleus; Electrons (-1) orbiting in discrete energy shells (K, L, M, N).",
                "Octet Rule: Atoms share or transfer valence electrons to achieve stable noble-gas electron configurations (8 valence electrons).",
                "Ionic Bonding: Electrostatic attraction between oppositely charged ions formed by complete electron transfer (e.g. NaCl).",
                "Covalent Bonding: Chemical bonding formed by the equal or polar sharing of electron pairs between nonmetal atoms (e.g. H₂O, CO₂)."
            ),
            chapters = listOf(
                StoryChapter(
                    chapterNumber = 1,
                    title = "The Quantum Architecture of the Atom",
                    paragraphs = listOf(
                        "Every substance in the observable universe is composed of chemical elements. At the center of an atom lies an unimaginably dense nucleus composed of positively charged protons and neutral neutrons, holding over 99.9% of atomic mass.",
                        "Niels Bohr revolutionized physics by postulating that electrons orbit the nucleus in quantized, discrete energy levels without radiating energy unless transitioning between states (absorbing or emitting photons).",
                        "The chemical identity of an element is determined solely by its atomic number (Z, number of protons), while its chemical reactivity is governed by the configuration of its outermost valence electrons."
                    ),
                    pullQuote = "“Electrons do not spiral into the nucleus; they occupy discrete, quantized harmonic shells of probability.”"
                ),
                StoryChapter(
                    chapterNumber = 2,
                    title = "Chemical Bonding: Ionic Transfer vs. Covalent Sharing",
                    paragraphs = listOf(
                        "Atoms bond with one another to attain lower potential energy and achieve the stable octet configuration characteristic of noble gases.",
                        "In ionic bonding, highly electropositive metals (such as sodium, Na) donate valence electrons to highly electronegative nonmetals (such as chlorine, Cl). The resulting cation (Na⁺) and anion (Cl⁻) bind together through omnidirectional electrostatic Coulombic forces, forming rigid crystalline lattices.",
                        "In covalent bonding, nonmetals with similar electronegativities share electron pairs. In a water molecule (H₂O), oxygen shares two electron pairs with hydrogen atoms, creating strong intramolecular bonds."
                    ),
                    pullQuote = "“Chemical bonds are nature's pursuit of thermodynamic harmony and valence shell stability.”"
                )
            ),
            vocabulary = listOf(
                VocabularyWord("Valence Electrons", "noun", "Electrons in the outermost energy shell of an atom that participate in chemical bonding.", "Carbon has 4 valence electrons, allowing it to form four versatile covalent bonds."),
                VocabularyWord("Ionic Bond", "noun", "A chemical bond formed through the electrostatic attraction between oppositely charged ions.", "Sodium chloride (table salt) is held together by strong ionic bonds."),
                VocabularyWord("Covalent Bond", "noun", "A chemical bond formed by the sharing of one or more pairs of electrons between atoms.", "Water and methane are held together by covalent electron sharing."),
                VocabularyWord("Electronegativity", "noun", "The tendency of an atom to attract shared electron pairs toward itself in a chemical bond.", "Fluorine possesses the highest electronegativity on the Pauling scale."),
                VocabularyWord("Octet Rule", "noun", "A chemical rule of thumb that atoms tend to combine such that each has eight electrons in its valence shell.", "Atoms gain, lose, or share electrons to satisfy the octet rule.")
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    id = 1,
                    question = "Which subatomic particles reside inside the central atomic nucleus?",
                    options = listOf("Protons and Neutrons", "Electrons and Photons", "Neutrons and Electrons only", "Alpha particles only"),
                    correctOptionIndex = 0,
                    explanation = "Protons and neutrons compose the atomic nucleus, bound together by the strong nuclear force."
                ),
                QuizQuestion(
                    id = 2,
                    question = "How many valence electrons are required in the outer shell for most atoms to achieve a stable octet?",
                    options = listOf("8 electrons", "2 electrons", "18 electrons", "32 electrons"),
                    correctOptionIndex = 0,
                    explanation = "The octet rule dictates that atoms achieve stable electronic configurations when their valence shell contains 8 electrons."
                ),
                QuizQuestion(
                    id = 3,
                    question = "What type of chemical bond forms when one atom transfers an electron completely to another atom?",
                    options = listOf("Ionic Bond", "Covalent Bond", "Hydrogen Bond", "Metallic Bond"),
                    correctOptionIndex = 0,
                    explanation = "Ionic bonds form when electrons are transferred from a metal to a nonmetal, creating attracting ions."
                ),
                QuizQuestion(
                    id = 4,
                    question = "What is the atomic number of an element equal to?",
                    options = listOf("The number of protons in its nucleus", "The sum of protons and neutrons", "The number of neutrons only", "The total electron mass"),
                    correctOptionIndex = 0,
                    explanation = "The atomic number (Z) uniquely identifies an element and equals the number of protons in its nucleus."
                ),
                QuizQuestion(
                    id = 5,
                    question = "Which type of chemical bond holds the hydrogen and oxygen atoms together within a water molecule (H₂O)?",
                    options = listOf("Covalent Bond", "Ionic Bond", "Nuclear Bond", "Gravitational Bond"),
                    correctOptionIndex = 0,
                    explanation = "Hydrogen and oxygen share electron pairs, forming polar covalent bonds within the H₂O molecule."
                )
            )
        ),

        // =========================================================================
        // SCIENCE: GRADES 1-8 (Foundations & Intermediate)
        // Living Organisms, Matter & States, Solar System, Weather
        // =========================================================================
        BookStory(
            id = "science_solar_system_states_middle",
            subject = Subject.SCIENCE,
            gradeTier = GradeTier.MIDDLE,
            isSscSpecial = false,
            title = "States of Matter & The Planets of the Solar System",
            subtitle = "Grades 6–8 Science Unit: Solids, Liquids, Gases & Celestial Dynamics",
            readTimeMinutes = 4,
            wikiOverview = "Matter exists in distinct physical states determined by molecular kinetic energy, while celestial gravity organizes planets in elliptical orbits around our parent star, the Sun.",
            timelineSteps = listOf(
                "States of Matter: Solids (fixed shape and volume), Liquids (fixed volume, takes shape of container), Gases (expands to fill all volume).",
                "Phase Changes: Melting, Freezing, Evaporation, Condensation, and Sublimation.",
                "Solar System Order: Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune."
            ),
            chapters = listOf(
                StoryChapter(
                    chapterNumber = 1,
                    title = "The Kinetic Theory of Matter",
                    paragraphs = listOf(
                        "Everything around us—from mountain rocks to ocean waves and atmospheric air—is composed of tiny particles called molecules in perpetual motion.",
                        "In solids, particles vibrate in fixed crystal positions. Adding heat increases kinetic energy, causing solids to melt into flowing liquids. With further heating, particles break free entirely into high-speed gas vapors."
                    ),
                    pullQuote = "“Heat is the kinetic dance of microscopic atoms.”"
                )
            ),
            vocabulary = listOf(
                VocabularyWord("Condensation", "noun", "The conversion of a vapor or gas to a liquid as it cools.", "Dew forming on morning grass leaves is an example of water condensation."),
                VocabularyWord("Orbit", "noun", "The curved gravitational path of an object around a star or planet.", "Earth completes one full orbit around the Sun every 365.25 days.")
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    id = 1,
                    question = "Which state of matter has a definite volume but takes the shape of whatever container holds it?",
                    options = listOf("Liquid", "Solid", "Gas", "Plasma"),
                    correctOptionIndex = 0,
                    explanation = "Liquids flow to match the shape of their container while retaining fixed volume."
                ),
                QuizQuestion(
                    id = 2,
                    question = "Which planet is closest to the Sun in our Solar System?",
                    options = listOf("Mercury", "Mars", "Jupiter", "Venus"),
                    correctOptionIndex = 0,
                    explanation = "Mercury is the innermost and smallest rocky planet orbiting closest to the Sun."
                ),
                QuizQuestion(
                    id = 3,
                    question = "What phase change occurs when water vapor turns into liquid water droplets?",
                    options = listOf("Condensation", "Sublimation", "Evaporation", "Melting"),
                    correctOptionIndex = 0,
                    explanation = "Condensation is the process where cooling gas vapor transitions back into liquid droplets."
                )
            )
        ),

        // =========================================================================
        // ICT: SSC / GRADES 9-12 (Exhaustive Topics)
        // Networking & Topology, Internet & Cloud, Cybersecurity & Malware,
        // HTML/CSS Web Development, Logic Gates, Programming (C/Python Basics)
        // =========================================================================
        BookStory(
            id = "ict_networking_topology_cloud_ssc",
            subject = Subject.ICT,
            gradeTier = GradeTier.SECONDARY_SSC,
            isSscSpecial = true,
            title = "Computer Networks, Network Topologies & Cloud Architecture",
            subtitle = "SSC ICT Board Exam Core: LAN/WAN, Star & Mesh Topologies, IP/TCP & Cloud Models",
            readTimeMinutes = 5,
            wikiOverview = "Computer networks interconnect autonomous computational devices through transmission media and standard protocols (OSI and TCP/IP models), forming the infrastructure of the global internet and cloud computing.",
            timelineSteps = listOf(
                "Geographical Scale: PAN (Personal), LAN (Local Area), MAN (Metropolitan Area), and WAN (Wide Area / Global Internet).",
                "Physical Topologies: Bus, Ring, Star (central switch hub), Tree, and Mesh (full fault-tolerant redundancy).",
                "OSI 7-Layer Model: Physical, Data Link, Network, Transport, Session, Presentation, and Application layers.",
                "Cloud Service Models: IaaS (Infrastructure as a Service), PaaS (Platform as a Service), and SaaS (Software as a Service)."
            ),
            chapters = listOf(
                StoryChapter(
                    chapterNumber = 1,
                    title = "Network Architectures & Physical Topologies",
                    paragraphs = listOf(
                        "A computer network enables decentralized devices (hosts, servers, routers) to exchange packets of data. Networks are categorized by physical span into Local Area Networks (LAN) within a single building and Wide Area Networks (WAN) spanning continents.",
                        "Physical network topology dictates how computing nodes are arranged. In a Star Topology, all client devices connect radially to a central switch or hub; if a single cable breaks, other workstations remain uninterrupted.",
                        "In a Full Mesh Topology, every node has a dedicated point-to-point physical connection to every other node, delivering supreme fault tolerance and zero single-point-of-failure vulnerability at higher cabling costs."
                    ),
                    pullQuote = "“In modern networks, the Star topology balances cost and maintainability, while Mesh topologies power mission-critical backbones.”"
                ),
                StoryChapter(
                    chapterNumber = 2,
                    title = "The TCP/IP Protocol Stack & Cloud Services",
                    paragraphs = listOf(
                        "The internet functions through standard protocol suites. The Internet Protocol (IP) assigns unique logical addresses (IPv4: 32-bit; IPv6: 128-bit) and routes packets across distributed networks, while Transmission Control Protocol (TCP) guarantees reliable, in-order packet delivery through three-way handshakes.",
                        "Cloud computing abstracts on-premise physical servers into on-demand, elastically scalable utility pools accessible via the internet.",
                        "Enterprises adopt three canonical cloud models: Infrastructure as a Service (IaaS, virtualized compute and storage), Platform as a Service (PaaS, managed runtime environments for developers), and Software as a Service (SaaS, end-user applications delivered via web browsers)."
                    ),
                    pullQuote = "“The Cloud is not a mystical ether; it is someone else's high-availability distributed computing cluster.”"
                )
            ),
            vocabulary = listOf(
                VocabularyWord("Star Topology", "noun", "A network layout where all nodes are connected independently to a central hub or switch.", "Star topology is the dominant standard in modern Ethernet office networks."),
                VocabularyWord("Mesh Topology", "noun", "A network topology where devices are interconnected with multiple redundant paths.", "Military and financial transaction backbones utilize mesh topologies for continuous uptime."),
                VocabularyWord("Router", "noun", "A networking device that forwards data packets between computer networks based on IP routing tables.", "Routers operate at the Network Layer (Layer 3) to guide packets across the internet."),
                VocabularyWord("IaaS", "noun", "Infrastructure as a Service; cloud computing providing virtualized computing resources over the internet.", "Amazon AWS EC2 and Google Cloud Compute Engine are prominent examples of IaaS."),
                VocabularyWord("Bandwidth", "noun", "The maximum rate of data transfer across a given path, measured in bits per second (bps).", "Fiber-optic cables offer gigabit bandwidth with ultra-low latency.")
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    id = 1,
                    question = "Which network topology connects every computer independently to a single central switch or hub?",
                    options = listOf("Star Topology", "Bus Topology", "Ring Topology", "Linear Topology"),
                    correctOptionIndex = 0,
                    explanation = "In a Star topology, every host machine connects to a central switch, isolating individual line faults."
                ),
                QuizQuestion(
                    id = 2,
                    question = "How many bits are contained in a standard IPv4 address?",
                    options = listOf("32 bits", "64 bits", "128 bits", "16 bits"),
                    correctOptionIndex = 0,
                    explanation = "An IPv4 address consists of 32 bits divided into four 8-bit octets (e.g. 192.168.1.1)."
                ),
                QuizQuestion(
                    id = 3,
                    question = "What type of network encompasses a single building, computer lab, or office floor?",
                    options = listOf("LAN (Local Area Network)", "WAN (Wide Area Network)", "MAN (Metropolitan Area Network)", "SAN"),
                    correctOptionIndex = 0,
                    explanation = "A LAN connects computers and devices within a limited geographical area such as a school or office."
                ),
                QuizQuestion(
                    id = 4,
                    question = "Which cloud service model provides virtualized servers, storage, and networking hardware on-demand?",
                    options = listOf("IaaS (Infrastructure as a Service)", "SaaS (Software as a Service)", "PaaS (Platform as a Service)", "DaaS"),
                    correctOptionIndex = 0,
                    explanation = "IaaS delivers fundamental computing infrastructure (virtual machines, disks, virtual networks) to customers."
                ),
                QuizQuestion(
                    id = 5,
                    question = "Which protocol in the TCP/IP stack is responsible for establishing a reliable, connection-oriented packet stream?",
                    options = listOf("TCP (Transmission Control Protocol)", "UDP", "ICMP", "ARP"),
                    correctOptionIndex = 0,
                    explanation = "TCP ensures error-checked, ordered, and guaranteed delivery of byte streams between internet applications."
                )
            )
        ),

        BookStory(
            id = "ict_cybersecurity_logic_gates_ssc",
            subject = Subject.ICT,
            gradeTier = GradeTier.SECONDARY_SSC,
            isSscSpecial = true,
            title = "Cybersecurity, Malware & Digital Logic Gates",
            subtitle = "SSC ICT Board Exam Core: AND/OR/NOT Gates, Phishing, Malware & Cryptography",
            readTimeMinutes = 5,
            wikiOverview = "Digital systems process binary information (0s and 1s) using electronic logic gates, while cybersecurity disciplines protect information systems, networks, and databases against unauthorized intrusion and malware.",
            timelineSteps = listOf(
                "Basic Logic Gates: AND (output 1 only if all inputs are 1), OR (output 1 if any input is 1), NOT (inverter).",
                "Universal Logic Gates: NAND and NOR (can synthesize any other boolean circuit).",
                "Common Threats: Phishing, Ransomware, Trojan Horses, Spyware, and Distributed Denial of Service (DDoS).",
                "Security Countermeasures: Two-Factor Authentication (2FA), Firewalls, SSL/TLS Encryption, and Role-Based Access."
            ),
            chapters = listOf(
                StoryChapter(
                    chapterNumber = 1,
                    title = "Digital Logic Gates: The Binary Brain of Silicon",
                    paragraphs = listOf(
                        "Computers represent all instructions and media through binary digits: 0 (low voltage) and 1 (high voltage). Electronic transistors are configured into fundamental logic gates that evaluate Boolean algebraic functions.",
                        "The AND gate yields a TRUE output only when both input signals are TRUE. The OR gate outputs TRUE if at least one input is TRUE. The NOT gate inverts its single input.",
                        "Engineers frequently construct complex microprocessors using NAND and NOR gates alone, which are classified as Universal Gates because any arbitrary combinatorial circuit can be built exclusively from them."
                    ),
                    pullQuote = "“NAND and NOR gates are universal building blocks capable of synthesizing all computational logic.”"
                ),
                StoryChapter(
                    chapterNumber = 2,
                    title = "Cybersecurity Defenses Against Modern Digital Threats",
                    paragraphs = listOf(
                        "As societal reliance upon digital banking, healthcare records, and governmental portals expands, cybersecurity safeguards the CIA triad: Confidentiality, Integrity, and Availability.",
                        "Malicious software (malware) encompasses self-replicating viruses, stealth trojans, keylogger spyware, and devastating ransomware that encrypts victim files for extortion.",
                        "Social engineering attacks like phishing trick users into forfeiting passwords through deceptive emails and counterfeit web portals. Robust security requires end-to-end encryption, regular offline backups, and multi-factor authentication (MFA)."
                    ),
                    pullQuote = "“The human element is often the most vulnerable link in any computational security system.”"
                )
            ),
            vocabulary = listOf(
                VocabularyWord("Logic Gate", "noun", "An idealized model of computation implementing a Boolean function on one or more binary inputs.", "Logic gates inside microprocessors execute billions of arithmetic operations each second."),
                VocabularyWord("Universal Gate", "noun", "A logic gate (NAND or NOR) that can implement any Boolean function without using other gate types.", "NAND gates are called universal gates because they can construct AND, OR, and NOT circuits."),
                VocabularyWord("Phishing", "noun", "A fraudulent attempt to obtain sensitive information like usernames and passwords by disguising as a trustworthy entity.", "Spear phishing emails mimic school administration portals to trick students."),
                VocabularyWord("Ransomware", "noun", "Malware designed to deny access to a computer system or files until a ransom payment is made.", "Hospitals employ air-gapped backups to prevent ransomware attacks from halting operations."),
                VocabularyWord("Cryptography", "noun", "The practice of encrypting plaintext into ciphertext to prevent unauthorized access.", "RSA public-key cryptography secures HTTPS web traffic across the internet.")
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    id = 1,
                    question = "Which logic gate outputs 1 ONLY if BOTH input signals are equal to 1?",
                    options = listOf("AND Gate", "OR Gate", "NOT Gate", "XOR Gate with equal inputs"),
                    correctOptionIndex = 0,
                    explanation = "An AND gate requires all inputs to be true (1) to output a 1."
                ),
                QuizQuestion(
                    id = 2,
                    question = "Which two logic gates are categorized as 'Universal Gates' in digital electronics?",
                    options = listOf("NAND and NOR", "AND and OR", "NOT and XOR", "BUFFER and XNOR"),
                    correctOptionIndex = 0,
                    explanation = "NAND and NOR gates are universal because any Boolean function can be implemented using only one of these types."
                ),
                QuizQuestion(
                    id = 3,
                    question = "What is the deceptive cyberattack where attackers send fake emails pretending to be trusted banks or universities?",
                    options = listOf("Phishing", "SQL Injection", "Hardware Defect", "Screen Recording"),
                    correctOptionIndex = 0,
                    explanation = "Phishing uses fraudulent messages designed to trick victims into revealing sensitive personal credentials."
                ),
                QuizQuestion(
                    id = 4,
                    question = "What type of malware encrypts a user's files and demands payment to restore access?",
                    options = listOf("Ransomware", "Adware", "Spreadsheet", "Firewall"),
                    correctOptionIndex = 0,
                    explanation = "Ransomware holds user data hostage through strong encryption until a monetary ransom is paid."
                ),
                QuizQuestion(
                    id = 5,
                    question = "What does the 'C' stand for in the fundamental cybersecurity 'CIA Triad'?",
                    options = listOf("Confidentiality", "Computer", "Certificate", "Circuit"),
                    correctOptionIndex = 0,
                    explanation = "The CIA triad represents the three pillars of security: Confidentiality, Integrity, and Availability."
                )
            )
        ),

        BookStory(
            id = "ict_web_development_html_css_ssc",
            subject = Subject.ICT,
            gradeTier = GradeTier.SECONDARY_SSC,
            isSscSpecial = true,
            title = "Web Development: HTML5, CSS Styling & Client-Server Web",
            subtitle = "SSC ICT Board Exam Core: Tags, Semantic Elements, Box Model & Hyperlinks",
            readTimeMinutes = 5,
            wikiOverview = "HyperText Markup Language (HTML5) defines the structural architecture of the World Wide Web, styled visually using Cascading Style Sheets (CSS) through the client-server HTTP protocol.",
            timelineSteps = listOf(
                "Tim Berners-Lee (1989): Invents the World Wide Web and original HTML format at CERN.",
                "HTML5 Structure: <!DOCTYPE html>, <html>, <head>, <title>, <body>, <header>, <nav>, <section>, <footer>.",
                "Key Elements: Headings (<h1> to <h6>), Paragraphs (<p>), Hyperlinks (<a href>), Images (<img src>), and Tables (<table>, <tr>, <td>).",
                "CSS Box Model: Content, Padding, Border, and Margin controlling web layout geometry."
            ),
            chapters = listOf(
                StoryChapter(
                    chapterNumber = 1,
                    title = "The Anatomy of HTML5 Web Markup",
                    paragraphs = listOf(
                        "Every webpage rendered in a modern web browser is fundamentally an HTML document: a structured text file containing nested opening and closing tags enclosed in angle brackets (<tag>content</tag>).",
                        "The <!DOCTYPE html> declaration informs the browser engine to render the document under modern HTML5 standards. The <head> element hosts metadata, character encoding (<meta charset='UTF-8'>), and stylesheet links.",
                        "Inside the <body>, developers use semantic tags like <header>, <main>, <article>, and <footer> to deliver clean document outlines that improve accessibility for screen readers and search engines."
                    ),
                    pullQuote = "<a href='https://learnicle.edu'>Empower Learning</a> (Hyperlinks knit the web together)"
                ),
                StoryChapter(
                    chapterNumber = 2,
                    title = "Styling with CSS: The Box Model & Responsive Design",
                    paragraphs = listOf(
                        "While HTML supplies structural content, Cascading Style Sheets (CSS) dictate presentation: typography, palettes, spacing, and adaptive layout geometries.",
                        "Every element rendered in CSS conforms to the Box Model: the central Content area surrounded successively by Padding (internal breathing room), Borders (decorative outlines), and Margins (external separation between adjacent elements).",
                        "Using modern CSS Grid and Flexbox, web applications dynamically adapt layouts from handheld smartphone screens to 4K desktop displays."
                    ),
                    pullQuote = "“HTML is the skeleton of the web; CSS is the skin and style.”"
                )
            ),
            vocabulary = listOf(
                VocabularyWord("Semantic HTML", "noun", "HTML markup that introduces meaning and structure to the webpage rather than just presentation.", "<article> and <nav> are semantic tags that clarify document purpose."),
                VocabularyWord("CSS Box Model", "noun", "A container model composed of Content, Padding, Border, and Margin wrapping every HTML element.", "Understanding the Box Model is vital for calculating element dimensions accurately."),
                VocabularyWord("Hyperlink", "noun", "A reference in a digital document that the user can click to navigate directly to another resource.", "The anchor tag <a href> generates hyperlinks across the World Wide Web."),
                VocabularyWord("Selector", "noun", "A CSS pattern used to select the HTML elements you want to style.", "Class selectors like .btn target all elements containing class='btn'."),
                VocabularyWord("Responsive Design", "noun", "An approach to web design that makes web pages render well on a variety of devices and screen sizes.", "CSS media queries enable responsive layouts on mobile devices.")
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    id = 1,
                    question = "Which HTML tag is used to create a clickable hyperlink to another web page?",
                    options = listOf("<a href='...'>", "<link>", "<href>", "<nav>"),
                    correctOptionIndex = 0,
                    explanation = "The anchor tag <a href='URL'> defines hyperlinks on the web."
                ),
                QuizQuestion(
                    id = 2,
                    question = "In the CSS Box Model, what layer directly surrounds the inner Content area before the border?",
                    options = listOf("Padding", "Margin", "Outline", "Shadow"),
                    correctOptionIndex = 0,
                    explanation = "Padding provides the internal spacing between element content and its outer border."
                ),
                QuizQuestion(
                    id = 3,
                    question = "Which HTML element represents the highest-level, most important heading on a page?",
                    options = listOf("<h1>", "<h6>", "<head>", "<header>"),
                    correctOptionIndex = 0,
                    explanation = "<h1> represents the primary top-level heading in HTML document hierarchies."
                ),
                QuizQuestion(
                    id = 4,
                    question = "What does the acronym HTML stand for in computer science?",
                    options = listOf("HyperText Markup Language", "High-level Text Management Language", "Home Tool Markup Language", "Hyperlinks and Text Multi-Layer"),
                    correctOptionIndex = 0,
                    explanation = "HTML stands for HyperText Markup Language, the standard markup language for documents designed to be displayed in a web browser."
                ),
                QuizQuestion(
                    id = 5,
                    question = "Which attribute in an <img> tag specifies the file path or URL of the image to display?",
                    options = listOf("src", "href", "alt", "path"),
                    correctOptionIndex = 0,
                    explanation = "The src (source) attribute provides the URL or path to the image asset."
                )
            )
        ),

        // =========================================================================
        // ICT: GRADES 1-8 (Foundations & Intermediate)
        // Basic Computer Parts, Keyboard/Mouse Skills, Internet Safety
        // =========================================================================
        BookStory(
            id = "ict_grade_1_5_computer_parts",
            subject = Subject.ICT,
            gradeTier = GradeTier.PRIMARY,
            isSscSpecial = false,
            title = "Hardware Basics: Inside Your Computer & Safe Internet",
            subtitle = "Grades 1–5 ICT Unit: CPU, Monitor, Keyboard, Mouse & Digital Etiquette",
            readTimeMinutes = 3,
            wikiOverview = "Computers are helpful electronic tools composed of input, processing, and output hardware that follow our instructions to calculate, write, and communicate.",
            timelineSteps = listOf(
                "Input Devices: Keyboard (typing letters), Mouse (clicking and pointing), Microphone (recording speech).",
                "Processing Device: The Central Processing Unit (CPU) acts as the electronic brain.",
                "Output Devices: Monitor (showing pictures and text), Speakers (sound), Printer (paper printouts).",
                "Internet Safety Rule: Never share real full names, home addresses, or passwords with strangers online."
            ),
            chapters = listOf(
                StoryChapter(
                    chapterNumber = 1,
                    title = "Meet the Computer: Input, Process, and Output",
                    paragraphs = listOf(
                        "A computer is an amazing machine that works like a helpful digital assistant. We provide input by tapping keys on a keyboard or clicking with a mouse.",
                        "Inside the computer case, the Central Processing Unit (CPU) acts like the computer's brain, solving calculations at lightning speed. It then sends output to the monitor so you can read, learn, and play!"
                    ),
                    pullQuote = "“The CPU is the hardworking brain that follows instructions faithfully.”"
                )
            ),
            vocabulary = listOf(
                VocabularyWord("Input Device", "noun", "A piece of equipment used to provide data and control signals to an information processing system.", "A computer mouse is an input device used to point and click."),
                VocabularyWord("Output Device", "noun", "Any device used to send data from a computer to another device or user.", "A computer screen is an output device displaying text and graphics.")
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    id = 1,
                    question = "Which computer component is known as the 'brain' of the computer?",
                    options = listOf("CPU (Central Processing Unit)", "The Mouse pad", "The Printer cable", "The Plastic case"),
                    correctOptionIndex = 0,
                    explanation = "The CPU processes instructions and performs all core computational logic."
                ),
                QuizQuestion(
                    id = 2,
                    question = "Which of the following is an input device used for typing letters and numbers?",
                    options = listOf("Keyboard", "Monitor", "Headphones", "Speaker"),
                    correctOptionIndex = 0,
                    explanation = "A keyboard takes user typing input and transmits keystrokes to the computer."
                ),
                QuizQuestion(
                    id = 3,
                    question = "What is the most important rule when using the internet safely at home or school?",
                    options = listOf("Never share passwords or personal home addresses with strangers online", "Type all letters in capital letters", "Only use the computer when it is raining"),
                    correctOptionIndex = 0,
                    explanation = "Keeping passwords and personal information private protects your identity and safety online."
                )
            )
        )
    )
}

object CuratedLessonsRepository {

    val allStories: List<BookStory> = CurriculumSyllabusData.sscAndGradeStories

    fun getStories(subject: Subject, gradeLevel: Int, isSscMode: Boolean): List<BookStory> {
        val targetTier = when {
            gradeLevel <= 5 -> GradeTier.PRIMARY
            gradeLevel <= 8 -> GradeTier.MIDDLE
            else -> GradeTier.SECONDARY_SSC
        }

        // Return matching stories for the subject
        val matched = allStories.filter { it.subject == subject }
        val tierMatched = matched.filter { it.gradeTier == targetTier }

        return if (tierMatched.isNotEmpty()) {
            if (isSscMode && targetTier == GradeTier.SECONDARY_SSC) {
                tierMatched.sortedByDescending { it.isSscSpecial }
            } else {
                tierMatched
            }
        } else {
            // Fallback to all stories for subject
            matched
        }
    }

    fun getStoryById(id: String): BookStory? {
        return allStories.find { it.id == id } ?: allStories.firstOrNull()
    }
}
