package com.kuky.dailyrecord.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    tableName = "sub_category",
    foreignKeys = [ForeignKey(
        entity = DBMainCategoryEntity::class, parentColumns = ["id"], childColumns = ["parent_id"],
        onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE
    )]
)
data class DBSubCategoryEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "category_name") val subCategoryName: String,
    @ColumnInfo(name = "parent_id") val parentId: Long,
)
