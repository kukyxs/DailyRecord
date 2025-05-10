package com.kuky.dailyrecord.testcase

import com.kuky.dailyrecord.db.getDatabase
import com.kuky.dailyrecord.model.DBMainCategoryEntity
import com.kuky.dailyrecord.model.DBRecordEntity
import com.kuky.dailyrecord.model.DBSubCategoryEntity
import kotlinx.datetime.Instant

class TestRecordCase {
    private val database by lazy { getDatabase() }

    suspend fun insertRecord(time: Instant) {
        database.recordDao().insertRecord(
            DBRecordEntity.insertIns(
                "10", true, "remark", listOf(),
                1, 1, time
            )
        )
    }
}