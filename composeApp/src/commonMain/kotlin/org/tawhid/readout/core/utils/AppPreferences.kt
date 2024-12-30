package org.tawhid.readout.core.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class AppPreferences(
    private val dataStore: DataStore<Preferences>
) {
    private val themeKey = stringPreferencesKey("theme")
    suspend fun getTheme() = dataStore.data.map { preferences ->
        preferences[themeKey] ?: Theme.SYSTEM_DEFAULT.name
    }.first()

    fun getThemeSync(): String {
        return runBlocking {
            getTheme()
        }
    }
    suspend fun changeThemeMode(value: String) = dataStore.edit { preferences ->
        preferences[themeKey] = value
    }
}