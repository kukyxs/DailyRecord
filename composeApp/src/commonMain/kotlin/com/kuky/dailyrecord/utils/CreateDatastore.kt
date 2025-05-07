package com.kuky.dailyrecord.utils

import androidx.datastore.core.DataMigration
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import okio.Path.Companion.toPath

const val PB_SUFFIX = ".preferences_pb"

expect fun dataStorePreferences(
    path: String,
    corruptionHandler: ReplaceFileCorruptionHandler<Preferences>? = null,
    coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
    migrations: List<DataMigration<Preferences>> = emptyList(),
): DataStore<Preferences>

internal fun createDatastoreWithDefaults(
    corruptionHandler: ReplaceFileCorruptionHandler<Preferences>? = null,
    migrations: List<DataMigration<Preferences>> = emptyList(),
    coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
    pathGetter: () -> String,
) = PreferenceDataStoreFactory.createWithPath(
    corruptionHandler = corruptionHandler,
    migrations = migrations,
    scope = coroutineScope,
    produceFile = {
        pathGetter().let {
            (if (it.endsWith(PB_SUFFIX)) it else "$it$PB_SUFFIX")
        }.toPath()
    }
)