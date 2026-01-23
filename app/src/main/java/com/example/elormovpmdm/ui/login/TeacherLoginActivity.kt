package com.example.elormovpmdm.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.elormovpmdm.BaseActivity
import com.example.elormovpmdm.ui.main.MainActivity
import com.example.elormovpmdm.R
import com.example.elormovpmdm.data.login.LoginState
import com.example.elormovpmdm.databinding.ActivityTeacherLoginBinding
import com.example.elormovpmdm.domain.model.UserResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TeacherLoginActivity : BaseActivity() {

    private lateinit var binding: ActivityTeacherLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

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
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                Log.i("GVA", email)
                Log.i("GVA", password)
                loginViewModel.login(email, password)
                Log.i("GVA", "Login pulsado")
            } else {
                Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleLoginSuccess (user: UserResponse) {
        val tipo = user.tipoId

        val intent = when(tipo) {
            3 -> Intent(this, MainActivity::class.java).apply {
                putExtra("USER_ROLE", "teacher")
            }
            4 -> Intent(this, MainActivity::class.java).apply {
                putExtra("USER_ROLE", "student")
            }
            else -> Intent(this, MainActivity::class.java)
        }

        startActivity(intent)
    }

    private fun initUI() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.state.collect { state ->
                    when (state) {
                        is LoginState.Loading -> {
                            Log.i("GVA", "Loading")
                            binding.btnLogin.isEnabled = false
                        }
                        is LoginState.Success -> {
                            Log.i("GVA", "Succes")
                            handleLoginSuccess(state.user)
                        }
                        is LoginState.Error -> {
                            binding.btnLogin.isEnabled = true
                        }
                        is LoginState.Idle -> {

                        }
                    }
                }
            }
        }

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