package com.kuky.dailyrecord.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color

val lightTypography = Typography()

val darkTypography = Typography(
    h1 = lightTypography.h1.copy(color = Color.White),
    h2 = lightTypography.h2.copy(color = Color.White),
    h3 = lightTypography.h3.copy(color = Color.White),
    h4 = lightTypography.h4.copy(color = Color.White),
    h5 = lightTypography.h5.copy(color = Color.White),
    h6 = lightTypography.h6.copy(color = Color.White),
    subtitle1 = lightTypography.subtitle1.copy(color = Color.White),
    subtitle2 = lightTypography.subtitle2.copy(color = Color.White),
    body1 = lightTypography.body1.copy(color = Color.White),
    body2 = lightTypography.body2.copy(color = Color.White),
    button = lightTypography.button.copy(color = Color.White),
    caption = lightTypography.caption.copy(color = Color.White),
    overline = lightTypography.overline.copy(color = Color.White)
)

val ThemeType.typography
    get() = when (this) {
        ThemeType.LIGHT -> lightTypography
        ThemeType.DARK -> darkTypography
    }