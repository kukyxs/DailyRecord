@file:Suppress("unused")

package com.kuky.dailyrecord.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 *   datastore 管理
 */
class LocalCacheManager {
    companion object {
        const val DEFAULT_NAME_SPACE = "TxComposeApp"
    }

    private val dataStorePool: MutableMap<String, DataStore<Preferences>> by lazy {
        mutableMapOf()
    }

    private val mutex by lazy { Mutex() }

    suspend fun getDataStore(namespace: String = DEFAULT_NAME_SPACE): DataStore<Preferences> {
        return mutex.withLock {
            if (dataStorePool.containsKey(namespace)) {
                dataStorePool[namespace]!!
            } else {
                val store = dataStorePreferences(namespace)
                dataStorePool[namespace] = store
                store
            }
        }
    }

    suspend inline fun <reified T> get(key: String, namespace: String = DEFAULT_NAME_SPACE): T? {
        return if (isLocalBasicClazzType<T>()) {
            getDataStore(namespace).data.map { it[getKey<T>(key)] }.first() as T?
        } else {
            val json: String = getDataStore(namespace).data.map { it[getKey<String>(key)] }.first() as? String ?: ""
            Json.decodeFromString<T>(json)
        }
    }

    suspend inline fun <reified T> set(key: String, value: T, namespace: String = DEFAULT_NAME_SPACE) {
        when (T::class) {
            Int::class -> getDataStore(namespace).edit { it[intPreferencesKey(key)] = value as Int }
            Long::class -> getDataStore(namespace).edit { it[longPreferencesKey(key)] = value as Long }
            Float::class -> getDataStore(namespace).edit { it[floatPreferencesKey(key)] = value as Float }
            Double::class -> getDataStore(namespace).edit { it[doublePreferencesKey(key)] = value as Double }
            Boolean::class -> getDataStore(namespace).edit { it[booleanPreferencesKey(key)] = value as Boolean }
            String::class -> getDataStore(namespace).edit { it[stringPreferencesKey(key)] = value as String }
            else -> getDataStore(namespace).edit { it[stringPreferencesKey(key)] = Json.encodeToString(value) }
        }
    }

    suspend inline fun <reified T> contains(key: String, namespace: String = DEFAULT_NAME_SPACE): Boolean {
        return getDataStore(namespace).data.map { it[getKey<T>(key)] }.first() != null
    }

    suspend inline fun <reified T> remove(key: String, namespace: String = DEFAULT_NAME_SPACE) {
        getDataStore(namespace).edit { it.remove(getKey<T>(key)) }
    }

    suspend fun clear(namespace: String = DEFAULT_NAME_SPACE) {
        getDataStore(namespace).edit { it.clear() }
    }

    inline fun <reified T> isLocalBasicClazzType(): Boolean {
        return T::class == Int::class || T::class == Long::class || T::class == Float::class
                || T::class == Double::class || T::class == Boolean::class || T::class == String::class
    }

    inline fun <reified T> getKey(key: String): Preferences.Key<*> {
        return when (T::class) {
            Int::class -> intPreferencesKey(key)
            Long::class -> longPreferencesKey(key)
            Float::class -> floatPreferencesKey(key)
            Double::class -> doublePreferencesKey(key)
            Boolean::class -> booleanPreferencesKey(key)
            else -> stringPreferencesKey(key)
        }
    }
}