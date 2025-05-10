package com.kuky.dailyrecord.testcase

import com.kuky.dailyrecord.db.getDatabase
import com.kuky.dailyrecord.model.DBMainCategoryEntity
import com.kuky.dailyrecord.model.DBSubCategoryEntity

class TestCategoryCase {
    private val database by lazy { getDatabase() }

    suspend fun insertCategory() {
        database.categoryDao().run {
            val id = insertMainCategory(DBMainCategoryEntity(null, "支出", false, "#000000", true))
            insertSubCategory(DBSubCategoryEntity(null, "日常支出", id))
        }
    }
}