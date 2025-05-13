package com.kuky.dailyrecord.pages.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.NavigationRail
import androidx.compose.material.NavigationRailItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.kuky.dailyrecord.composeapp.generated.resources.Res
import com.kuky.dailyrecord.composeapp.generated.resources.compose_multiplatform
import com.kuky.dailyrecord.composeapp.generated.resources.md_add
import com.kuky.dailyrecord.configs.GlobalState
import com.kuky.dailyrecord.pages.edit.RecordEditPage
import com.kuky.dailyrecord.pages.home.HomeViewModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Composable
fun HomeDesktopMode(maxWidth: Dp, viewModel: HomeViewModel) {
    val nav = LocalNavigator.currentOrThrow
    val globalState = koinInject<GlobalState>()
    val state by viewModel.state.collectAsState()
    val gridColumns = when {
        maxWidth < 900.dp -> 1
        maxWidth < 1600.dp -> 2
        else -> 3
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                nav.push(RecordEditPage())
            }) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    ) {
        Row {
            NavigationRail {
                NavigationRailItem(
                    true,
                    label = { Text("Home") },
                    icon = { Image(painterResource(Res.drawable.compose_multiplatform), contentDescription = null, modifier = Modifier.size(30.dp)) },
                    onClick = { globalState.changeThemeType() },
                )
            }

            LazyVerticalGrid(columns = GridCells.Fixed(gridColumns)) {
                item(span = { GridItemSpan(gridColumns) }) {
                    LazyRow(Modifier.padding(horizontal = 8.dp)) {
                        items(state.monthList.size, key = { state.monthList[it].month }) {
                            val month = state.monthList[it]
                            HomeRecordMonthItem(month, it == state.selIndex) {
                                viewModel.getRecordInMonth(state.monthList[it], it)
                            }
                        }
                    }
                }

                state.recordGroup.forEachIndexed { index, recordList ->
                    item(span = { GridItemSpan(gridColumns) }) {
                        HomeRecordDateItem(recordList.first().recordDate, 1, index == 0)
                    }

                    items(recordList.size, key = { recordList[it].id!! }) {
                        HomeRecordItem(recordList[it])
                    }
                }
            }
        }
    }
}