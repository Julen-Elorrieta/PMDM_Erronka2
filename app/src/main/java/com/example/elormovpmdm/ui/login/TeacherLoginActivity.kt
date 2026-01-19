package com.example.elormovpmdm.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.elormovpmdm.BaseActivity
import com.example.elormovpmdm.ui.main.MainActivity
import com.example.elormovpmdm.R
import com.example.elormovpmdm.databinding.ActivityTeacherLoginBinding
import kotlinx.coroutines.launch

class TeacherLoginActivity : BaseActivity() {

    private lateinit var binding: ActivityTeacherLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityTeacherLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.loginTeacher)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initComponents()
        initUI()
    }

    private fun initComponents() {
        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("USER_ROLE", "teacher")
            startActivity(intent)
        }
        binding.tvStudentOption.setOnClickListener {
            val intent = Intent(this, StudentLoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initUI() {
        lifecycleScope.launch {
            settingsDataStore.darkModeFlow.collect { isDark ->
                val mode = if (isDark) {
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_NO
                }
                AppCompatDelegate.setDefaultNightMode(mode)
            }
        }
    }
}