package com.kuky.dailyrecord.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors


val lightColorScheme = lightColors()

val darkColorScheme = darkColors()

val ThemeType.themeColors
    get() = when (this) {
        ThemeType.LIGHT -> lightColorScheme
        ThemeType.DARK -> darkColorScheme
    }