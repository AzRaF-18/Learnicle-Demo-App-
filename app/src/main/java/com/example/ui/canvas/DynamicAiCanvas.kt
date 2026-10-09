package com.example.ui.canvas

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AccentCyan
import com.example.ui.theme.AccentGreen
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CardElevated
import com.example.ui.theme.CardElevatedHover
import com.example.ui.theme.CrimsonAccent
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import kotlin.math.sin

@Composable
fun DynamicAiCanvas(
    promptDescription: String,
    modifier: Modifier = Modifier
) {
    var parameterValue by remember { mutableFloatStateOf(0.5f) }

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
                text = "DYNAMIC INTUITION MODEL",
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
                    text = "Parameter λ: ${String.format("%.2f", parameterValue)}",
                    style = MaterialTheme.typography.labelSmall,
                    color = CrimsonAccent,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Visual Canvas
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF101015))
                .border(1.dp, BorderSubtle, RoundedCornerShape(12.dp))
        ) {
            Canvas(modifier = Modifier.matchParentSize()) {
                val w = size.width
                val h = size.height
                val midY = h / 2f

                // Draw subtle grid lines
                for (i in 1..3) {
                    val y = h * (i / 4f)
                    drawLine(
                        color = BorderSubtle.copy(alpha = 0.4f),
                        start = Offset(0f, y),
                        end = Offset(w, y),
                        strokeWidth = 1f
                    )
                }

                // Dynamic reactive wave/curve path
                val path = Path()
                val freq = 1f + (parameterValue * 5f)
                val amp = (h * 0.32f) * (0.4f + parameterValue * 0.6f)

                for (x in 0..w.toInt() step 4) {
                    val xf = x.toFloat()
                    val normalizedX = xf / w
                    val yf = midY - (sin(normalizedX * freq * Math.PI.toFloat() * 2f) * amp)
                    if (x == 0) {
                        path.moveTo(xf, yf)
                    } else {
                        path.lineTo(xf, yf)
                    }
                }

                // Glow path
                drawPath(
                    path = path,
                    color = CrimsonAccent.copy(alpha = 0.25f),
                    style = Stroke(width = 8f, cap = StrokeCap.Round)
                )

                // Main path
                drawPath(
                    path = path,
                    color = CrimsonAccent,
                    style = Stroke(width = 2.5f, cap = StrokeCap.Round)
                )

                // Probe point at current parameter position
                val probeX = w * parameterValue
                val probeY = midY - (sin(parameterValue * freq * Math.PI.toFloat() * 2f) * amp)

                drawCircle(
                    color = AccentCyan.copy(alpha = 0.3f),
                    radius = 14f,
                    center = Offset(probeX, probeY)
                )
                drawCircle(
                    color = AccentCyan,
                    radius = 6f,
                    center = Offset(probeX, probeY)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = promptDescription.ifBlank { "Explore system dynamics and inspect the phase response." },
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary,
            fontSize = 11.sp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "System Intensity Control",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
            Text(
                text = "${(parameterValue * 100).toInt()}%",
                style = MaterialTheme.typography.bodySmall,
                color = AccentCyan,
                fontWeight = FontWeight.Bold
            )
        }

        Slider(
            value = parameterValue,
            onValueChange = { parameterValue = it },
            valueRange = 0.05f..1f,
            colors = SliderDefaults.colors(
                thumbColor = CrimsonAccent,
                activeTrackColor = CrimsonAccent,
                inactiveTrackColor = CardElevatedHover
            ),
            modifier = Modifier.height(30.dp)
        )
    }
}
