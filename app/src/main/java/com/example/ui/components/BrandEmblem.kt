package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CrimsonAccent
import com.example.ui.theme.CrimsonPrimary

@Composable
fun LearnicleEmblem(
    modifier: Modifier = Modifier,
    size: Dp = 80.dp
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(size * 0.25f))
            .background(
                Brush.radialGradient(
                    colors = listOf(Color(0xFF330F15), Color(0xFF14141A))
                )
            )
            .border(
                1.5.dp,
                Brush.linearGradient(
                    listOf(CrimsonAccent, Color(0xFF35151D))
                ),
                RoundedCornerShape(size * 0.25f)
            ),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(size * 0.65f)) {
            val w = this.size.width
            val h = this.size.height
            val cx = w / 2f
            val cy = h / 2f

            // Sleek isometric hexagon prism
            val r = w * 0.44f
            val path = Path()

            val angles = listOf(30.0, 90.0, 150.0, 210.0, 270.0, 330.0)
            val points = angles.map { deg ->
                val rad = Math.toRadians(deg)
                Offset(
                    (cx + r * kotlin.math.cos(rad)).toFloat(),
                    (cy + r * kotlin.math.sin(rad)).toFloat()
                )
            }

            path.moveTo(points[0].x, points[0].y)
            for (i in 1..5) {
                path.lineTo(points[i].x, points[i].y)
            }
            path.close()

            // Hexagon outline
            drawPath(
                path = path,
                color = CrimsonAccent,
                style = Stroke(width = 2.5f, cap = StrokeCap.Round)
            )

            // Inner prism radiating vertices
            for (p in points) {
                drawLine(
                    color = CrimsonAccent.copy(alpha = 0.5f),
                    start = Offset(cx, cy),
                    end = p,
                    strokeWidth = 1.5f
                )
            }

            // Central core node
            drawCircle(
                color = CrimsonAccent,
                radius = 3.5f,
                center = Offset(cx, cy)
            )
            drawCircle(
                color = Color.White,
                radius = 1.8f,
                center = Offset(cx, cy)
            )
        }
    }
}

@Composable
fun GoogleLogoIcon(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(20.dp)) {
        val w = size.width
        val h = size.height
        val cx = w / 2f
        val cy = h / 2f
        val r = w * 0.45f

        // Stylized colored G-quadrants
        // Blue top right
        drawArc(
            color = Color(0xFF4285F4),
            startAngle = 270f,
            sweepAngle = 90f,
            useCenter = false,
            style = Stroke(width = 4f, cap = StrokeCap.Round),
            size = Size(r * 2, r * 2),
            topLeft = Offset(cx - r, cy - r)
        )
        // Red top left
        drawArc(
            color = Color(0xFFEA4335),
            startAngle = 180f,
            sweepAngle = 90f,
            useCenter = false,
            style = Stroke(width = 4f, cap = StrokeCap.Round),
            size = Size(r * 2, r * 2),
            topLeft = Offset(cx - r, cy - r)
        )
        // Yellow bottom left
        drawArc(
            color = Color(0xFFFBBC05),
            startAngle = 120f,
            sweepAngle = 60f,
            useCenter = false,
            style = Stroke(width = 4f, cap = StrokeCap.Round),
            size = Size(r * 2, r * 2),
            topLeft = Offset(cx - r, cy - r)
        )
        // Green bottom right
        drawArc(
            color = Color(0xFF34A853),
            startAngle = 0f,
            sweepAngle = 120f,
            useCenter = false,
            style = Stroke(width = 4f, cap = StrokeCap.Round),
            size = Size(r * 2, r * 2),
            topLeft = Offset(cx - r, cy - r)
        )
        // Horizontal bar
        drawLine(
            color = Color(0xFF4285F4),
            start = Offset(cx - 1f, cy),
            end = Offset(cx + r, cy),
            strokeWidth = 4f,
            cap = StrokeCap.Square
        )
    }
}

@Composable
fun MicrosoftLogoIcon(modifier: Modifier = Modifier) {
    Column(modifier = modifier.size(18.dp)) {
        Row {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(Color(0xFFF25022)) // Red
            )
            Spacer(modifier = Modifier.width(2.dp))
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(Color(0xFF7FBA00)) // Green
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Row {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(Color(0xFF00A4EF)) // Blue
            )
            Spacer(modifier = Modifier.width(2.dp))
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(Color(0xFFFFB900)) // Yellow
            )
        }
    }
}
