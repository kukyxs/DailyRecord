package com.kuky.dailyrecord

import android.app.Application
import android.content.Context

class RecordApp : Application() {
    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }

}