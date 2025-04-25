package com.kuky.dailyrecord.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "sub_category",
    primaryKeys = ["id"],
    foreignKeys = [ForeignKey(
        entity = DBMainCategoryEntity::class, parentColumns = ["id"], childColumns = ["parent_id"],
        onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE
    )]
)
data class DBSubCategoryEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "category_id") val categoryId: Long,
    @ColumnInfo(name = "sub_category_name") val subCategoryName: String,
    @ColumnInfo(name = "parent_id") val parentId: Long,
)
