package com.example.elormovpmdm.domain

import com.example.elormovpmdm.SettingsDataStore
import com.example.elormovpmdm.data.login.SessionState
import com.example.elormovpmdm.data.modules.ApplicationScope
import com.example.elormovpmdm.domain.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    private val dataStore: SettingsDataStore,
    @ApplicationScope private val externalScope: CoroutineScope
) {
    private var _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    private val _sessionState = MutableStateFlow<SessionState>(SessionState.Loading)
    val sessionState: StateFlow<SessionState> = _sessionState.asStateFlow()
    
    init {
        externalScope.launch { 
            dataStore.userFlow.collect { user ->
                _sessionState.value = if (user != null) {
                    SessionState.Authenticated(user)
                } else {
                    SessionState.NotAuthenticated
                }
                _currentUser.value = user
            }
        }
    }
    
    suspend fun saveUser(user: User) {
        dataStore.saveLoggedUser(user)
    }

    suspend fun clearSession() {
        dataStore.saveLoggedUser(null)
    }
}