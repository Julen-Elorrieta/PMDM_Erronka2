package com.example.elormovpmdm.ui.teacherProfile

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.os.LocaleListCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.elormovpmdm.MainActivity
import com.example.elormovpmdm.R
import com.example.elormovpmdm.SettingsDataStore
import com.example.elormovpmdm.databinding.ActivityProfileBinding
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityProfileBinding
    private lateinit var settingsDataStore: SettingsDataStore
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        settingsDataStore = SettingsDataStore(this)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.profile_activity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupLanguageObserver()
        initComponent()
    }
    
    private fun initComponent() {
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        
        val languageOptions = arrayOf(
            getString(R.string.spanish),
            getString(R.string.basque),
            getString(R.string.english)
        )
        
        val codes = arrayOf("es", "eu", "en")
        
        val languageAdapter =
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, languageOptions)
        
        binding.autoCompleteTextView.setAdapter(languageAdapter)
        
        binding.autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            val selectedCode = codes[position]
            lifecycleScope.launch {
                settingsDataStore.saveLanguage(selectedCode)
                applyLanguage(selectedCode)
            }
        }
        
        binding.btnThemeChange.setOnClickListener {
            lifecycleScope.launch {
                settingsDataStore.darkModeFlow.collect { isDark ->
                    AppCompatDelegate.setDefaultNightMode(
                        if (isDark) AppCompatDelegate.MODE_NIGHT_YES
                        else AppCompatDelegate.MODE_NIGHT_NO
                    )
                }
            }
        }
    }
    
    private fun setupLanguageObserver() {
        lifecycleScope.launch {
            settingsDataStore.languageFlow.collect { langCode ->
                val codes = arrayOf("es", "eu", "en")
                val languageOptions = arrayOf(
                    getString(R.string.spanish),
                    getString(R.string.basque),
                    getString(R.string.english)
                )
                val index = codes.indexOf(langCode)
                if(index != -1) {
                    binding.autoCompleteTextView.setText(languageOptions[index], false)
                }
            }
        }
    }
    
    private fun applyLanguage(langCode: String) {
        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(langCode)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }
}