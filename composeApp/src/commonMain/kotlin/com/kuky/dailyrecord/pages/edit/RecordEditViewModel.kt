package com.kuky.dailyrecord.pages.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuky.dailyrecord.extension.safeLaunch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RecordEditViewModel(private val repository: RecordEditRepository) : ViewModel() {

    private val _state = MutableStateFlow(RecordEditPageState())
    val state: StateFlow<RecordEditPageState> get() = _state

    suspend fun findRecordById(id: Long) {
        viewModelScope.safeLaunch(Dispatchers.Default) {
            val record = repository.findRecordById(id)
            _state.value = _state.value.copy(record = record)
        }
    }

    suspend fun allMainCategories() {
        viewModelScope.safeLaunch(Dispatchers.Default) {
            val mainCategories = repository.allMainCategories()
            _state.value = _state.value.copy(mainCategoryList = mainCategories, selMainCategoryIndex = 0)
            if (mainCategories.isNotEmpty()) allSubCategoriesInMain(mainCategories.first().id!!, 0)
        }
    }

    suspend fun onMainCategoryChange(index: Int) {
        if (_state.value.selMainCategoryIndex == index) return
        viewModelScope.safeLaunch(Dispatchers.Default) {
            val mainCategories = _state.value.mainCategoryList
            val mainCategory = mainCategories[index]
            _state.value = _state.value.copy(mainCategoryList = mainCategories, selMainCategoryIndex = index)
            allSubCategoriesInMain(mainCategory.id!!, 0)
        }
    }

    suspend fun allSubCategoriesInMain(mainCategoryId: Long, index: Int) {
        viewModelScope.safeLaunch(Dispatchers.Default) {
            val subCategories = repository.allSubCategoriesInMain(mainCategoryId)
            _state.value = _state.value.copy(subCategoryList = subCategories, selSubCategoryIndex = index)
        }
    }

    fun onSubCategoryChange(index: Int) {
        if (_state.value.selSubCategoryIndex == index) return
        _state.value = _state.value.copy(selSubCategoryIndex = index)
    }
}