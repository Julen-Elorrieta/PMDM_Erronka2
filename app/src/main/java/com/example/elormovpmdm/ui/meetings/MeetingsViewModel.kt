package com.example.elormovpmdm.ui.meetings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elormovpmdm.data.meetings.MeetingsApiService
import com.example.elormovpmdm.data.students.UsersApiService
import com.example.elormovpmdm.domain.SessionManager
import com.example.elormovpmdm.domain.model.Center
import com.example.elormovpmdm.domain.model.Meeting
import com.example.elormovpmdm.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
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
                } else {
                    Log.e("GVA", "Error API: ${response.code()} - ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("GVA", "FALLO TOTAL: ${e.message}")
            }
        }
    }
    
    suspend fun createMeeting(createMeetingRequest: Meeting) {
        try {
            var reunionCreada: Meeting? = null
            viewModelScope.launch {
                try {
                    reunionCreada = meetingsApiService.addMeeting(createMeetingRequest)
                } catch (e: Exception) {

                }

            }
            Log.i("GVA", "Reunión creada exitosamente: ${reunionCreada?.idReunion}")

        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Log.e("GVA", "Error HTTP: ${e.code()} - $errorBody")
        } catch (e: IOException) {
            Log.e("GVA", "Error de red: ${e.message}")
        } catch (e: Exception) {
            Log.e("GVA", "Error inesperado", e)
        }
    }
}