package com.kuky.dailyrecord.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kuky.dailyrecord.db.views.DBRecordDate
import com.kuky.dailyrecord.db.views.DBRecordDetail
import com.kuky.dailyrecord.db.views.DBRecordMonth
import com.kuky.dailyrecord.model.DBRecordEntity

@Dao
interface RecordDao {
    @Query("select distinct record_month from record order by record_month desc")
    suspend fun allRecordMonth(): List<DBRecordMonth>

    @Query("select distinct record_date from record where record_month=:month order by record_date desc")
    suspend fun allRecordDateInMonth(month: Int): List<DBRecordDate>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: DBRecordEntity): Long

    @Query(
        "select a.id, a.mount, a.is_expense, a.remark, a.record_time, a.record_date, a.record_month, a.record_year, a.pinned_pictures, a.category_id, a.sub_category_id, " +
                "b.id as category_id, b.category_name as main_category, b.category_color as category_color, c.id as sub_category_id, c.category_name as sub_category " +
                "from record as a left join main_category as b on b.id = a.category_id left join sub_category as c on c.id = a.sub_category_id order by a.record_time desc"
    )
    suspend fun allRecords(): List<DBRecordDetail>


    @Query(
        "select a.id, a.mount, a.is_expense, a.remark, a.record_time, a.record_date, a.record_month, a.record_year, a.pinned_pictures, a.category_id, a.sub_category_id, " +
                "b.id as category_id, b.category_name as main_category, b.category_color as category_color, c.id as sub_category_id, c.category_name as sub_category " +
                "from record as a left join main_category as b on b.id = a.category_id left join sub_category as c on c.id = a.sub_category_id where a.record_month=:month order by a.record_time desc"
    )
    suspend fun recordsByMonth(month: Int): List<DBRecordDetail>

    @Query(
        "select a.id, a.mount, a.is_expense, a.remark, a.record_time, a.record_date, a.record_month, a.record_year, a.pinned_pictures, a.category_id, a.sub_category_id, " +
                "b.id as category_id, b.category_name as main_category, b.category_color as category_color, c.id as sub_category_id, c.category_name as sub_category " +
                "from record as a left join main_category as b on b.id = a.category_id left join sub_category as c on c.id = a.sub_category_id where a.record_date=:date order by a.record_time desc"
    )
    suspend fun recordsByDate(date: Int): List<DBRecordDetail>

}