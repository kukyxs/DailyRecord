package com.kuky.dailyrecord.pages.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.kuky.dailyrecord.db.views.DBRecordDetail
import com.kuky.dailyrecord.extension.getTime
import com.kuky.dailyrecord.extension.toColor
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
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier.padding(end = 4.dp)
                            .clip(CircleShape)
                            .background(record.categoryColor.toColor())
                            .size(12.dp)
                    )

                    Text("${record.mainCategory}-${record.subCategory}", style = MaterialTheme.typography.subtitle1)

                    Box(Modifier.weight(1f))

                    Text(record.mount, style = MaterialTheme.typography.subtitle1)
                }

                record.remark?.let {
                    Text(
                        it, modifier = Modifier.padding(top = 4.dp, start = 16.dp)
                    )
                }

                Text(
                    Instant.fromEpochMilliseconds(record.recordTime).getTime(),
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}