package com.projects.photos_and_map.ui.screens.authorization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.github.michaelbull.result.onSuccess
import com.projects.photos_and_map.data.data_store.DataStoreManagerImpl
import com.projects.photos_and_map.data.repositories.login.LoginRepositoryImpl
import com.projects.photos_and_map.domain.use_cases.SignInUseCase
import com.projects.photos_and_map.domain.use_cases.SignUpUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class AuthorizationViewModel(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {
    private val _loginFieldState: MutableStateFlow<String> = MutableStateFlow("")
    val loginFieldState: StateFlow<String> get() = _loginFieldState
    private val _passwordFieldState: MutableStateFlow<String> = MutableStateFlow("")
    val passwordFieldState: StateFlow<String> get() = _passwordFieldState
    private val _confirmPasswordFieldState: MutableStateFlow<String> = MutableStateFlow("")
    val confirmPasswordFieldState: StateFlow<String> get() = _confirmPasswordFieldState
    private val _isLoginValid: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoginValid: StateFlow<Boolean> get() = _isLoginValid
    private val _isPasswordValid: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isPasswordValid: StateFlow<Boolean> get() = _isPasswordValid
    private val _isConfirmPasswordValid: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isConfirmPasswordValid: StateFlow<Boolean> get() = _isConfirmPasswordValid


    fun onLoginValueChange(newValue: String) {
        _isLoginValid.value = newValue.length in 4..32
        _loginFieldState.value = newValue
    }

    fun onLoginPasswordValueChange(newValue: String) {
        _passwordFieldState.value = newValue
        _isPasswordValid.value = newValue.length in 8..500
    }

    fun onRegistrationPasswordValueChange(newValue: String) {
        _passwordFieldState.value = newValue
        validCheck(newValue)
    }

    fun onConfirmPasswordValueChange(newValue: String) {
        _confirmPasswordFieldState.value = newValue
        validCheck(newValue)
    }

    fun onLogin(
        onLogin: () -> Unit
    ) {
        viewModelScope.launch {
            signInUseCase(
                login = _loginFieldState.value,
                password = _passwordFieldState.value
            ).collect { signInRequest ->
                signInRequest.onSuccess {
                    onLogin()
                }
            }
        }
    }

    fun onRegistration(
        onRegistration: () -> Unit
    ) {
        viewModelScope.launch {
            signUpUseCase(
                login = _loginFieldState.value,
                password = _passwordFieldState.value
            ).collect { signUpRequest ->
                signUpRequest.onSuccess {
                    onRegistration()
                }
            }
        }
    }

    private fun validCheck(newValue: String) {
        val isValid =
            newValue.length in 8..500 && _passwordFieldState.value == _confirmPasswordFieldState.value
        _isPasswordValid.value = isValid
        _isConfirmPasswordValid.value = isValid
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val loginRepository = LoginRepositoryImpl(DataStoreManagerImpl)
                AuthorizationViewModel(
                    signInUseCase = SignInUseCase(loginRepository = loginRepository),
                    signUpUseCase = SignUpUseCase(loginRepository = loginRepository)
                )
            }
        }
    }
}
