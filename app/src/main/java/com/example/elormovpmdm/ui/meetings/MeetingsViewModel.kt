package com.example.elormovpmdm.ui.meetings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elormovpmdm.data.meetings.MeetingsApiService
import com.example.elormovpmdm.data.students.UsersApiService
import com.example.elormovpmdm.domain.SessionManager
import com.example.elormovpmdm.domain.model.Center
import com.example.elormovpmdm.domain.model.CreateMeetingRequest
import com.example.elormovpmdm.domain.model.Meeting
import com.example.elormovpmdm.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MeetingsViewModel @Inject constructor(
    private val meetingsApiService: MeetingsApiService,
    private val usersApiService: UsersApiService,
    private val sessionManager: SessionManager
): ViewModel(){
    private val _meetings = MutableStateFlow<List<Meeting>> (emptyList())
    val meetings: StateFlow<List<Meeting>> = _meetings

    private val _users = MutableStateFlow<List<User>> (emptyList())
    val users: StateFlow<List<User>> = _users

    private val _centers = MutableStateFlow<List<Center>> (emptyList())
    val centers: StateFlow<List<Center>> = _centers
    
    val user = sessionManager.currentUser.value

    init {
        getAllMeetings()
        getAllUsers()
        getAllCenters()
    }

    fun getAllMeetings() {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    meetingsApiService.getReuniones(sessionManager.currentUser.value!!.id)
                }
                
                if(response.isSuccessful) {
                    _meetings.value = response.body() ?: emptyList()
                } else {
                    Log.i("GVA", "Error API: ${response.code()} - ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.i("GVA", "FALLO TOTAL: ${e.message}")
            }
        }
    }

    fun getAllUsers() {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    if (sessionManager.currentUser.value!!.tipoId == 4) {
                        usersApiService.getTeachersFromStudents(sessionManager.currentUser.value!!.id)
                    } else {
                        usersApiService.getStudentsFromTeacher(sessionManager.currentUser.value!!.id)
                    }
                }

                if (response.isSuccessful) {
                    _users.value = response.body() ?: emptyList()
                } else {
                    Log.i("GVA", "Error API: ${response.code()} - ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.i("GVA", "FALLO TOTAL: ${e.message}")
            }
        }
    }

    fun getAllCenters() {
        viewModelScope.launch {
            try {
                Log.i("GVA", "getCenterList llamado")
                
                val response = withContext(Dispatchers.IO) {
                    meetingsApiService.getCenterList()
                }

                if (response.isSuccessful) {
                    _centers.value = response.body()?.CENTROS ?: emptyList()
                    _centers.value.forEach { 
                        Log.i("GVA", it.NOM)
                    }
                } else {
                    Log.e("GVA", "Error API: ${response.code()} - ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("GVA", "FALLO TOTAL: ${e.message}")
            }
        }
    }
    
    fun createMeeting(createMeetingRequest: CreateMeetingRequest) {
        meetingsApiService.addMeeting(createMeetingRequest)
    }
}