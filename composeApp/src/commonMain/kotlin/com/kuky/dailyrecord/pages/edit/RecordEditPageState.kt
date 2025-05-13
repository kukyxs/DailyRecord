package com.kuky.dailyrecord.pages.edit

import com.kuky.dailyrecord.db.views.DBRecordDetail
import com.kuky.dailyrecord.model.DBMainCategoryEntity
import com.kuky.dailyrecord.model.DBSubCategoryEntity

data class RecordEditPageState(
    val mainCategoryList: List<DBMainCategoryEntity> = emptyList(),
    val subCategoryList: List<DBSubCategoryEntity> = emptyList(),
    val record: DBRecordDetail? = null,
    val selMainCategoryIndex: Int = 0,
    val selSubCategoryIndex: Int = 0
)
