package com.example.megalifecompose.ui.new_password

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ccink.common.base_views.BaseSpacer
import com.ccink.model.Constants
import com.ccink.model.model.ChangePasswordRequest
import com.ccink.resources.*
import com.example.megalifecompose.navigation.AuthScreen

@Preview
@Composable
private fun NewPasswordPreview() {
    NewPasswordScreen(rememberNavController())
}

@Composable
fun NewPasswordRoute(navController: NavController, viewModel: NewPasswordViewModel = viewModel()) {
    NewPasswordScreen(
        navController = navController,
        viewModel.uiState.collectAsStateWithLifecycle().value
    ) {
        viewModel.acceptNewPassword(ChangePasswordRequest(it))
    }
}

@Composable
private fun NewPasswordScreen(
    navController: NavController,
    state: NewPasswordViewModel.NewPasswordState? = null,
    onClickAction: (String) -> Unit = {}
) {

    initVMObservers(navController, state)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo_new_password),
                contentDescription = null,
                modifier = Modifier
                    .width(180.dp)
                    .height(180.dp)
            )

            BaseSpacer(height = 20.dp)

            Text(
                text = "Create new password",
                style = MaterialTheme.styles.s24h29w600
            )


            TextFieldsNewPassword(navController, onClickAction)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldsNewPassword(
    navController: NavController,
    onClick: (String) -> Unit,
) {

    var newTextPassword by remember {
        mutableStateOf("")
    }

    var repeatTextPassword by remember {
        mutableStateOf("")
    }

    var isPasswordNotSimilar: Boolean? by remember {
        mutableStateOf(null)
    }

    var isPasswordShown by remember {
        mutableStateOf(true)
    }

    var progress by remember {
        mutableFloatStateOf(0.0f)
    }

    var isErrorVisible: Boolean? by remember {
        mutableStateOf(null)
    }

    var msgColor by remember {
        mutableStateOf(Color(0xFF9B9B9B))
    }

    Column {

        OutlinedTextField(
            value = newTextPassword,
            onValueChange = {
                newTextPassword = it
                isPasswordNotSimilar = repeatTextPassword != it
                progress =
                    Constants.PROGRESS_MAX_VALUE
                        .div(it.length + 1)
                        .times(isPasswordValid(it).size)
                isErrorVisible =
                    isPasswordValid(it).size != (it.length + 1)
                msgColor = if (isErrorVisible == true) ErrorColor else Color(0xFF9B9B9B)
            },
            isError = isErrorVisible == true,
            placeholder = {
                Text(
                    text = "New password",
                    style = MaterialTheme.styles.s16w400h19,
                    color = MaterialTheme.colors.colorA7A3FF
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_locked),
                    contentDescription = null
                )
            },
            trailingIcon = {
                IconButton(onClick = { isPasswordShown = !isPasswordShown }) {
                    val icon =
                        if (isPasswordShown) R.drawable.ic_eye_off else R.drawable.ic_password_toggle
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = if (isPasswordShown) "Show Password" else "Hide Password"
                    )
                }
            },
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Go
            ),
            keyboardActions = KeyboardActions(
                onGo = {

                }
            ),
            shape = RoundedCornerShape(
                10.dp
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BtnEnabled,
                unfocusedBorderColor = Color.Transparent,
                cursorColor = BtnEnabled,
                focusedLeadingIconColor = Color.Black,
                unfocusedLeadingIconColor = IconColor,
                focusedTrailingIconColor = Color.Black,
                unfocusedTrailingIconColor = IconColor,
                focusedContainerColor = if (isErrorVisible == true) BtnContainerErrorColor else BtnContainerColor,
                unfocusedContainerColor = if (isErrorVisible == true) BtnContainerErrorColor else BtnContainerColor,
                errorBorderColor = ErrorColor,
                errorTrailingIconColor = Color.Black
            ),
            visualTransformation = if (isPasswordShown) VisualTransformation.None else PasswordVisualTransformation(),
        )

        isErrorVisible?.let {

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                shape = RoundedCornerShape(8.dp),
                color = Color.Gray
            ) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    progress = progress,
                    color = if (it) ProgressColor else SuccessColor,
                    trackColor = ProgressTrackColor
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                val icon = if (it) R.drawable.ic_error else R.drawable.ic_success
                val color = if (it) ErrorColor else SuccessColor
                val msg = if (it) "Error" else "Success"
                Icon(painter = painterResource(id = icon), contentDescription = null, tint = color)
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = msg,
                    style = TextStyle(
                        fontSize = 13.sp,
                        lineHeight = 15.6.sp,
                        fontWeight = FontWeight(500),
                        color = color,
                    )
                )

            }

        }

        if (isErrorVisible == null || isErrorVisible == true) {
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = "Пароль должен состоять как минимум из 6 символов и содержать допутимые символы:\nA-Z, a-z, 0-9, ! , # ,\$ ,%, &, _ , -",
                style = TextStyle(
                    fontSize = 13.sp,
                    lineHeight = 15.6.sp,
                    fontWeight = FontWeight(400),
                    color = msgColor,
                )
            )
        }


        var isRepeatPasswordShown by remember {
            mutableStateOf(true)
        }

        OutlinedTextField(
            value = repeatTextPassword,
            onValueChange = {
                repeatTextPassword = it
                isPasswordNotSimilar = newTextPassword != it
            },
            isError = isPasswordNotSimilar == true,
            enabled = isErrorVisible == false,
            placeholder = {
                Text(
                    text = "Repeat password",
                    style = MaterialTheme.styles.s16w400h19,
                    color = MaterialTheme.colors.colorA7A3FF
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_locked),
                    contentDescription = null
                )
            },
            trailingIcon = {
                IconButton(onClick = { isRepeatPasswordShown = !isRepeatPasswordShown }) {
                    val icon =
                        if (isRepeatPasswordShown) R.drawable.ic_eye_off else R.drawable.ic_password_toggle
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = if (isRepeatPasswordShown) "Show Password" else "Hide Password"
                    )
                }
            },
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Go
            ),
            keyboardActions = KeyboardActions(
                onGo = {

                }
            ),
            shape = RoundedCornerShape(
                10.dp
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = BtnEnabled,
                unfocusedBorderColor = Color.Transparent,
                cursorColor = BtnEnabled,
                focusedLeadingIconColor = Color.Black,
                unfocusedLeadingIconColor = IconColor,
                focusedTrailingIconColor = Color.Black,
                unfocusedTrailingIconColor = IconColor,
                disabledBorderColor = Color.Transparent,
                containerColor = if (isPasswordNotSimilar == true) BtnContainerErrorColor else BtnContainerColor,
                errorBorderColor = ErrorColor,
                errorTrailingIconColor = Color.Black
            ),
            visualTransformation = if (isRepeatPasswordShown) VisualTransformation.None else PasswordVisualTransformation(),
        )

        if (isPasswordNotSimilar == true) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Icon(
                    painter = painterResource(id = R.drawable.ic_error),
                    contentDescription = "icon success",
                    tint = ErrorColor
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = "Not similar",
                    style = TextStyle(
                        fontSize = 13.sp,
                        lineHeight = 15.6.sp,
                        fontWeight = FontWeight(500),
                        color = ErrorColor,
                    )
                )

            }
        }

        Button(
            onClick = {
                onClick(newTextPassword)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = BtnEnabled,
                disabledContainerColor = BtnNotEnabled
            ),
            shape = RoundedCornerShape(20),
            enabled = isPasswordNotSimilar == false
        ) {

            Text(
                text = "Accept", style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(700),
                    color = Color.White,
                ), modifier = Modifier.padding(vertical = 12.dp)
            )

        }


    }

}

enum class PasswordError {
    LENGTH,
    ALLOWED_CHARACTER
}

fun isPasswordValid(password: String): List<PasswordError> {
    val success = mutableListOf<PasswordError>()

    if (password.length >= Constants.REQUIRED_PASSWORD_LENGTH &&
        !success.contains(PasswordError.LENGTH)) {
            success.add(PasswordError.LENGTH)
    }
    password.map {
        if (it in 'a'..'z' ||
                it in 'A'..'Z' ||
                it.isDigit() ||
                it in "!#\$%&_-") {
            success.add(PasswordError.ALLOWED_CHARACTER)
        }
    }

    return success
}

private fun initVMObservers(
    navController: NavController,
    state: NewPasswordViewModel.NewPasswordState?
) {
    state?.let { state ->
        when (state) {
            is NewPasswordViewModel.NewPasswordState.Success -> {

                navController.navigate(route = AuthScreen.SUCCESS_SCREEN)

            }

            is NewPasswordViewModel.NewPasswordState.Error -> {

                throw Exception("notSimular")

            }

            else -> {}
        }
    }
}