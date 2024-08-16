package com.example.megalifecompose.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ccink.common.base_views.BaseLeadingIconTextField
import com.ccink.common.base_views.BaseMainBtn
import com.ccink.common.base_views.BasePasswordTextField
import com.ccink.common.base_views.BaseSpacer
import com.ccink.common.base_views.BaseSystemUiController
import com.ccink.model.model.AuthRequest
import com.ccink.resources.R
import com.ccink.resources.colors
import com.ccink.resources.styles
import com.example.megalifecompose.navigation.AuthScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Preview(showBackground = true)
@Composable
private fun LoginPreview() {
    LoginScreen()
}

@Composable
fun LoginRoute(
    viewModel: LoginViewModel = viewModel(),
    navigateToPass: () -> Unit,
    navigateToMain: () -> Unit,
    navigateToForgot: () -> Unit,
    navigateToSupport: () -> Unit,
    navigateToAgreement: () -> Unit,
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    state.HandlingLoadState(
        onDismissRequest = { viewModel.sentEvent(AuthEvent.OnError) },
        navigateToChangePass = navigateToPass,
        navigateToMain = navigateToMain
    ) {
        LoginScreen(
            navigateToForgot = navigateToForgot,
            navigateToSupport = navigateToSupport,
            navigateToAgreement = navigateToAgreement
        ) { e, p ->
            viewModel.sentEvent(AuthEvent.CheckUser(AuthRequest(e, p)))
        }
    }

}

@Composable
private fun LoginScreen(
    navigateToForgot: () -> Unit = {},
    navigateToSupport: () -> Unit = {},
    navigateToAgreement: () -> Unit = {},
    onBtnClick: (String, String) -> Unit = { _, _ -> },
) {

    BaseSystemUiController(systemUiController = rememberSystemUiController())

    var textPassword by remember {
        mutableStateOf("")
    }

    var textLogin by remember {
        mutableStateOf("")
    }

    var isPasswordShown by remember {
        mutableStateOf(true)
    }

    var isBtnEnabled by remember {
        mutableStateOf(true)
    }

    isBtnEnabled = textLogin.isNotEmpty() && textPassword.isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .windowInsetsPadding(WindowInsets.systemBars),
        verticalArrangement = Arrangement.Center
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        /**
                         * TODO: S T U B !!!
                         */
                        onBtnClick("admin@example.com", "123456")
                    }, painter = painterResource(id = R.drawable.logo_login),
                contentDescription = null
            )

            BaseSpacer(height = 60.dp)

            BaseLeadingIconTextField(
                hint = "Логин",
                icon = R.drawable.ic_user,
                KeyboardType.Email
            ) {
                textLogin = it
            }

            BaseSpacer(height = 16.dp)

            BasePasswordTextField(
                hint = "Пароль",
                {
                    textPassword = it
                },
                {
                    isPasswordShown = it
                }
            )

            BaseSpacer(height = 10.dp)

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    modifier = Modifier.clickable {
                        navigateToForgot()
                    }, text = "Forgot the password?",
                    style = MaterialTheme.styles.s14h17w400,
                    color = Color.Black
                )

            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    modifier = Modifier.clickable {
                        navigateToSupport()
                    },
                    text = "Обратиться в техподдержку",
                    style = MaterialTheme.styles.s14h17w400,
                    color = MaterialTheme.colors.color212529,
                    textDecoration = TextDecoration.Underline
                )
            }

            BaseMainBtn(title = "Enter", isBtnEnabled) {
                onBtnClick(textLogin, textPassword)
            }

            BaseSpacer(height = 10.dp)

            Text(
                modifier = Modifier.clickable {
                    navigateToAgreement()
                },
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.Black)) {
                        append("Нажимая кнопку, я соглашаюсь с условиями ")
                    }
                    withStyle(style = SpanStyle(color = MaterialTheme.colors.colorB066FF)) {
                        append("Пользовательского соглашения ")
                    }
                },
                style = MaterialTheme.styles.s12h14w400,
            )

        }


    }

}



