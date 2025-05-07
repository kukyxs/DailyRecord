package com.kuky.dailyrecord.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    tableName = "record",
    foreignKeys = [
        ForeignKey(entity = DBMainCategoryEntity::class, parentColumns = ["id"], childColumns = ["category_id"]),
        ForeignKey(entity = DBSubCategoryEntity::class, parentColumns = ["id"], childColumns = ["sub_category_id"])
    ],
)
data class DBRecordEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "mount")
    val mount: String,
    @ColumnInfo(name = "is_expense")
    val isExpense: Boolean,
    @ColumnInfo(name = "remark")
    val remark: String?,
    @ColumnInfo(name = "record_time")
    val recordTime: Long,
    @ColumnInfo(name = "record_date")
    val recordDate: Int,
    @ColumnInfo(name = "record_month")
    val recordMonth: Int,
    @ColumnInfo(name = "record_year")
    val recordYear: Int,
    @ColumnInfo(name = "pinned_pictures")
    val pinnedPictures: List<String>,
    @ColumnInfo(name = "category_id")
    val categoryId: Long,
    @ColumnInfo(name = "sub_category_id")
    val subCategoryId: Long
) {
    companion object {
        fun insertIns(
            mount: String, isExpense: Boolean, remark: String?, pinnedPictures: List<String>,
            categoryId: Long, subCategoryId: Long, insertTime: Instant? = null,
        ): DBRecordEntity {
            val date = insertTime ?: Clock.System.now()
            val ds = date.toLocalDateTime(TimeZone.currentSystemDefault())
            val m = ds.monthNumber.toString().padStart(2, '0')
            val d = ds.dayOfMonth.toString().padStart(2, '0')

            return DBRecordEntity(
                null, mount, isExpense, remark, date.toEpochMilliseconds(),
                "${ds.year}${m}${d}".toInt(), "${ds.year}${m}".toInt(), ds.year, pinnedPictures, categoryId, subCategoryId
            )
        }
    }
}