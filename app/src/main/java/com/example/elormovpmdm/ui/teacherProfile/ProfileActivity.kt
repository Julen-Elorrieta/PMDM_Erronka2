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
import com.example.elormovpmdm.BaseActivity
import com.example.elormovpmdm.ui.main.MainActivity
import com.example.elormovpmdm.R
import com.example.elormovpmdm.SettingsDataStore
import com.example.elormovpmdm.databinding.ActivityProfileBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProfileActivity : BaseActivity() {
    
    private lateinit var binding: ActivityProfileBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.profile_activity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
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
            }
        }
        
        binding.btnThemeChange.setOnClickListener {
            lifecycleScope.launch {
                val isDark = settingsDataStore.darkModeFlow.first()
                
                settingsDataStore.saveDarkMode(!isDark)
            }
        }
    }
}