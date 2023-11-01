package com.projects.photos_and_map.ui.screens.authorization

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.projects.photos_and_map.ui.theme.AppTheme


@Composable
fun AuthorizationScreen(
    viewModel: AuthorizationViewModel = viewModel(factory = AuthorizationViewModel.Factory),
    onAuthorization: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val login by viewModel.loginFieldState.collectAsStateWithLifecycle()
    val password by viewModel.passwordFieldState.collectAsStateWithLifecycle()
    val confirmPassword by viewModel.confirmPasswordFieldState.collectAsStateWithLifecycle()
    val isLoginValid by viewModel.isLoginValid.collectAsStateWithLifecycle()
    val isPasswordValid by viewModel.isPasswordValid.collectAsStateWithLifecycle()
    val isConfirmPasswordValid by viewModel.isConfirmPasswordValid.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Tabs(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it },
            modifier = Modifier.fillMaxWidth()
        )

        when (selectedTab) {
            0 -> AuthorizationPage(
                login = login,
                password = password,
                error = error,
                isFormValid = isLoginValid && isPasswordValid,
                onLoginValueChange = viewModel::onLoginValueChange,
                onPasswordValueChange = viewModel::onLoginPasswordValueChange,
                onSubmit = { viewModel.onLogin(onAuthorization) },
                modifier = Modifier.padding(
                    horizontal = AppTheme.dimens.spacing20,
                    vertical = AppTheme.dimens.spacing170
                )
            )
            1 -> AuthorizationPage(
                login = login,
                password = password,
                error = error,
                isFormValid = isLoginValid && isPasswordValid && isConfirmPasswordValid,
                onLoginValueChange = viewModel::onLoginValueChange,
                onPasswordValueChange = viewModel::onRegistrationPasswordValueChange,
                isRegistry = true,
                confirmPassword = confirmPassword,
                onConfirmPasswordValueChange = viewModel::onConfirmPasswordValueChange,
                onSubmit ={ viewModel.onRegistration(onAuthorization) },
                modifier = Modifier.padding(
                    horizontal = AppTheme.dimens.spacing20,
                    vertical = AppTheme.dimens.spacing170
                )
            )
        }
    }
}
