package com.kuky.dailyrecord

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.awt.Dimension


fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "DailyRecord",
        state = rememberWindowState(width = 400.dp, height = 600.dp)
    ) {
        window.minimumSize = Dimension(400, 600)
        App()
    }
}