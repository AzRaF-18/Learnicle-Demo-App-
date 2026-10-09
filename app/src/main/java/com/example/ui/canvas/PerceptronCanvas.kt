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
import androidx.compose.ui.graphics.PathEffect
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
import kotlin.math.cos
import kotlin.math.sin

private data class DataPoint(val x: Float, val y: Float, val isPositive: Boolean)

@Composable
fun PerceptronCanvas(
    modifier: Modifier = Modifier
) {
    // Rotation angle for decision line normal vector (-PI/2 to PI/2)
    var angleDeg by remember { mutableFloatStateOf(45f) }
    var bias by remember { mutableFloatStateOf(0f) }

    // Normalized points in [-1, 1] range
    val points = remember {
        listOf(
            DataPoint(0.5f, 0.6f, true),
            DataPoint(0.7f, 0.3f, true),
            DataPoint(0.3f, 0.8f, true),
            DataPoint(0.8f, 0.7f, true),
            DataPoint(-0.4f, -0.5f, false),
            DataPoint(-0.7f, -0.2f, false),
            DataPoint(-0.3f, -0.8f, false),
            DataPoint(-0.6f, -0.6f, false)
        )
    }

    // Weights derived from angle: w1 = cos(rad), w2 = sin(rad)
    val rad = Math.toRadians(angleDeg.toDouble())
    val w1 = cos(rad).toFloat()
    val w2 = sin(rad).toFloat()

    // Calculate accuracy: point (x, y) classified positive if w1*x + w2*y + bias >= 0
    val correctlyClassified = points.count { p ->
        val score = (w1 * p.x) + (w2 * p.y) + bias
        (score >= 0f) == p.isPositive
    }
    val accuracyPercent = (correctlyClassified * 100) / points.size

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
                text = "PERCEPTRON HYPERPLANE",
                style = MaterialTheme.typography.labelSmall,
                color = CrimsonAccent,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(if (accuracyPercent >= 85) Color(0xFF0F311C) else Color(0xFF2C1016))
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "Separation: $accuracyPercent%",
                    style = MaterialTheme.typography.labelSmall,
                    color = if (accuracyPercent >= 85) AccentGreen else CrimsonAccent,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // 2D Plane Canvas
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF101015))
                .border(1.dp, BorderSubtle, RoundedCornerShape(12.dp))
        ) {
            Canvas(modifier = Modifier.matchParentSize()) {
                val cx = size.width / 2f
                val cy = size.height / 2f
                val scale = size.minDimension * 0.42f

                // Draw Coordinate Axes
                val dash = PathEffect.dashPathEffect(floatArrayOf(6f, 6f), 0f)
                drawLine(
                    color = BorderSubtle,
                    start = Offset(0f, cy),
                    end = Offset(size.width, cy),
                    strokeWidth = 1f,
                    pathEffect = dash
                )
                drawLine(
                    color = BorderSubtle,
                    start = Offset(cx, 0f),
                    end = Offset(cx, size.height),
                    strokeWidth = 1f,
                    pathEffect = dash
                )

                // Decision boundary: line perpendicular to (w1, w2)
                // Equation: w1*x + w2*y + bias = 0
                // Tangent vector is (-w2, w1)
                val lineLength = size.maxDimension * 1.2f
                val lineCenterX = cx - (w1 * bias * scale)
                val lineCenterY = cy + (w2 * bias * scale)

                val dirX = -w2
                val dirY = w1

                val start = Offset(lineCenterX - dirX * lineLength, lineCenterY + dirY * lineLength)
                val end = Offset(lineCenterX + dirX * lineLength, lineCenterY - dirY * lineLength)

                // Draw decision boundary line with glow
                drawLine(
                    color = CrimsonAccent.copy(alpha = 0.35f),
                    start = start,
                    end = end,
                    strokeWidth = 6f
                )
                drawLine(
                    color = CrimsonAccent,
                    start = start,
                    end = end,
                    strokeWidth = 2.5f
                )

                // Draw Data Points
                points.forEach { p ->
                    val px = cx + (p.x * scale)
                    val py = cy - (p.y * scale)
                    val score = (w1 * p.x) + (w2 * p.y) + bias
                    val isCorrect = (score >= 0f) == p.isPositive

                    val pointColor = if (p.isPositive) AccentCyan else CrimsonAccent

                    // Outer halo if correct
                    if (isCorrect) {
                        drawCircle(
                            color = pointColor.copy(alpha = 0.25f),
                            radius = 12f,
                            center = Offset(px, py)
                        )
                    }
                    drawCircle(
                        color = pointColor,
                        radius = 6.5f,
                        center = Offset(px, py)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Angle Slider
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Orientation θ: ${angleDeg.toInt()}°",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
            Text(
                text = "Weights: (${String.format("%.2f", w1)}, ${String.format("%.2f", w2)})",
                style = MaterialTheme.typography.bodySmall,
                color = TextMuted,
                fontSize = 11.sp
            )
        }
        Slider(
            value = angleDeg,
            onValueChange = { angleDeg = it },
            valueRange = 0f..180f,
            colors = SliderDefaults.colors(
                thumbColor = CrimsonAccent,
                activeTrackColor = CrimsonAccent,
                inactiveTrackColor = CardElevatedHover
            ),
            modifier = Modifier.height(30.dp)
        )

        // Bias Slider
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Bias offset (b): ${String.format("%.2f", bias)}",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
            Text(
                text = "Shifts hyperplane parallel",
                style = MaterialTheme.typography.bodySmall,
                color = TextMuted,
                fontSize = 11.sp
            )
        }
        Slider(
            value = bias,
            onValueChange = { bias = it },
            valueRange = -0.8f..0.8f,
            colors = SliderDefaults.colors(
                thumbColor = AccentCyan,
                activeTrackColor = AccentCyan,
                inactiveTrackColor = CardElevatedHover
            ),
            modifier = Modifier.height(30.dp)
        )
    }
}
