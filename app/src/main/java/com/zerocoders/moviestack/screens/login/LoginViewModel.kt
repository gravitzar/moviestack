package com.zerocoders.moviestack.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zerocoders.moviestack.SessionPreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginScreenUiState(
    val userName: String = "",
    val password: String = "",
    val userNameHasError: Boolean = false,
    val userNameErrorMessage: String = "",
    val passwordHasError: Boolean = false,
    val passwordErrorMessage: String = "",
    val isLoading: Boolean = false
)

sealed class LoginScreenUiEvent {
    data class OnUserNameUpdated(val name: String) : LoginScreenUiEvent()
    data class OnPasswordUpdated(val password: String) : LoginScreenUiEvent()
    object OnLoginClicked : LoginScreenUiEvent()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val sessionPreferenceManager: SessionPreferenceManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginScreenUiState())
    val uiState: StateFlow<LoginScreenUiState> = _uiState.asStateFlow()
    val isLoggedIn = sessionPreferenceManager.isLoggedInFlow

    private fun setLoggedIn() = viewModelScope.launch {
        sessionPreferenceManager.setIsLoggedIn(true)
    }

    private fun saveUserName(userName: String) = viewModelScope.launch {
        sessionPreferenceManager.saveUserName(userName)
    }

    private fun checkInputsAndLogin() = viewModelScope.launch {
        var isUserNameOk = false
        var isPasswordOk = false
        if (_uiState.value.userName.isBlank()) {
            _uiState.update { it.copy(userNameHasError = true, userNameErrorMessage = "user name cannot be empty") }
        } else {
            _uiState.update { it.copy(userNameHasError = false, userNameErrorMessage = "") }
            isUserNameOk = true
        }
        if (_uiState.value.password.isBlank()) {
            _uiState.update { it.copy(passwordHasError = true, passwordErrorMessage = "password cannot be empty") }
        } else {
            _uiState.update { it.copy(passwordHasError = false, passwordErrorMessage = "") }
            isPasswordOk = true
        }
        delay(200)
        if (isUserNameOk && isPasswordOk) {
            saveUserName(_uiState.value.userName)
            setLoggedIn()
        } else {
            _uiState.update { state ->
                state.copy(isLoading = false)
            }
        }
    }

    fun onEventUpdated(event: LoginScreenUiEvent) = viewModelScope.launch {
        when (event) {
            is LoginScreenUiEvent.OnPasswordUpdated -> {
                _uiState.update { state ->
                    state.copy(password = event.password)
                }
            }

            is LoginScreenUiEvent.OnUserNameUpdated -> {
                _uiState.update { state ->
                    state.copy(userName = event.name)
                }
            }

            LoginScreenUiEvent.OnLoginClicked -> {
                _uiState.update { state ->
                    state.copy(isLoading = true)
                }
                checkInputsAndLogin()
            }
        }
    }
}