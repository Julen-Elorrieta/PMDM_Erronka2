package com.example.elormovpmdm

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.elormovpmdm.domain.model.User
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings_prefs")

class SettingsDataStore(private val context: Context) {
    
    private val gson = Gson()
    
    companion object {
        val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
        val LANGUAGE_KEY = stringPreferencesKey("language_code")
        val USER_DATA_KEY = stringPreferencesKey("user_data")
    }
    
    // Guardar valores
    suspend fun saveDarkMode(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[DARK_MODE_KEY] = enabled
        }
    }
    
    suspend fun saveLanguage(langCode: String) {
        context.dataStore.edit { prefs ->
            prefs[LANGUAGE_KEY] = langCode
        }
    }
    
    suspend fun saveLoggedUser(user: User?) {
        context.dataStore.edit { prefs ->
            prefs[USER_DATA_KEY] = gson.toJson(user)
        }
    }
    
    // Leer valores
    val darkModeFlow: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[DARK_MODE_KEY] ?: false
    }
    
    val languageFlow: Flow<String> = context.dataStore.data.map { prefs ->
        // El idioma predeterminado es el español
        prefs[LANGUAGE_KEY] ?: "es"
    }
    
    val userFlow: Flow<User?> = context.dataStore.data.map { prefs ->
        val json = prefs[USER_DATA_KEY]
        if (!json.isNullOrEmpty()) {
            gson.fromJson(json, User::class.java)
        } else {
            null
        }
    }
}