package com.kuky.dailyrecord

import android.os.Build
import androidx.compose.ui.platform.LocalConfiguration

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()
actual fun getPlatformName(): String {
    return Build.BRAND
}