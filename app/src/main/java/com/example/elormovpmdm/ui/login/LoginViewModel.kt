package com.example.elormovpmdm.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elormovpmdm.data.login.LoginApiService
import com.example.elormovpmdm.data.login.LoginState
import com.example.elormovpmdm.domain.SessionManager
import com.example.elormovpmdm.domain.model.LoginRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginApiService: LoginApiService,
    private val sessionManager: SessionManager
): ViewModel() {
    private val _state = MutableStateFlow<LoginState>(LoginState.Idle)
    val state: StateFlow<LoginState> = _state

    fun login(email: String, password: String) {
        viewModelScope.launch {
            Log.i("GVA", "Corrutina lanzada")

            _state.value = LoginState.Loading

            val result = withContext(Dispatchers.IO) {
                try {
                    loginApiService.login(LoginRequest(email, password))
                } catch (e: Exception) {
                    Log.e("GVA", "EXCEPCIÓN DETECTADA: ${e.javaClass.simpleName}")
                    Log.e("GVA", "MENSAJE: ${e.message}")
                    e.printStackTrace()
                    null
                }
            }

            if (result != null && result.isSuccessful) {
                val user = result.body()
                if (user != null) {
                    _state.value = LoginState.Success
                    sessionManager.saveUser(user)
                } else {
                    _state.value = LoginState.Error("Respuesta vacía del servidor")
                }
            } else {
                // Esto nos dirá si es 404 (Ruta mal), 401 (Login mal) o 500 (Crash en Java)
                val errorCode = result?.code()
                val errorContent = result?.errorBody()?.string()

                Log.e("GVA", "DETALLE DEL ERROR: Código $errorCode")
                Log.e("GVA", "CONTENIDO: $errorContent")

                val errorMsg = when(errorCode) {
                    401 -> "Contraseña incorrecta"
                    404 -> "Ruta no encontrada (Revisa LoginApiService)"
                    500 -> "Error interno del servidor Java"
                    else -> "Error desconocido: $errorCode"
                }
                _state.value = LoginState.Error(errorMsg)
            }
        }
    }
}