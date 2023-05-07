@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)

package com.zerocoders.moviestack.screens.login

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.zerocoders.moviestack.R
import com.zerocoders.moviestack.ui.theme.MovieStackTheme
import com.zerocoders.moviestack.ui.theme.montserratBlackTextStyle
import com.zerocoders.moviestack.ui.theme.robotoBlackTextStyle

@Composable
internal fun LoginScreenRoute(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit = {},
    onNavigateToSignUp: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isLoggedIn by viewModel.isLoggedIn.collectAsStateWithLifecycle(initialValue = null)
    val activity = LocalContext.current as? Activity

    LoginScreen(
        uiState = uiState,
        onNavigateToSignUp,
        onLoginScreenEvent = {
            viewModel.onEventUpdated(it)
        }
    )
    LaunchedEffect(
        key1 = isLoggedIn,
        block = {
            if (isLoggedIn == true) {
                onLoginSuccess()
            }
        }
    )
    BackHandler(true) {
        if (isLoggedIn == false) {
            activity?.finish()
        }
    }
}

@Composable
fun LoginScreen(
    uiState: LoginScreenUiState,
    onNavigateToSignUp: () -> Unit = {},
    onLoginScreenEvent: (LoginScreenUiEvent) -> Unit = {}
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val softwareKeyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 8.dp)
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val painter = rememberAsyncImagePainter(model = R.drawable.login)
        Image(
            painter = painter,
            contentDescription = "login_art",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )
        Text(
            text = "LOGIN",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            style = montserratBlackTextStyle
        )
        OutlinedTextField(
            value = uiState.userName,
            onValueChange = { userName ->
                onLoginScreenEvent(LoginScreenUiEvent.OnUserNameUpdated(name = userName))
            },
            isError = uiState.userNameHasError,
            label = { Text(text = "User Name") },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            textStyle = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            trailingIcon = {
                if (uiState.userName.isNotBlank()) {
                    IconButton(onClick = {
                        onLoginScreenEvent(LoginScreenUiEvent.OnUserNameUpdated(name = ""))
                    }) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.Outlined.Cancel,
                            contentDescription = stringResource(id = R.string.clear),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            },
            keyboardActions = KeyboardActions(onDone = {
                softwareKeyboardController?.hide()
                focusManager.moveFocus(FocusDirection.Down)
            }),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        if (uiState.userNameHasError) {
            Text(
                text = uiState.userNameErrorMessage,
                color = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )
        }

        OutlinedTextField(
            value = uiState.password,
            onValueChange = { password ->
                onLoginScreenEvent(LoginScreenUiEvent.OnPasswordUpdated(password = password))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .focusRequester(focusRequester),
            isError = uiState.passwordHasError,
            label = { Text(text = "Password") },
            trailingIcon = {
                if (uiState.password.isNotBlank()) {
                    IconButton(onClick = {
                        onLoginScreenEvent(LoginScreenUiEvent.OnPasswordUpdated(password = ""))
                    }) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.Outlined.Cancel,
                            contentDescription = stringResource(id = R.string.clear),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            },
            visualTransformation = PasswordVisualTransformation(),
            keyboardActions = KeyboardActions(onDone = {
                softwareKeyboardController?.hide()
                focusManager.moveFocus(FocusDirection.Enter)
            }),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )
        if (uiState.passwordHasError) {
            Text(
                text = uiState.passwordErrorMessage,
                color = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )
        }

        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(top = 16.dp),
            )
        } else {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                onClick = {
                    onLoginScreenEvent(LoginScreenUiEvent.OnLoginClicked)
                }
            ) {
                Text(
                    text = "LOGIN",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
        Text(
            text = "Sign Up here...",
            style = robotoBlackTextStyle,
            modifier = Modifier
                .padding(top = 20.dp)
                .clickable {
                    onNavigateToSignUp()
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    MovieStackTheme {
        LoginScreen(uiState = LoginScreenUiState(userName = "Donny Carlson", password = "interesset"))
    }
}