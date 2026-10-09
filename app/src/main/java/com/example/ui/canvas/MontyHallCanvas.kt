package com.example.ui.canvas

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.MeetingRoom
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AccentGold
import com.example.ui.theme.AccentGreen
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CardElevated
import com.example.ui.theme.CardElevatedHover
import com.example.ui.theme.CrimsonAccent
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun MontyHallCanvas(
    modifier: Modifier = Modifier
) {
    // Car is fixed at Door 2 for pedagogical clarity or randomized
    val carDoor = 2
    var selectedDoor by remember { mutableIntStateOf(1) }
    var hostRevealedDoor by remember { mutableStateOf<Int?>(3) } // Monty opens door 3 (goat)
    var isSwitched by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(CardElevated)
            .border(1.dp, BorderSubtle, RoundedCornerShape(16.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "INTERACTIVE MONTY HALL VISUALIZER",
            style = MaterialTheme.typography.labelSmall,
            color = CrimsonAccent,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 3 Doors
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (doorNum in 1..3) {
                val isSelected = selectedDoor == doorNum
                val isRevealedByHost = hostRevealedDoor == doorNum
                val isCar = doorNum == carDoor

                val borderColor by animateColorAsState(
                    targetValue = when {
                        isSelected -> CrimsonAccent
                        isRevealedByHost -> Color(0xFF6E6E85)
                        else -> BorderSubtle
                    },
                    animationSpec = tween(300),
                    label = "doorBorder"
                )

                val bgColor by animateColorAsState(
                    targetValue = when {
                        isSelected -> Color(0xFF2C1016)
                        isRevealedByHost -> Color(0xFF141419)
                        else -> CardElevatedHover
                    },
                    animationSpec = tween(300),
                    label = "doorBg"
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(120.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(bgColor)
                        .border(if (isSelected) 2.dp else 1.dp, borderColor, RoundedCornerShape(12.dp))
                        .clickable(enabled = !isRevealedByHost) {
                            selectedDoor = doorNum
                            isSwitched = (doorNum != 1)
                        }
                        .testTag("door_$doorNum"),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        if (isRevealedByHost) {
                            Icon(
                                imageVector = Icons.Default.Pets,
                                contentDescription = "Goat",
                                tint = TextMuted,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "GOAT 🐐",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextMuted,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Eliminated",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextMuted,
                                fontSize = 10.sp
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.MeetingRoom,
                                contentDescription = "Door $doorNum",
                                tint = if (isSelected) CrimsonAccent else TextSecondary,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Door $doorNum",
                                style = MaterialTheme.typography.titleMedium,
                                color = if (isSelected) TextPrimary else TextSecondary,
                                fontWeight = FontWeight.Bold
                            )
                            if (isSelected) {
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = if (isSwitched) "SWITCHED" else "ORIGINAL",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = CrimsonAccent,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Interaction Bar: Switch vs Stay toggle
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = {
                    selectedDoor = 1
                    isSwitched = false
                },
                modifier = Modifier
                    .weight(1f)
                    .testTag("stay_door_button"),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = if (!isSwitched) CrimsonAccent else TextSecondary
                ),
                border = BorderStroke(1.dp, if (!isSwitched) CrimsonAccent else BorderSubtle)
            ) {
                Text(
                    text = "Stay (Door 1)",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = if (!isSwitched) FontWeight.Bold else FontWeight.Normal
                )
            }

            Button(
                onClick = {
                    selectedDoor = 2
                    isSwitched = true
                },
                modifier = Modifier
                    .weight(1f)
                    .testTag("switch_door_button"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSwitched) CrimsonAccent else CardElevatedHover,
                    contentColor = TextPrimary
                )
            ) {
                Text(
                    text = "Switch (Door 2)",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = if (isSwitched) FontWeight.Bold else FontWeight.Normal
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Probability comparison card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(CardElevatedHover)
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Win Probability if you STAY:",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
                Text(
                    text = "33.3% (1 in 3)",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            LinearProgressIndicator(
                progress = { 0.333f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = TextSecondary,
                trackColor = BorderSubtle,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Win Probability if you SWITCH:",
                    style = MaterialTheme.typography.bodySmall,
                    color = AccentGreen,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "66.7% (2 in 3) ⚡ DOUBLED",
                    style = MaterialTheme.typography.bodySmall,
                    color = AccentGreen,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            LinearProgressIndicator(
                progress = { 0.667f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = AccentGreen,
                trackColor = BorderSubtle,
            )
        }
    }
}
