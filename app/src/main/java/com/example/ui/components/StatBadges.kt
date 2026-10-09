package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AccentGold
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CardElevatedHover
import com.example.ui.theme.CrimsonAccent
import com.example.ui.theme.TextMuted

@Composable
fun StreakBadge(
    streakDays: Int,
    modifier: Modifier = Modifier,
    popTrigger: Any? = null,
    onClick: (() -> Unit)? = null
) {
    val scale = remember { Animatable(1f) }
    var prevStreak by remember { mutableIntStateOf(streakDays) }

    LaunchedEffect(streakDays, popTrigger) {
        if (streakDays != prevStreak || popTrigger != null) {
            prevStreak = streakDays
            scale.snapTo(1f)
            scale.animateTo(
                targetValue = 1.35f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            scale.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
        }
    }

    Box(
        modifier = modifier
            .scale(scale.value)
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF2B1B0A))
            .border(
                1.dp,
                if (scale.value > 1.05f) Color(0xFFFF9500) else Color(0xFF5E3A15),
                RoundedCornerShape(20.dp)
            )
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .padding(horizontal = 10.dp, vertical = 6.dp)
            .testTag("streak_badge"),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "🔥", fontSize = 13.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "$streakDays Days",
                style = MaterialTheme.typography.labelMedium,
                color = AccentGold,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun XpBadge(
    xp: Int,
    modifier: Modifier = Modifier,
    popTrigger: Any? = null,
    onClick: (() -> Unit)? = null
) {
    val scale = remember { Animatable(1f) }
    var prevXp by remember { mutableIntStateOf(xp) }

    LaunchedEffect(xp, popTrigger) {
        if (xp != prevXp || popTrigger != null) {
            prevXp = xp
            scale.snapTo(1f)
            scale.animateTo(
                targetValue = 1.35f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            scale.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
        }
    }

    Box(
        modifier = modifier
            .scale(scale.value)
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF261016))
            .border(
                1.dp,
                if (scale.value > 1.05f) CrimsonAccent else Color(0xFF4A1822),
                RoundedCornerShape(20.dp)
            )
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .padding(horizontal = 10.dp, vertical = 6.dp)
            .testTag("xp_badge"),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "⚡", fontSize = 13.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "$xp XP",
                style = MaterialTheme.typography.labelMedium,
                color = CrimsonAccent,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun SubjectBadge(
    subject: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(CardElevatedHover)
            .border(1.dp, BorderSubtle, RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(
            text = subject.uppercase(),
            style = MaterialTheme.typography.bodySmall,
            color = TextMuted,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.5.sp
        )
    }
}
