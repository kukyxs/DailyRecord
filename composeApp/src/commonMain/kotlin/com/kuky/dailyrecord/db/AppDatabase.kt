package com.kuky.dailyrecord.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.kuky.dailyrecord.db.dao.CategoryDao
import com.kuky.dailyrecord.db.dao.RecordDao
import com.kuky.dailyrecord.db.views.DBRecordDetail
import com.kuky.dailyrecord.model.DBMainCategoryEntity
import com.kuky.dailyrecord.model.DBRecordEntity
import com.kuky.dailyrecord.model.DBSubCategoryEntity


@Database(
    entities = [DBRecordEntity::class, DBMainCategoryEntity::class, DBSubCategoryEntity::class],
    views = [DBRecordDetail::class],
    version = 1, exportSchema = true
)
@TypeConverters(StringListConverter::class)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recordDao(): RecordDao
    abstract fun categoryDao(): CategoryDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT", "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

expect fun getDatabase(): AppDatabase

internal const val DATABASE_NAME = "record.db"