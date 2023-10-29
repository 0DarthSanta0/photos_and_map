@file:OptIn(ExperimentalMaterial3Api::class)

package com.projects.photos_and_map.ui.screens.authorization

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.projects.photos_and_map.R
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



@Composable
fun Tabs(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    TabRow(
        selectedTabIndex = selectedTab,
        modifier = modifier,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                color = Color.White,
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab])
            )
        }
    ) {
        AuthTab(
            isSelected = selectedTab == 0,
            text = stringResource(R.string.login),
            onTabSelected = { onTabSelected(0) },
            modifier = Modifier.height(AppTheme.dimens.spacing80)
        )
        AuthTab(
            isSelected = selectedTab == 1,
            text = stringResource(R.string.register),
            onTabSelected = { onTabSelected(1) },
            modifier = Modifier.height(AppTheme.dimens.spacing80)
        )
    }
}

@Composable
fun AuthTab(
    isSelected: Boolean,
    text: String,
    onTabSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    Tab(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primary),
        selected = isSelected,
        onClick = { onTabSelected() }
    ) {
        Text(
            text = text,
            color = Color.White
        )
    }
}

@Composable
fun AuthorizationPage(
    login: String,
    password: String,
    modifier: Modifier = Modifier,
    isFormValid: Boolean,
    confirmPassword: String? = null,
    isRegistry: Boolean = false,
    onLoginValueChange: (String) -> Unit,
    onPasswordValueChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onConfirmPasswordValueChange: (String) -> Unit = {}
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = login,
            onValueChange = onLoginValueChange,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            placeholder = { Text(stringResource(R.string.login_field)) }
        )

        OutlinedTextField(
            value = password,
            onValueChange = onPasswordValueChange,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            placeholder = { Text(stringResource(R.string.password_field)) },
            visualTransformation = PasswordVisualTransformation()
        )

        if(isRegistry) {
            if (confirmPassword != null) {
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = onConfirmPasswordValueChange,
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    placeholder = { Text(stringResource(R.string.confirm_password_field)) },
                    visualTransformation = PasswordVisualTransformation()
                )
            }
        }

        Button(
            enabled = isFormValid,
            onClick = { onSubmit() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(stringResource(if (isRegistry) R.string.register_button else R.string.login_button))
        }
    }
}
