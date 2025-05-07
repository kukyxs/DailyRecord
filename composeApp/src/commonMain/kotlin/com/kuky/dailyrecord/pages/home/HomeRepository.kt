package com.kuky.dailyrecord.pages.home

import com.kuky.dailyrecord.db.getDatabase
import com.kuky.dailyrecord.db.views.DBRecordMonth

class HomeRepository() {
    private val database by lazy { getDatabase() }

    suspend fun getAllMonth() = database.recordDao().allRecordMonth()

    suspend fun getAllRecordInMonth(month: DBRecordMonth) = database.recordDao().recordsByMonth(month.month)

    suspend fun allRecords() = database.recordDao().allRecords()
}