package com.kuky.dailyrecord.utils

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow


class EventBus {
    private val _regularEvents = MutableSharedFlow<Event>(extraBufferCapacity = 64)
    val regularEvents = _regularEvents.asSharedFlow()

    private val _stickyEvents = MutableSharedFlow<Event>(replay = 1, extraBufferCapacity = 64)
    val stickyEvents = _stickyEvents.asSharedFlow()

    suspend fun emitRegular(event: Event) = _regularEvents.emit(event)

    suspend fun emitSticky(event: Event) = _stickyEvents.emit(event)
}

sealed class Event

data class TestEvent(val msg: String) : Event()