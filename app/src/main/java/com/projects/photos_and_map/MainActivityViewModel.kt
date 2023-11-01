package com.projects.photos_and_map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.onSuccess
import com.projects.photos_and_map.domain.use_cases.IsAuthorizedCheckUseCase
import com.projects.photos_and_map.ui.navigation.Screens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class MainActivityViewModel(
    private val isAuthorizedCheckUseCase: IsAuthorizedCheckUseCase
) : ViewModel() {
    private val _route: MutableStateFlow<Screens?> = MutableStateFlow(null)
    val route: StateFlow<Screens?> get() = _route

    init {
        checkStatus()
    }

    private fun checkStatus() {
        viewModelScope.launch {
            isAuthorizedCheckUseCase().onSuccess { isAuth ->
                _route.value = if (isAuth) Screens.MainScreen else Screens.AuthorizationScreen
            }
        }
    }
}
