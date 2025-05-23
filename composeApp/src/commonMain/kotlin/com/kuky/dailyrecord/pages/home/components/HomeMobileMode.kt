package com.kuky.dailyrecord.pages.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.kuky.dailyrecord.composeapp.generated.resources.Res
import com.kuky.dailyrecord.composeapp.generated.resources.compose_multiplatform
import com.kuky.dailyrecord.pages.edit.RecordEditPage
import com.kuky.dailyrecord.pages.home.HomeViewModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeMobileMode(viewModel: HomeViewModel) {
    val nav = LocalNavigator.currentOrThrow
    val state by viewModel.state.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            Text("Home")
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                nav.push(RecordEditPage())
            }) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
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
                LazyRow(Modifier.padding(horizontal = 4.dp)) {
                    items(state.monthList.size, key = { state.monthList[it].month }) {
                        val month = state.monthList[it]
                        HomeRecordMonthItem(month, it == state.selIndex) {
                            viewModel.getRecordInMonth(state.monthList[it], it)
                        }
                    }
                }
            }

            state.recordGroup.forEach { recordList ->
                stickyHeader(key = "${recordList.first().recordDate}") {
                    HomeRecordDateItem(recordList.first().recordDate)
                }

                items(recordList.size, key = { recordList[it].id!! }) {
                    HomeRecordItem(recordList[it])
                }
            }
        }
    }
}