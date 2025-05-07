package com.kuky.dailyrecord.pages.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kuky.dailyrecord.composeapp.generated.resources.Res
import com.kuky.dailyrecord.composeapp.generated.resources.compose_multiplatform
import com.kuky.dailyrecord.pages.home.HomeViewModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@Composable
fun HomeMobileMode(viewModel: HomeViewModel) {
    val state by viewModel.state.collectAsState()
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
            item {
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