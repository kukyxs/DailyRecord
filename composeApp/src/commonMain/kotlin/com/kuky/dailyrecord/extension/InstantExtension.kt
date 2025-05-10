package com.kuky.dailyrecord.extension

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Instant.getTime(): String {
    val date = this.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${date.hour}:${date.minute}"
}

fun Instant.getDate(): String {
    val date = this.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${date.monthNumber}-${date.dayOfMonth}"
}