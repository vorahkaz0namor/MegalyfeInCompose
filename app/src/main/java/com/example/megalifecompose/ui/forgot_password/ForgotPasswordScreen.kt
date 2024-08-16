package com.example.megalifecompose.ui.forgot_password

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ccink.common.base_views.BaseContainerWithBackBtn
import com.ccink.common.base_views.BaseMainBtn
import com.ccink.common.base_views.BaseSpacer
import com.ccink.common.base_views.BaseTextField
import com.ccink.resources.R
import com.ccink.resources.colors
import com.ccink.resources.styles
import com.example.megalifecompose.navigation.AuthScreen

@Preview(showBackground = true)
@Composable
private fun ForgotPasswordPreview() {
    ForgotPasswordScreen()
}

@Composable
fun ForgotPasswordRoute(navController: NavController, viewModel: ForgotPasswordViewModel = viewModel()) {

    ForgotPasswordScreen(navController = navController) { email ->

    }

}

@Composable
private fun ForgotPasswordScreen(
    navController: NavController = rememberNavController(),
    intent: (email: String) -> Unit = {},
) {

    var emailField by remember {
        mutableStateOf("")
    }

    val isErrorVisible by remember {
        mutableStateOf(false)
    }

    BaseContainerWithBackBtn(
        navController = navController,
        "Восстановление пароля",
        rememberScrollState(),
        AuthScreen.LOGIN_SCREEN,
        AuthScreen.LOGIN_SCREEN
    ) {

        Text(
            text = "Для восстановления пароля введите адрес электронной почты.\n\nМы отправим ссылку на восстановление пароля на указанный адрес.",
            style = MaterialTheme.styles.s13h16w400,
            color = MaterialTheme.colors.color9B9B9B
        )

        BaseSpacer(height = 24.dp)

        BaseTextField(
            hint = "Email",
            KeyboardType.Email,
            borderColor = MaterialTheme.colors.colorCED4DA
        ) {
            emailField = it
        }

        if (isErrorVisible) {

            BaseSpacer(height = 8.dp)

            InitErrorMessage()

        }

        BaseSpacer(height = 16.dp)

        Text(
            text = "Обратиться в техподдержку",
            style = MaterialTheme.styles.s14h17w400,
            color = MaterialTheme.colors.color212529,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable {
                navController.navigate(AuthScreen.SUPPORT_SCREEN)
            })

        BaseSpacer(height = 24.dp)

        BaseMainBtn(title = "Enter", isErrorVisible) {
            intent(emailField)
        }

        BaseSpacer(height = 10.dp)

        Row {

            Text(
                text = "Я вспомнил(а) пароль. ",
                style = MaterialTheme.styles.s12h14w400,
                color = MaterialTheme.colors.color212529
            )

            Text(
                modifier = Modifier.clickable {
                    //todo: Back func
                },
                text = "Вернуться",
                style = MaterialTheme.styles.s12h14w400,
                color = MaterialTheme.colors.colorB066FF,
                textDecoration = TextDecoration.Underline,
            )

        }

    }


}

@Composable
private fun InitErrorMessage() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_error),
            contentDescription = null,
            tint = MaterialTheme.colors.colorFF4E64
        )

        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = "Пользователь отсутствует в системе",
            style = MaterialTheme.styles.s13h16w400,
            color = MaterialTheme.colors.colorFF4E64
        )
    }
}

