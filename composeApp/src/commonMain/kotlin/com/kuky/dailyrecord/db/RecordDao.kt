package com.kuky.dailyrecord.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kuky.dailyrecord.model.DBRecordEntity

@Dao
interface RecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: DBRecordEntity)

    @Query("SELECT * FROM record")
    suspend fun allRecords(): List<DBRecordEntity>
}