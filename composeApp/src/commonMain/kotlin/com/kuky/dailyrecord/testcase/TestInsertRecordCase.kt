package com.kuky.dailyrecord.testcase

import com.kuky.dailyrecord.db.getDatabase
import com.kuky.dailyrecord.model.DBRecordEntity
import kotlinx.datetime.Instant

class TestInsertRecordCase {
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