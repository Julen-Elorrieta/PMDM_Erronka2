package com.example.elormovpmdm.ui.meetings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elormovpmdm.data.meetings.MeetingsApiService
import com.example.elormovpmdm.domain.SessionManager
import com.example.elormovpmdm.domain.model.Meeting
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
    private val sessionManager: SessionManager
): ViewModel(){
    private val _meetings = MutableStateFlow<List<Meeting>> (emptyList())
    val meetings: StateFlow<List<Meeting>> = _meetings

    init {
        getAllMeetings()
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
}