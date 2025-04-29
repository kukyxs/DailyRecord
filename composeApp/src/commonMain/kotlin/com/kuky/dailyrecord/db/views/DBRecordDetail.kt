package com.kuky.dailyrecord.db.views

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import kotlinx.serialization.Serializable

@Serializable
@DatabaseView(
    "select a.id, a.mount, a.is_expense, a.remark, a.record_time, a.record_date, a.record_month, a.record_year, a.pinned_pictures, a.category_id, a.sub_category_id, " +
            "b.id as category_id, b.category_name as main_category, b.category_color as category_color, c.id as sub_category_id, c.category_name as sub_category " +
            "from record as a left join main_category as b on b.id = a.category_id left join sub_category as c on c.id = a.sub_category_id",
    viewName = "record_detail"
)
data class DBRecordDetail(
    @ColumnInfo(name = "id")
    val id: Long?,
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
    @ColumnInfo(name = "main_category")
    val mainCategory: String,
    @ColumnInfo(name = "sub_category_id")
    val subCategoryId: Long,
    @ColumnInfo(name = "sub_category")
    val subCategory: String,
    @ColumnInfo(name = "category_color")
    val categoryColor: String,
)