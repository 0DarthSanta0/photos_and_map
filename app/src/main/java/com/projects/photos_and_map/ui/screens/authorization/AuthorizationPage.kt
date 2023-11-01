package com.projects.photos_and_map.ui.screens.authorization

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.projects.photos_and_map.AppErrors
import com.projects.photos_and_map.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorizationPage(
    login: String,
    password: String,
    modifier: Modifier = Modifier,
    isFormValid: Boolean,
    error: AppErrors?,
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

        if (isRegistry) {
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

        if (error != null) {
            Text(
                color = Color.Red,
                text = stringResource(R.string.error),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
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