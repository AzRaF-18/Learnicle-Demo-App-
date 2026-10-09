package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

private data class ConfettiParticle(
    val initialXRatio: Float,
    val initialYRatio: Float,
    val color: Color,
    val size: Float,
    val angleRad: Float,
    val speed: Float,
    val rotationSpeed: Float,
    val isCircle: Boolean
)

@Composable
fun ConfettiCelebration(
    triggerKey: Any?,
    modifier: Modifier = Modifier
) {
    if (triggerKey == null) return

    val progress = remember(triggerKey) { Animatable(0f) }

    val particles = remember(triggerKey) {
        val colors = listOf(
            Color(0xFFFFD700), // Gold
            Color(0xFFE50914), // Crimson Red
            Color(0xFF38EF7D), // Neon Emerald
            Color(0xFF00C6FF), // Cyan
            Color(0xFFFF758C), // Pink
            Color(0xFFFFAA00), // Amber
            Color(0xFFFFFFFF)  // White
        )
        val random = Random(triggerKey.hashCode())
        List(48) {
            // Emits from center-top of the screen (around top 15-25%)
            val initialAngle = (random.nextFloat() * Math.PI.toFloat()) // 0 to PI (mostly downwards and spread outwards)
            ConfettiParticle(
                initialXRatio = 0.5f + (random.nextFloat() - 0.5f) * 0.4f,
                initialYRatio = 0.08f + random.nextFloat() * 0.08f,
                color = colors[random.nextInt(colors.size)],
                size = 7f + random.nextFloat() * 9f,
                angleRad = initialAngle,
                speed = 280f + random.nextFloat() * 450f,
                rotationSpeed = (random.nextFloat() - 0.5f) * 720f,
                isCircle = random.nextBoolean()
            )
        }
    }

    LaunchedEffect(triggerKey) {
        progress.snapTo(0f)
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1400, easing = LinearEasing)
        )
    }

    if (progress.value < 1f) {
        val t = progress.value
        val alpha = (1f - (t * t)).coerceIn(0f, 1f)

        Canvas(modifier = modifier.fillMaxSize()) {
            val canvasW = size.width
            val canvasH = size.height

            particles.forEach { p ->
                val gravityY = 480f * t * t
                val currX = p.initialXRatio * canvasW + (cos(p.angleRad) * p.speed * t)
                val currY = p.initialYRatio * canvasH + (sin(p.angleRad) * p.speed * t) + gravityY
                val particleColor = p.color.copy(alpha = alpha)

                rotate(degrees = p.rotationSpeed * t, pivot = Offset(currX, currY)) {
                    if (p.isCircle) {
                        drawCircle(
                            color = particleColor,
                            radius = p.size / 2f,
                            center = Offset(currX, currY)
                        )
                    } else {
                        drawRect(
                            color = particleColor,
                            topLeft = Offset(currX - p.size / 2f, currY - p.size / 3f),
                            size = Size(p.size, p.size * 0.6f)
                        )
                    }
                }
            }
        }
    }
}
