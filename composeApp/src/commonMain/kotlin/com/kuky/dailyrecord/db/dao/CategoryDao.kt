package com.kuky.dailyrecord.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kuky.dailyrecord.model.DBMainCategoryEntity
import com.kuky.dailyrecord.model.DBSubCategoryEntity

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMainCategory(category: DBMainCategoryEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubCategory(category: DBSubCategoryEntity): Long

    @Query("select * from main_category")
    suspend fun allMainCategory(): List<DBMainCategoryEntity>

    @Query("select * from sub_category")
    suspend fun allSubCategory(): List<DBSubCategoryEntity>
}