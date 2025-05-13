package com.kuky.dailyrecord.pages.edit

import com.kuky.dailyrecord.db.getDatabase
import com.kuky.dailyrecord.db.views.DBRecordDetail

class RecordEditRepository {
    private val database by lazy { getDatabase() }

    suspend fun findRecordById(id: Long): DBRecordDetail? {
        return database.recordDao().findRecordById(id)
    }

    suspend fun allMainCategories() = database.categoryDao().allMainCategories()

    suspend fun allSubCategoriesInMain(mainCategoryId: Long) = database.categoryDao().allSubCategoriesInMain(mainCategoryId)
}