package com.kuky.dailyrecord.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

enum class ThemeType { LIGHT, DARK }

@Composable
fun DailyRecordTheme(
    themeType: ThemeType = ThemeType.LIGHT,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalExtendColors provides themeType.extendColors
    ) {
        MaterialTheme(
            colors = themeType.themeColors,
            content = content,
            typography = themeType.typography,
        )
    }
}