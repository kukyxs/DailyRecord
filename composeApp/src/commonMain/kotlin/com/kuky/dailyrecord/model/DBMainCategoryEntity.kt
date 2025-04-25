package com.kuky.dailyrecord.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "main_category", primaryKeys = ["id"])
data class DBMainCategoryEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "category_name")
    val categoryName: String,
    @ColumnInfo(name = "can_be_removed")
    val canBeRemoved: Boolean,
    @ColumnInfo(name = "category_color")
    val categoryColor: String,
    @ColumnInfo(name = "is_expense")
    val isExpense: Boolean,
)