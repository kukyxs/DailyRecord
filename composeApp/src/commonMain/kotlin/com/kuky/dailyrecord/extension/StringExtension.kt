package com.kuky.dailyrecord.extension

import androidx.compose.ui.graphics.Color


fun String.toColor(): Color {
    val cleaned = removePrefix("#").uppercase()
    val (a, r, g, b) = when (cleaned.length) {
        6 -> listOf(0xFF, cleaned.substring(0, 2), cleaned.substring(2, 4), cleaned.substring(4, 6))
        8 -> listOf(cleaned.substring(0, 2), cleaned.substring(2, 4), cleaned.substring(4, 6), cleaned.substring(6, 8))
        else -> throw IllegalArgumentException("Invalid hex color format")
    }

    return Color(
        alpha = "$a".toInt(16),
        red = "$r".toInt(16),
        green = "$g".toInt(16),
        blue = "$b".toInt(16)
    )
}