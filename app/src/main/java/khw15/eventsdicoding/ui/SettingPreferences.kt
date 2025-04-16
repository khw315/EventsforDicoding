package khw15.eventsdicoding.ui

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingPreferences private constructor(
    private val dataStore: DataStore<Preferences>
) {

    private object Keys {
        val THEME = booleanPreferencesKey("theme_setting")
        val NOTIFICATIONS = booleanPreferencesKey("notification_setting")
    }

    fun getThemeSetting(): Flow<Boolean> =
        dataStore.data.map { preferences ->
            preferences[Keys.THEME] ?: false
        }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[Keys.THEME] = isDarkModeActive
        }
    }

    fun getNotificationSetting(): Flow<Boolean> =
        dataStore.data.map { preferences ->
            preferences[Keys.NOTIFICATIONS] ?: false
        }

    suspend fun saveNotificationSetting(isNotificationActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[Keys.NOTIFICATIONS] = isNotificationActive
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: SettingPreferences(dataStore).also { INSTANCE = it }
            }
        }
    }
}