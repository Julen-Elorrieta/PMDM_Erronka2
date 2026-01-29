package com.example.elormovpmdm.ui.schedule

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elormovpmdm.data.students.UsersApiService
import com.example.elormovpmdm.domain.SessionManager
import com.example.elormovpmdm.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SchedulesViewModel @Inject constructor(
    private val usersApiService: UsersApiService,
    private val sessionManager: SessionManager
): ViewModel() {
    private val _users = MutableStateFlow<List<User>> (emptyList())
    val users: StateFlow<List<User>> = _users
    
    init {
        getAllUsers()
    }
    
    fun getAllUsers() {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    usersApiService.getAllUsers(sessionManager.currentUser.value!!.id)
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