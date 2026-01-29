package com.example.elormovpmdm.ui.schedule.userSchedule

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elormovpmdm.data.schedule.ScheduleApiService
import com.example.elormovpmdm.domain.SessionManager
import com.example.elormovpmdm.domain.model.Schedule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class UserScheduleViewModel @Inject constructor(private val scheduleApiService: ScheduleApiService, savedStateHandle: SavedStateHandle): ViewModel() {
    private val _schedules = MutableStateFlow<List<Schedule>> (emptyList())
    val schedules: StateFlow<List<Schedule>> = _schedules
    private val userId: Int = savedStateHandle["user_id"] ?: 0

    init {
        getAllModules()
    }

    fun getAllModules() {
        viewModelScope.launch {
            try{
                val response = withContext(Dispatchers.IO) {
                    scheduleApiService.getHorario(userId)
                }

                if(response.isSuccessful) {
                    _schedules.value = response.body() ?: emptyList()
                } else {
                    Log.i("GVA", "Error API: ${response.code()} - ${response.errorBody()?.toString()}")
                }
            } catch (e: Exception) {
                Log.i("GVA", "Fallo total: ${e.message}")
            }
        }
    }
}