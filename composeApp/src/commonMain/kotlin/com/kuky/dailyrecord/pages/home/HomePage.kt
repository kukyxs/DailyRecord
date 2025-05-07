package com.kuky.dailyrecord.pages.home

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.kuky.dailyrecord.pages.home.components.HomeDesktopMode
import com.kuky.dailyrecord.pages.home.components.HomeMobileMode
import org.koin.compose.koinInject

class HomePage : Screen {
    @Composable
    override fun Content() {
        val viewModel = koinInject<HomeViewModel>()

        LaunchedEffect(Unit) {
            viewModel.fetchAllMonthInRecord()
        }

        BoxWithConstraints {
            if (maxWidth <= 600.dp) {
                HomeMobileMode(viewModel)
            } else {
                HomeDesktopMode(maxWidth, viewModel)
            }
        }
    }
}
