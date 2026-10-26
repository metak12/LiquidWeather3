package com.example.liquidweather3.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun WeatherGraph(
    temperatures: List<Float>,
    hours: List<String>,
    modifier: Modifier = Modifier
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val textColor = MaterialTheme.colorScheme.onSurface

    Column(modifier = modifier.fillMaxWidth().padding(16.dp)) {
        Text(
            text = "Почасовой график температуры",
            style = MaterialTheme.typography.titleMedium,
            color = textColor
        )
        Spacer(modifier = Modifier.height(16.dp))

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            if (temperatures.isEmpty()) return@Canvas

            val maxTemp = temperatures.maxOrNull() ?: 0f
            val minTemp = temperatures.minOrNull() ?: 0f
            val tempRange = if (maxTemp == minTemp) 1f else maxTemp - minTemp

            val widthStep = size.width / (temperatures.size - 1).coerceAtLeast(1)
            val points = temperatures.mapIndexed { index, temp ->
                val x = index * widthStep
                // Нормализуем по высоте canvas (инвертируем Y, так как в Compose 0 сверху)
                val y = size.height - ((temp - minTemp) / tempRange) * (size.height - 40f) - 20f
                Offset(x, y)
            }

            // Отрисовка линии графика
            val path = Path().apply {
                if (points.isNotEmpty()) {
                    moveTo(points[0].x, points[0].y)
                    for (i in 1 until points.size) {
                        lineTo(points[i].x, points[i].y)
                    }
                }
            }

            drawPath(
                path = path,
                color = primaryColor,
                style = Stroke(width = 4.dp.toPx())
            )

            // Отрисовка точек на значениях
            points.forEach { point ->
                drawCircle(
                    color = primaryColor,
                    radius = 6.dp.toPx(),
                    center = point
                )
            }
        }
    }
}
