package com.kuky.dailyrecord.pages.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.NavigationRail
import androidx.compose.material.NavigationRailItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kuky.dailyrecord.composeapp.generated.resources.Res
import com.kuky.dailyrecord.composeapp.generated.resources.compose_multiplatform
import com.kuky.dailyrecord.pages.home.HomeViewModel
import org.jetbrains.compose.resources.painterResource

@Composable
fun HomeDesktopMode(maxWidth: Dp, viewModel: HomeViewModel) {
    val state by viewModel.state.collectAsState()
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

            items(state.recordList.size, key = { state.recordList[it].id!! }) {
                val record = state.recordList[it]
                HomeRecordItem(record)
            }
        }
    }
}