package com.kuky.dailyrecord.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    tableName = "record", primaryKeys = ["id"],
    foreignKeys = [
        ForeignKey(entity = DBMainCategoryEntity::class, parentColumns = ["id"], childColumns = ["category_id"]),
        ForeignKey(entity = DBSubCategoryEntity::class, parentColumns = ["id"], childColumns = ["sub_category_id"])
    ],
)
data class DBRecordEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) val id: Long?,
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
    @ColumnInfo(name = "category_id")
    val categoryId: Long,
    @ColumnInfo(name = "sub_category_id")
    val subCategoryId: Long
)