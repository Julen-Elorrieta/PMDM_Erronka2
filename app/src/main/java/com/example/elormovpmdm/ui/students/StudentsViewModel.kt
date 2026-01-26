package com.example.elormovpmdm.ui.students

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.elormovpmdm.data.UserProvider
import com.example.elormovpmdm.data.students.StudentsApiService
import com.example.elormovpmdm.domain.User
import com.example.elormovpmdm.domain.model.LoginRequest
import com.example.elormovpmdm.domain.model.UserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StudentsViewModel @Inject constructor(private val studentsApiService: StudentsApiService): ViewModel() {
    private val _user = MutableStateFlow<List<UserResponse>> (emptyList())
    val user: StateFlow<List<UserResponse>> = _user

    fun getAllStudents() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    studentsApiService.getStudents()
                } catch (e: Exception) {
                    Log.e("GVA", "EXCEPCIÓN DETECTADA: ${e.javaClass.simpleName}")
                    Log.e("GVA", "MENSAJE: ${e.message}")
                    e.printStackTrace()
                    null
                }
            }

            if (result != null && result.isSuccessful) {
                result.body()?.let { list ->
                    _user.value = list
                }
            } else {
                Log.e("GVA", "Error en la respuesta: ${result?.code()}")
            }
        }
    }

}