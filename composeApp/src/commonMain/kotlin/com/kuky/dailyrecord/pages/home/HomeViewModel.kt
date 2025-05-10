package com.kuky.dailyrecord.pages.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuky.dailyrecord.db.views.DBRecordDetail
import com.kuky.dailyrecord.db.views.DBRecordMonth
import com.kuky.dailyrecord.extension.safeLaunch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {
    private val _state = MutableStateFlow(HomePageState())
    val state: StateFlow<HomePageState> get() = _state

    fun fetchAllMonthInRecord() {
        viewModelScope.safeLaunch(Dispatchers.Default) {
            val monthList = repository.getAllMonth()
            _state.value = _state.value.copy(monthList = monthList)
            println(monthList)
            if (monthList.isNotEmpty()) getRecordInMonth(monthList.first(), 0)
        }
    }

    fun getRecordInMonth(month: DBRecordMonth, index: Int) {
        viewModelScope.safeLaunch(Dispatchers.Default) {
            val records = repository.getAllRecordInMonth(month)
            val recordGroup = records.groupBy { it.recordDate }
            val keys = recordGroup.keys.sortedByDescending { it }
            val recordList = mutableListOf<List<DBRecordDetail>>()
                .apply { keys.forEach { add(recordGroup[it]!!) } }
            _state.value = _state.value.copy(recordGroup = recordList, selIndex = index)
            println(records)
        }
    }
}