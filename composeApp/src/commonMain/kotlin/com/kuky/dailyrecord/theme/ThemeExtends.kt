package com.kuky.dailyrecord.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


/**
 * 主题扩展
 * @property alertColor Color
 * @constructor
 */
data class ExtendColor(
    val alertColor: Color,
    val onAlertColor: Color,
)

val LocalExtendColors = staticCompositionLocalOf {
    ExtendColor(Color.Unspecified, Color.Unspecified)
}

val ThemeType.extendColors
    get() = when (this) {
        ThemeType.LIGHT -> ExtendColor(Color.Red, Color.White)
        ThemeType.DARK -> ExtendColor(Color(red = 51, green = 2, blue = 2, alpha = 255), Color.White)
    }


object ExtendTheme {
    val colors: ExtendColor
        @Composable
        get() = LocalExtendColors.current
}