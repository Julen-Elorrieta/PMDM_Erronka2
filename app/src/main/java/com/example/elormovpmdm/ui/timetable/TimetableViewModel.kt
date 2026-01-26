package com.example.elormovpmdm.ui.timetable

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elormovpmdm.data.students.UsersApiService
import com.example.elormovpmdm.domain.model.UserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TimetableViewModel @Inject constructor(private val usersApiService: UsersApiService): ViewModel() {
    private val _users = MutableStateFlow<List<UserResponse>> (emptyList())
    val users: StateFlow<List<UserResponse>> = _users
    
    init {
        getAllUsers()
    }
    
    fun getAllUsers() {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    usersApiService.getAllUsers()
                }
                
                if (response.isSuccessful) {
                    _users.value = response.body() ?: emptyList()
                } else {
                    Log.i("GVA", "Error API: ${response.code()} - ${response.errorBody()?.toString()}")
                }
            } catch (e: Exception) {
                Log.i("GVA", "FALLO TOTAL: ${e.message}")
            }
        }
    }
}