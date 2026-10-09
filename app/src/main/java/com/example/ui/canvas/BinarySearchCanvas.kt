package com.example.ui.canvas

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
fun BinarySearchCanvas(
    modifier: Modifier = Modifier
) {
    val items = remember { listOf(2, 5, 8, 12, 16, 23, 38, 45, 56, 67, 78, 89, 91, 94, 99, 105) }
    val target = 45 // index 7

    // Search state: 0 -> full range, 1 -> low half or high half, etc.
    var currentStep by remember { mutableIntStateOf(0) }

    // Binary search bounds per step
    // Step 0: low=0, high=15, mid=7 (which is 45!)
    // Let's demonstrate searching for 78 (index 10):
    // Target 78:
    // Step 0: low=0, high=15, mid=7 (val 45). Since 45 < 78, discard left!
    // Step 1: low=8, high=15, mid=11 (val 89). Since 89 > 78, discard right!
    // Step 2: low=8, high=10, mid=9 (val 67). Since 67 < 78, discard left!
    // Step 3: low=10, high=10, mid=10 (val 78). FOUND!
    val searchTarget = 78

    val (low, high, mid, found) = when (currentStep) {
        0 -> Quadruple(0, 15, 7, false)
        1 -> Quadruple(8, 15, 11, false)
        2 -> Quadruple(8, 10, 9, false)
        else -> Quadruple(10, 10, 10, true)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(CardElevated)
            .border(1.dp, BorderSubtle, RoundedCornerShape(16.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "BINARY SEARCH BISECTION",
                style = MaterialTheme.typography.labelSmall,
                color = CrimsonAccent,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xFF2C1016))
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "Target: $searchTarget",
                    style = MaterialTheme.typography.labelSmall,
                    color = CrimsonAccent,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 16-element Grid (4x4)
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            userScrollEnabled = false
        ) {
            itemsIndexed(items) { index, value ->
                val isInRange = index in low..high
                val isMid = index == mid
                val isTarget = index == 10 && found

                val bgColor by animateColorAsState(
                    targetValue = when {
                        isTarget -> AccentGreen
                        isMid -> CrimsonAccent
                        isInRange -> CardElevatedHover
                        else -> Color(0xFF121216)
                    },
                    animationSpec = tween(250),
                    label = "cellBg"
                )

                val textColor by animateColorAsState(
                    targetValue = when {
                        isTarget -> Color.Black
                        isMid -> Color.White
                        isInRange -> TextPrimary
                        else -> TextMuted.copy(alpha = 0.35f)
                    },
                    label = "cellText"
                )

                Box(
                    modifier = Modifier
                        .height(38.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(bgColor)
                        .border(
                            width = if (isMid || isTarget) 1.5.dp else 1.dp,
                            color = when {
                                isTarget -> AccentGreen
                                isMid -> CrimsonAccent
                                isInRange -> BorderSubtle
                                else -> Color.Transparent
                            },
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "$value",
                            style = MaterialTheme.typography.labelMedium,
                            color = textColor,
                            fontWeight = if (isMid || isTarget) FontWeight.Bold else FontWeight.Medium
                        )
                        if (isMid && !found) {
                            Text(
                                text = "MID",
                                fontSize = 8.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        } else if (isTarget) {
                            Text(
                                text = "FOUND!",
                                fontSize = 8.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Step explanation and metrics
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(CardElevatedHover)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Step ${currentStep + 1} of 4 (Log₂ 16 = 4)",
                    style = MaterialTheme.typography.labelMedium,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = when (currentStep) {
                        0 -> "Check mid: 45 < $searchTarget → Discard left 8 items"
                        1 -> "Check mid: 89 > $searchTarget → Discard right 4 items"
                        2 -> "Check mid: 67 < $searchTarget → Discard left 2 items"
                        else -> "Target $searchTarget matched in strictly 4 steps!"
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = if (found) AccentGreen else TextSecondary,
                    fontSize = 11.sp
                )
            }

            Text(
                text = "${(high - low + 1)} left",
                style = MaterialTheme.typography.labelMedium,
                color = AccentGold,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Step buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = { currentStep = 0 },
                modifier = Modifier
                    .weight(1f)
                    .testTag("reset_binary_search"),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = TextSecondary),
                border = BorderStroke(1.dp, BorderSubtle)
            ) {
                Icon(Icons.Default.Refresh, contentDescription = "Reset", modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Reset", style = MaterialTheme.typography.labelMedium)
            }

            Button(
                onClick = {
                    if (currentStep < 3) currentStep++ else currentStep = 0
                },
                modifier = Modifier
                    .weight(1.5f)
                    .testTag("step_binary_search"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (found) AccentGreen else CrimsonAccent,
                    contentColor = if (found) Color.Black else TextPrimary
                )
            ) {
                Text(
                    text = if (found) "Restart Demo" else "Next Bisect Step",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )
                if (!found) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}

private data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)
