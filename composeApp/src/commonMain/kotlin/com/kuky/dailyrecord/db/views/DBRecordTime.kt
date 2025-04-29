package com.kuky.dailyrecord.db.views

import androidx.room.ColumnInfo
import androidx.room.DatabaseView

@DatabaseView("select distinct record_month from record")
data class DBRecordMonth(
    @ColumnInfo(name = "record_month")
    val month: Int
)

@DatabaseView("select distinct record_date from record")
data class DBRecordDate(
    @ColumnInfo(name = "record_date")
    val date: Int
)