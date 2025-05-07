package com.kuky.dailyrecord.db.views

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import kotlinx.serialization.Serializable

@Serializable
@DatabaseView("select distinct record_month from record")
data class DBRecordMonth(
    @ColumnInfo(name = "record_month")
    val month: Int
) {
    fun formatMonth(): String {
        val year = "$month".take(4)
        val month = "$month".takeLast(2)
        return "${year}年${month}月"
    }
}

@DatabaseView("select distinct record_date from record")
data class DBRecordDate(
    @ColumnInfo(name = "record_date")
    val date: Int
)