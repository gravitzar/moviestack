package com.zerocoders.moviestack.screens.signup

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

data class SignUpScreenUiState(
    val userName: String = "",
    val userNameHasError: Boolean = false,
    val userNameErrorMessage: String = "",
    val password: String = "",
    val passwordHasError: Boolean = false,
    val passwordErrorMessage: String = "",
    val confirmPassword: String = "",
    val confirmPasswordHasError: Boolean = false,
    val confirmPasswordErrorMessage: String = "",
    val emailId: String = "",
    val emailHasError: Boolean = false,
    val emailErrorMessage: String = "",
    val phoneNumber: String = "",
    val phoneNumberHasError: Boolean = false,
    val phoneNumberErrorMessage: String = "",
    val isLoading: Boolean = false
)

sealed class SignUpScreenUiEvent {
    data class OnUserNameUpdated(val name: String) : SignUpScreenUiEvent()
    data class OnEmailUpdated(val email: String) : SignUpScreenUiEvent()
    data class OnPhoneUpdated(val phone: String) : SignUpScreenUiEvent()
    data class OnPasswordUpdated(val password: String) : SignUpScreenUiEvent()
    data class OnConfirmPasswordUpdated(val confirmPassword: String) : SignUpScreenUiEvent()
    object OnSignUpClicked : SignUpScreenUiEvent()
}

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val sessionPreferenceManager: SessionPreferenceManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpScreenUiState())
    val uiState: StateFlow<SignUpScreenUiState> = _uiState.asStateFlow()
    val isLoggedIn = sessionPreferenceManager.isLoggedInFlow

    private fun setLoggedIn() = viewModelScope.launch {
        sessionPreferenceManager.setIsLoggedIn(true)
    }

    private fun saveUserName(userName: String) = viewModelScope.launch {
        sessionPreferenceManager.saveUserName(userName)
    }


    private fun checkInputsAndLogin() = viewModelScope.launch {
        val isUserNameOk = if (_uiState.value.userName.isBlank()) {
            _uiState.update { it.copy(userNameHasError = true, userNameErrorMessage = "user name cannot be empty") }
            false
        } else {
            _uiState.update { it.copy(userNameHasError = false, userNameErrorMessage = "") }
            true
        }

        val isEmailOk = if (_uiState.value.emailId.isBlank()) {
            _uiState.update { it.copy(emailHasError = true, emailErrorMessage = "email cannot be empty") }
            false
        } else {
            _uiState.update { it.copy(emailHasError = false, emailErrorMessage = "") }
            true
        }

        val isPhoneOk = if (_uiState.value.phoneNumber.isBlank()) {
            _uiState.update { it.copy(phoneNumberHasError = true, phoneNumberErrorMessage = "email cannot be empty") }
            false
        } else {
            _uiState.update { it.copy(phoneNumberHasError = false, phoneNumberErrorMessage = "") }
            true
        }

        val isPasswordIsOK = if (_uiState.value.password.isBlank()) {
            _uiState.update { it.copy(passwordHasError = true, passwordErrorMessage = "password cannot be empty") }
            false
        } else {
            _uiState.update { it.copy(passwordHasError = false, passwordErrorMessage = "") }
            true
        }

//        val isConfirmPasswordIsOk = if (_uiState.value.confirmPassword.isBlank()) {
//            _uiState.update { it.copy(confirmPasswordHasError = true, confirmPasswordErrorMessage = "password cannot be empty") }
//            false
//        } else {
//            _uiState.update { it.copy(confirmPasswordHasError = false, confirmPasswordErrorMessage = "") }
//            true
//        }

        val isPasswordsMatched = if (_uiState.value.password != _uiState.value.confirmPassword) {
            _uiState.update { it.copy(confirmPasswordHasError = true, confirmPasswordErrorMessage = "passwords are not same") }
            false
        } else {
            _uiState.update { it.copy(confirmPasswordHasError = false, confirmPasswordErrorMessage = "") }
            true
        }

        delay(200)
        if (isUserNameOk && isPasswordIsOK && isEmailOk && isPhoneOk /*&& isConfirmPasswordIsOk */ && isPasswordsMatched) {
            setLoggedIn()
            saveUserName(_uiState.value.userName)
        } else {
            _uiState.update { state ->
                state.copy(isLoading = false)
            }
        }
    }

    fun onEventUpdated(event: SignUpScreenUiEvent) = viewModelScope.launch {
        when (event) {
            is SignUpScreenUiEvent.OnPasswordUpdated -> {
                _uiState.update { state ->
                    state.copy(password = event.password)
                }
            }

            SignUpScreenUiEvent.OnSignUpClicked -> {
                _uiState.update { state ->
                    state.copy(isLoading = true)
                }
                checkInputsAndLogin()
            }

            is SignUpScreenUiEvent.OnUserNameUpdated -> {
                _uiState.update { state ->
                    state.copy(userName = event.name)
                }
            }

            is SignUpScreenUiEvent.OnConfirmPasswordUpdated -> {
                _uiState.update { state ->
                    state.copy(confirmPassword = event.confirmPassword)
                }
            }

            is SignUpScreenUiEvent.OnEmailUpdated -> {
                _uiState.update { state ->
                    state.copy(emailId = event.email)
                }
            }

            is SignUpScreenUiEvent.OnPhoneUpdated -> {
                _uiState.update { state ->
                    state.copy(phoneNumber = event.phone)
                }
            }
        }
    }
}