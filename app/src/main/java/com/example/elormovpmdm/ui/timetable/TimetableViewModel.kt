package com.example.elormovpmdm.ui.timetable

import androidx.lifecycle.ViewModel
import com.example.elormovpmdm.data.UserProvider
import com.example.elormovpmdm.domain.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class TimetableViewModel @Inject constructor(private val userProvider: UserProvider): ViewModel() {
    private val _user = MutableStateFlow<List<User>> (emptyList())
    val user: StateFlow<List<User>> = _user
    
    init {
        _user.value = userProvider.getUsers()
    }
}