package com.kuky.dailyrecord.pages.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kuky.dailyrecord.db.views.DBRecordMonth

@Composable
fun HomeRecordMonthItem(
    month: DBRecordMonth, sel: Boolean,
    onMonthChangeTap: (() -> Unit)? = null
) {
    Box(
        Modifier.clickable(
            indication = null, interactionSource = MutableInteractionSource(),
            onClick = onMonthChangeTap ?: {}
        ).padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(
            month.formatMonth(), fontSize = if (sel) 16.sp else 14.sp,
            color = if (sel) MaterialTheme.colors.primary else Color.Unspecified,
        )
    }
}