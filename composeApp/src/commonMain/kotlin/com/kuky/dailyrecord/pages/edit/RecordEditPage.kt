package com.kuky.dailyrecord.pages.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.kuky.dailyrecord.pages.edit.components.RecordCategories
import kotlinx.coroutines.launch
import org.koin.compose.koinInject


class RecordEditPage(private val id: Long? = null) : Screen {
    @Composable
    override fun Content() {
        val nav = LocalNavigator.currentOrThrow
        val viewModel = koinInject<RecordEditViewModel>()
        val state by viewModel.state.collectAsState()
        val scope = rememberCoroutineScope()

        LaunchedEffect(Unit) {
            viewModel.allMainCategories()
            if (id != null) viewModel.findRecordById(id)
        }

        Scaffold(topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { nav.pop() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null,
                            tint = MaterialTheme.colors.onBackground
                        )
                    }
                },
                title = { Text(if (id == null) "新建账单" else "编辑账单") },
                backgroundColor = MaterialTheme.colors.background,
                actions = {
                    IconButton(onClick = {}) {

                    }
                }
            )
        }) {
            Column {
                RecordCategories(
                    state.mainCategoryList, state.subCategoryList,
                    0, 0, modifier = Modifier.weight(1f),
                    onMainClick = { scope.launch { viewModel.onMainCategoryChange(it) } },
                    onSubClick = { scope.launch { viewModel.onSubCategoryChange(it) } }
                )

                Box(Modifier.fillMaxWidth().background(Color.Red).height(200.dp))
            }
        }
    }
}