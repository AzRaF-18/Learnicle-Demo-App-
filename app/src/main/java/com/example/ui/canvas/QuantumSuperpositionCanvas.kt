package com.example.ui.canvas

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AccentCyan
import com.example.ui.theme.AccentGold
import com.example.ui.theme.AccentGreen
import com.example.ui.theme.AccentPurple
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CardElevated
import com.example.ui.theme.CardElevatedHover
import com.example.ui.theme.CrimsonAccent
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun QuantumSuperpositionCanvas(
    modifier: Modifier = Modifier
) {
    // Angle in degrees between 0 and 90 defining amplitudes:
    // alpha = cos(theta), beta = sin(theta)
    var thetaDeg by remember { mutableFloatStateOf(45f) }
    var collapsedResult by remember { mutableStateOf<String?>(null) } // null = coherent superposition

    val thetaRad = Math.toRadians(thetaDeg.toDouble())
    val alpha = cos(thetaRad).toFloat()
    val beta = sin(thetaRad).toFloat()

    val prob0 = (alpha * alpha)
    val prob1 = (beta * beta)

    val animatedProb0 by animateFloatAsState(targetValue = prob0, animationSpec = tween(200), label = "p0")
    val animatedProb1 by animateFloatAsState(targetValue = prob1, animationSpec = tween(200), label = "p1")

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
                text = "QUANTUM SUPERPOSITION |ψ⟩",
                style = MaterialTheme.typography.labelSmall,
                color = CrimsonAccent,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(if (collapsedResult == null) Color(0xFF23102C) else Color(0xFF2C1016))
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    text = if (collapsedResult == null) "Coherent Wave" else "COLLAPSED: $collapsedResult",
                    style = MaterialTheme.typography.labelSmall,
                    color = if (collapsedResult == null) AccentPurple else CrimsonAccent,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Bloch Circle / Wave Amplitude Canvas
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF101015))
                .border(1.dp, BorderSubtle, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.matchParentSize()) {
                val cx = size.width / 2f
                val cy = size.height / 2f
                val radius = size.height * 0.42f

                // Draw unit probability circle
                drawCircle(
                    color = BorderSubtle,
                    radius = radius,
                    center = Offset(cx, cy),
                    style = Stroke(width = 1.5f)
                )

                // Basis axis: Horizontal (|0⟩) and Vertical (|1⟩)
                drawLine(
                    color = BorderSubtle.copy(alpha = 0.5f),
                    start = Offset(cx - radius - 15f, cy),
                    end = Offset(cx + radius + 15f, cy),
                    strokeWidth = 1f
                )
                drawLine(
                    color = BorderSubtle.copy(alpha = 0.5f),
                    start = Offset(cx, cy - radius - 15f),
                    end = Offset(cx, cy + radius + 15f),
                    strokeWidth = 1f
                )

                if (collapsedResult == null) {
                    // Vector |ψ⟩ = cos(θ)|0⟩ + sin(θ)|1⟩
                    val vx = cx + (alpha * radius)
                    val vy = cy - (beta * radius)

                    // Draw amplitude vector
                    drawLine(
                        color = CrimsonAccent,
                        start = Offset(cx, cy),
                        end = Offset(vx, vy),
                        strokeWidth = 3f,
                        cap = StrokeCap.Round
                    )
                    drawCircle(
                        color = CrimsonAccent,
                        radius = 5f,
                        center = Offset(vx, vy)
                    )
                    // Glow around tip
                    drawCircle(
                        color = CrimsonAccent.copy(alpha = 0.3f),
                        radius = 12f,
                        center = Offset(vx, vy)
                    )
                } else {
                    // Flash collapsed state
                    val isZero = collapsedResult == "|0⟩ Alive"
                    val collapsedColor = if (isZero) AccentGreen else CrimsonAccent
                    val targetX = if (isZero) cx + radius else cx
                    val targetY = if (isZero) cy else cy - radius

                    drawLine(
                        color = collapsedColor,
                        start = Offset(cx, cy),
                        end = Offset(targetX, targetY),
                        strokeWidth = 5f,
                        cap = StrokeCap.Round
                    )
                    drawCircle(
                        color = collapsedColor,
                        radius = 16f,
                        center = Offset(targetX, targetY)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // State vector equation and probabilities
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(CardElevatedHover)
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "|0⟩ Alive: ${(prob0 * 100).toInt()}%",
                    style = MaterialTheme.typography.bodySmall,
                    color = AccentGreen,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "|1⟩ Decayed: ${(prob1 * 100).toInt()}%",
                    style = MaterialTheme.typography.bodySmall,
                    color = CrimsonAccent,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            LinearProgressIndicator(
                progress = { animatedProb0 },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = AccentGreen,
                trackColor = CrimsonAccent
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Born Rule: |α|² + |β|² = 1.00 (Strict 100% Conservation)",
                style = MaterialTheme.typography.bodySmall,
                color = TextMuted,
                fontSize = 10.sp
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Amplitude angle slider (only if coherent)
        if (collapsedResult == null) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "State Phase θ: ${thetaDeg.toInt()}°",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
                Text(
                    text = "α = ${String.format("%.2f", alpha)}, β = ${String.format("%.2f", beta)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMuted,
                    fontSize = 11.sp
                )
            }
            Slider(
                value = thetaDeg,
                onValueChange = { thetaDeg = it },
                valueRange = 0f..90f,
                colors = SliderDefaults.colors(
                    thumbColor = CrimsonAccent,
                    activeTrackColor = CrimsonAccent,
                    inactiveTrackColor = CardElevatedHover
                ),
                modifier = Modifier.height(30.dp)
            )
        }

        // Action Buttons: Measure / Collapse or Reset
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (collapsedResult != null) {
                Button(
                    onClick = { collapsedResult = null },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("reset_superposition"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AccentPurple,
                        contentColor = TextPrimary
                    )
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Re-establish Superposition", style = MaterialTheme.typography.labelMedium)
                }
            } else {
                Button(
                    onClick = {
                        val sample = Random.nextFloat()
                        collapsedResult = if (sample < prob0) "|0⟩ Alive" else "|1⟩ Decayed"
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("measure_wavefunction"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CrimsonAccent,
                        contentColor = TextPrimary
                    )
                ) {
                    Icon(Icons.Default.Visibility, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Open Box / Measure Wavefunction", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
