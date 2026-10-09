package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CrimsonAccent
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary

/**
 * Persistent top header bar for Learnicle:
 * - High-contrast dark theme background (#0F0F12)
 * - Crimson red accent bottom border (#E50914)
 * - "Learnicle" logo/title on the left with subtle "v1.0" subscript
 * - Active user stats (🔥 Streak & ⚡ XP) on the right
 */
@Composable
fun PersistentTopHeaderBar(
    streakDays: Int,
    xp: Int,
    modifier: Modifier = Modifier,
    streakPopTrigger: Any? = null,
    xpPopTrigger: Any? = null,
    onHeaderClick: (() -> Unit)? = null
) {
    val headerBg = Color(0xFF0F0F12)
    val crimsonBorder = Color(0xFFE50914)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(headerBg)
            .drawBehind {
                // Bottom crimson red accent border line
                val strokeWidth = 1.5.dp.toPx()
                val y = size.height - strokeWidth / 2f
                drawLine(
                    color = crimsonBorder,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = strokeWidth
                )
            }
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .testTag("persistent_top_header_bar")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left: Learnicle Logo & Brand Title with v1.0 subscript
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.testTag("header_brand_row")
            ) {
                LearnicleEmblem(
                    size = 28.dp,
                    modifier = Modifier.testTag("header_emblem")
                )
                Spacer(modifier = Modifier.width(8.dp))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "Learnicle",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        letterSpacing = 0.3.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "v1.0",
                        style = MaterialTheme.typography.labelSmall,
                        color = CrimsonAccent,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                }
            }

            // Right: Active User Stats (🔥 Streak & ⚡ XP)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.testTag("header_stats_row")
            ) {
                StreakBadge(
                    streakDays = streakDays,
                    popTrigger = streakPopTrigger,
                    onClick = onHeaderClick
                )
                XpBadge(
                    xp = xp,
                    popTrigger = xpPopTrigger,
                    onClick = onHeaderClick
                )
            }
        }
    }
}
