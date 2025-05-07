package com.kuky.dailyrecord.pages.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kuky.dailyrecord.db.views.DBRecordDetail
import kotlinx.datetime.Instant

@Composable
fun HomeRecordItem(
    record: DBRecordDetail,
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Box(modifier = Modifier.padding(12.dp)) {
            Column {
                Text("Record ${record.mainCategory}")
                Text(Instant.fromEpochMilliseconds(record.recordTime).toString())
            }
        }
    }
}