package com.kuky.dailyrecord

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import cafe.adriel.voyager.navigator.Navigator
import com.kuky.dailyrecord.configs.GlobalState
import com.kuky.dailyrecord.configs.provideUtilsModule
import com.kuky.dailyrecord.configs.repositoryModule
import com.kuky.dailyrecord.configs.testCaseModules
import com.kuky.dailyrecord.configs.viewModelModule
import com.kuky.dailyrecord.pages.splash.SplashPage
import com.kuky.dailyrecord.theme.DailyRecordTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    KoinApplication({ modules(provideUtilsModule, repositoryModule, viewModelModule, testCaseModules) }) {
        val appState = koinInject<GlobalState>()
        val theme = appState.themeTypeFlow.collectAsState()
        DailyRecordTheme(theme.value) { Navigator(SplashPage()) }
    }
}