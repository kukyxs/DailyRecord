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
    suspend fun allMainCategories(): List<DBMainCategoryEntity>

    @Query("select * from sub_category")
    suspend fun allSubCategories(): List<DBSubCategoryEntity>

    @Query("select * from sub_category where parent_id=:mainCategoryId")
    suspend fun allSubCategoriesInMain(mainCategoryId: Long): List<DBSubCategoryEntity>
}