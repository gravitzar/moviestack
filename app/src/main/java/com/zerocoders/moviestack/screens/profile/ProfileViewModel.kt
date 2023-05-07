package com.zerocoders.moviestack.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zerocoders.moviestack.SessionPreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val sessionPreferenceManager: SessionPreferenceManager
) : ViewModel() {
    val isLoggedIn = sessionPreferenceManager.isLoggedInFlow
    val userName = sessionPreferenceManager.userNameFlow

    fun logout() = viewModelScope.launch {
        sessionPreferenceManager.setIsLoggedIn(false)
    }
}