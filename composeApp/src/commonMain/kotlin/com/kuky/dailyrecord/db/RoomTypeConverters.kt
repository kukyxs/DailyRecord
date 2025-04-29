package com.kuky.dailyrecord.db

import androidx.room.TypeConverter

class StringListConverter {
    @TypeConverter
    fun stringListToString(list: List<String>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun stringToStringList(string: String): List<String> {
        return string.split(",")
    }
}