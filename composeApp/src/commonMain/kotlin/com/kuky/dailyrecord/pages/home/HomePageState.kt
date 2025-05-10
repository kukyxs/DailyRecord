package com.kuky.dailyrecord.pages.home

import com.kuky.dailyrecord.db.views.DBRecordDetail
import com.kuky.dailyrecord.db.views.DBRecordMonth

data class HomePageState(
    val monthList: List<DBRecordMonth> = listOf(),
    val selIndex: Int = 0,
    val recordList: List<DBRecordDetail> = listOf(),
    val recordGroup: List<List<DBRecordDetail>> = listOf()
)
