package com.example.tes1

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class MyPreferencesRepository private constructor(private val dataStore: DataStore<Preferences>) {

    //switches
    val red_switch: Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[RED_SWITCH_KEY] ?: false
    }.distinctUntilChanged()

    val green_switch: Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[GREEN_SWITCH_KEY] ?: false
    }.distinctUntilChanged()

    val blue_switch: Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[BLUE_SWITCH_KEY] ?: false
    }.distinctUntilChanged()

    //seekbar
    val red_progress: Flow<Int> = dataStore.data.map {prefs ->
        prefs[RED_PROGRESS_KEY] ?: 0
    }.distinctUntilChanged()

    val green_progress: Flow<Int> = dataStore.data.map {prefs ->
        prefs[GREEN_PROGRESS_KEY] ?: 0
    }.distinctUntilChanged()

    val blue_progress: Flow<Int> = dataStore.data.map {prefs ->
        prefs[BLUE_PROGRESS_KEY] ?: 0
    }.distinctUntilChanged()

    //text
    val red_text: Flow<String> = dataStore.data.map {prefs ->
        prefs[RED_TEXT_KEY] ?: ""
    }.distinctUntilChanged()

    val green_text: Flow<String> = dataStore.data.map {prefs ->
        prefs[GREEN_TEXT_KEY] ?: ""
    }.distinctUntilChanged()

    val blue_text: Flow<String> = dataStore.data.map {prefs ->
        prefs[BLUE_TEXT_KEY] ?: ""
    }.distinctUntilChanged()

    private suspend fun saveStringValue(key: Preferences.Key<String>, value: String) {
        this.dataStore.edit { prefs ->
            prefs[key] = value
        }
    }

    private suspend fun saveBooleanValue(key: Preferences.Key<Boolean>, value: Boolean) {
        this.dataStore.edit { prefs ->
            prefs[key] = value
        }
    }

    private suspend fun saveIntValue(key: Preferences.Key<Int>, value: Int) {
        this.dataStore.edit { prefs ->
            prefs[key] = value
        }
    }

    suspend fun saveInputString(value: String, index: Int) {
        val key: Preferences.Key<String> = when (index) {
            1 -> RED_TEXT_KEY
            2 -> GREEN_TEXT_KEY
            3 -> BLUE_TEXT_KEY

            else -> {throw NoSuchFieldException("Invalid input index: $index")}
        }
        this.saveStringValue(key, value)
    }

    suspend fun saveInputBool(value: Boolean, index: Int) {
        val key: Preferences.Key<Boolean> = when (index) {
            1 -> RED_SWITCH_KEY
            2 -> GREEN_SWITCH_KEY
            3 -> BLUE_SWITCH_KEY

            else -> {throw NoSuchFieldException("invalid input index: $index")}
        }
        this.saveBooleanValue(key, value)
    }

    suspend fun saveInputInt(value: Int, index: Int) {
        val key: Preferences.Key<Int> = when (index) {
            1 -> RED_PROGRESS_KEY
            2 -> GREEN_PROGRESS_KEY
            3 -> BLUE_PROGRESS_KEY

            else -> {throw NoSuchFieldException("invalid input index: $index")}
        }
        this.saveIntValue(key, value)
    }



    companion object{
        private const val PREFERENCES_DATA_FILE_NAME = "color"

        private val RED_SWITCH_KEY = booleanPreferencesKey("red_switch")
        private val GREEN_SWITCH_KEY = booleanPreferencesKey("green_switch")
        private val BLUE_SWITCH_KEY = booleanPreferencesKey("blue_switch")
        private val RED_PROGRESS_KEY = intPreferencesKey("red_progress")
        private val GREEN_PROGRESS_KEY = intPreferencesKey("green_progress")
        private val BLUE_PROGRESS_KEY = intPreferencesKey("blue_progress")
        private val RED_TEXT_KEY = stringPreferencesKey("red_text")
        private val GREEN_TEXT_KEY = stringPreferencesKey("green_text")
        private val BLUE_TEXT_KEY = stringPreferencesKey("blue_text")

        private var INSTANCE: MyPreferencesRepository? = null

        fun initialize(context: Context) {

            if ( INSTANCE == null ) {
                val dataStore = PreferenceDataStoreFactory.create {
                    context.preferencesDataStoreFile(PREFERENCES_DATA_FILE_NAME)
                }
                INSTANCE = MyPreferencesRepository(dataStore)
            }
        }

        fun get(): MyPreferencesRepository {
            return INSTANCE ?: throw IllegalAccessException("MyPreferencesRepository has not yet been initalize()'ed")
        }
    }
}