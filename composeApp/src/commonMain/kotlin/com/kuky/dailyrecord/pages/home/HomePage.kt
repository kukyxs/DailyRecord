package com.kuky.dailyrecord.pages.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.NavigationRail
import androidx.compose.material.NavigationRailItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.kuky.dailyrecord.composeapp.generated.resources.Res
import com.kuky.dailyrecord.composeapp.generated.resources.compose_multiplatform
import com.kuky.dailyrecord.db.getDatabase
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource

class HomePage : Screen {
    @Composable
    override fun Content() {
        LaunchedEffect(Unit){
            val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            println(now.monthNumber.toString().padStart(2, '0'))
        }
        BoxWithConstraints {
            ResponsiveHomePage(maxWidth)
        }
    }

    @Composable
    fun ResponsiveHomePage(maxWidth: Dp) {
        if (maxWidth <= 600.dp) {
            MobileHomePage()
        } else {
            DesktopHomePage(maxWidth)
        }
    }

    @Composable
    fun DesktopHomePage(maxWidth: Dp) {
        val gridColumns = if (maxWidth < 1500.dp) 2 else 3

        Row {
            NavigationRail {
                NavigationRailItem(
                    true,
                    label = { Text("Home") },
                    icon = { Image(painterResource(Res.drawable.compose_multiplatform), contentDescription = null, modifier = Modifier.size(30.dp)) },
                    onClick = {},
                )
            }

            LazyVerticalGrid(columns = GridCells.Fixed(gridColumns)) {
                items(100) {
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Box(modifier = Modifier.padding(12.dp)) {
                            Text("Item $it")
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun MobileHomePage() {
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()

        Scaffold(
            scaffoldState = scaffoldState,
            drawerContent = {
                Text("Home")
            },
            topBar = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 20.dp, horizontal = 12.dp)
                        .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) {
                            scope.launch { scaffoldState.drawerState.open() }
                        }) {
                    Image(
                        painterResource(Res.drawable.compose_multiplatform), contentDescription = null,
                        modifier = Modifier.size(40.dp).padding(end = 4.dp)
                    )
                    Text("PureRecord", style = MaterialTheme.typography.h5)
                }
            }) {

            LazyColumn {
                items(100) {
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Box(modifier = Modifier.padding(12.dp)) {
                            Text("Item $it")
                        }
                    }
                }
            }
        }
    }
}
