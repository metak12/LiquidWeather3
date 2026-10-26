package com.example.liquidweather3.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.*
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.*
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.example.liquidweather3.ui.MainActivity

class WeatherWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = WeatherGlanceAppWidget()
}

class WeatherGlanceAppWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            GlanceTheme {
                WeatherWidgetContent()
            }
        }
    }

    @Composable
    fun WeatherWidgetContent() {
        val size = LocalSize.current
        val isLarge = size.height >= 200.dp && size.width >= 250.dp
        val isMedium = size.height >= 120.dp

        Box(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(GlanceTheme.colors.surfaceVariant)
                .padding(16.dp)
                .clickable(actionStartActivity<MainActivity>()),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalAlignment = Alignment.Vertical.CenterVertically,
                horizontalAlignment = Alignment.Horizontal.CenterHorizontally
            ) {
                // Минимальный размер (2x1) — температура и город
                Row(verticalAlignment = Alignment.Vertical.CenterVertically) {
                    Text(
                        text = "+18°C",
                        style = TextStyle(fontSize = 28.sp, color = GlanceTheme.colors.onSurface)
                    )
                    Spacer(modifier = GlanceModifier.width(8.dp))
                    Text(
                        text = "Москва",
                        style = TextStyle(fontSize = 16.sp, color = GlanceTheme.colors.onSurfaceVariant)
                    )
                }

                // Средний размер (2x2 / 4x2) — добавляем влажность и ветер
                if (isMedium) {
                    Spacer(modifier = GlanceModifier.height(8.dp))
                    Text(
                        text = "Влажность: 65% | Ветер: 4 м/с",
                        style = TextStyle(fontSize = 12.sp, color = GlanceTheme.colors.onSurfaceVariant)
                    )
                }

                // Максимальный размер (4x4) — полная сводка и УФ-индекс
                if (isLarge) {
                    Spacer(modifier = GlanceModifier.height(12.dp))
                    Text(
                        text = "Ощущается как +19°C. УФ-индекс: 3. Днем солнечно, без осадков.",
                        style = TextStyle(fontSize = 12.sp, color = GlanceTheme.colors.onSurface)
                    )
                }
            }
        }
    }
}
