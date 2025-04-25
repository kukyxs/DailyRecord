package com.kuky.dailyrecord.db

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import java.io.File

actual fun getDatabase(): AppDatabase {
    val dbFile = File(System.getProperty("java.io.tmpdir"), DATABASE_NAME)
    println("----> dbFile: ${dbFile.absolutePath}")
    return Room.databaseBuilder<AppDatabase>(name = dbFile.absolutePath)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}