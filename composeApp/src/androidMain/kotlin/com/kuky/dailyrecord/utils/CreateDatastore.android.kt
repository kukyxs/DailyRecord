package com.kuky.dailyrecord.utils


import androidx.datastore.core.DataMigration
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.Preferences
import com.kuky.dailyrecord.RecordApp
import kotlinx.coroutines.CoroutineScope
import java.io.File

actual fun dataStorePreferences(
    path: String,
    corruptionHandler: ReplaceFileCorruptionHandler<Preferences>?,
    coroutineScope: CoroutineScope,
    migrations: List<DataMigration<Preferences>>
): DataStore<Preferences> {
    return createDatastoreWithDefaults(
        coroutineScope = coroutineScope,
        corruptionHandler = corruptionHandler,
        migrations = migrations,
        pathGetter = {
            File(RecordApp.context.applicationContext.filesDir, "datastore/$path").path
        }
    )
}