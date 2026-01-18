package com.example.elormovpmdm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

open class BaseActivity : AppCompatActivity() {
    
    protected lateinit var settingsDataStore: SettingsDataStore
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingsDataStore = SettingsDataStore(this)
        
        lifecycleScope.launch { 
            settingsDataStore.darkModeFlow.collect { isDark ->
                val mode = if (isDark) {
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_NO
                }
                
                if(AppCompatDelegate.getDefaultNightMode() != mode) {
                    AppCompatDelegate.setDefaultNightMode(mode)
                }
            }
        }
        
        lifecycleScope.launch { 
            settingsDataStore.languageFlow.collect { langCode ->
                val appLocale = LocaleListCompat.forLanguageTags(langCode)
                
                if(AppCompatDelegate.getApplicationLocales() != appLocale) {
                    AppCompatDelegate.setApplicationLocales(appLocale)
                }
            }
        }
    }
}