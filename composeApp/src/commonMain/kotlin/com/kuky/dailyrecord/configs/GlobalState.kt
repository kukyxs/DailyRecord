package com.kuky.dailyrecord.configs

import com.kuky.dailyrecord.theme.ThemeType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GlobalState {
    private val _themeTypeFlow = MutableStateFlow(ThemeType.LIGHT)
    val themeTypeFlow: StateFlow<ThemeType> = _themeTypeFlow

    fun changeThemeType() {
        _themeTypeFlow.value = when (_themeTypeFlow.value) {
            ThemeType.LIGHT -> ThemeType.DARK
            ThemeType.DARK -> ThemeType.LIGHT
        }
    }
}