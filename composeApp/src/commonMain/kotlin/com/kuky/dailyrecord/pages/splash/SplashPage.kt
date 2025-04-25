package com.kuky.dailyrecord.pages.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.kuky.dailyrecord.composeapp.generated.resources.Res
import com.kuky.dailyrecord.composeapp.generated.resources.app_icon
import com.kuky.dailyrecord.configs.GlobalState
import com.kuky.dailyrecord.pages.home.HomePage
import com.kuky.dailyrecord.theme.ExtendTheme
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

class SplashPage : Screen {
    @Composable
    override fun Content() {
        val appState = koinInject<GlobalState>()
        val nav = LocalNavigator.currentOrThrow

        LaunchedEffect(Unit) {
            delay(5000)
            nav.replace(HomePage())
        }

        Box(modifier = Modifier.background(MaterialTheme.colors.background).fillMaxSize()) {
            Column(
                modifier = Modifier.align(Alignment.BottomCenter)
                    .padding(bottom = 20.dp)
                    .clickable(interactionSource = MutableInteractionSource(), indication = null) {
                        appState.changeThemeType()
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
            )
            {
                Image(contentDescription = null, painter = painterResource(Res.drawable.app_icon))
                Text(text = "PureRecord", modifier = Modifier.padding(top = 8.dp))
                Box(modifier = Modifier.padding(top = 20.dp).size(20.dp).clip(CircleShape).background(ExtendTheme.colors.alertColor))
            }
        }
    }
}