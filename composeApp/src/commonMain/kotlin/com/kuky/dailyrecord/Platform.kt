package com.kuky.dailyrecord

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun getPlatformName(): String