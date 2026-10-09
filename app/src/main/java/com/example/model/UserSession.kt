package com.example.model

enum class ReaderTheme(val displayName: String, val bgHex: Long, val textHex: Long, val cardHex: Long) {
    MIDNIGHT("Midnight", 0xFF0F0F12, 0xFFFFFFFF, 0xFF18181F),
    WARM_PARCHMENT("Parchment", 0xFF1C1917, 0xFFF5F5F4, 0xFF292524),
    SEPIA("Sepia", 0xFF241E19, 0xFFFBE8D3, 0xFF2F2721)
}

data class UserSession(
    val isLoggedIn: Boolean = false,
    val email: String = "azrafbinm@gmail.com",
    val displayName: String = "Azraf Bin M.",
    val gradeLevel: Int = 10,
    val isSscMode: Boolean = true,
    val streakDays: Int = 0,
    val xp: Int = 0,
    val notificationsEnabled: Boolean = true,
    val reminderTime: String = "9:00 AM",
    val completedStoryIds: Set<String> = emptySet(),
    val readerTheme: ReaderTheme = ReaderTheme.MIDNIGHT,
    val readerFontSizeSp: Int = 16
)
