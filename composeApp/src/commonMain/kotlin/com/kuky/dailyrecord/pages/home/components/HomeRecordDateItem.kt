package com.kuky.dailyrecord.pages.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeRecordDateItem(date: Int, mode: Int = 0, first: Boolean = false) {
    val month = "$date".substring(4, 6)
    val day = "$date".substring(6, 8)

    if (mode == 0)
        Box(
            modifier = Modifier.fillMaxWidth()
                .background(MaterialTheme.colors.background)
                .padding(12.dp)
        ) {
            Text(text = "${month}月${day}日")
        }
    else
        Card(
            modifier = Modifier.fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, top = if (first) 4.dp else 12.dp, bottom = 4.dp)
        ) {
            Box(Modifier.padding(12.dp)) {
                Text(text = "${month}月${day}日")
            }
        }
}