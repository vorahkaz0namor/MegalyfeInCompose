package com.ccink.common.base_views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ccink.resources.BtnContainerColor
import com.ccink.resources.BtnEnabled
import com.ccink.resources.IconColor
import com.ccink.resources.R
import com.ccink.resources.colors
import com.ccink.resources.styles

@Composable
@Preview
private fun TextViewPreview() {
    BaseTextField("Name")
}

@Composable
@Preview
private fun BasePasswordTextFieldPreview() {
    BasePasswordTextField()
}

@Composable
@Preview
private fun BaseLeadingIconTextFieldPreview() {
    BaseLeadingIconTextField()
}

@Composable
fun BaseTextField(hint: String, keyboardType: KeyboardType = KeyboardType.Text, borderColor: Color = Color.Transparent, height: Dp = Dp.Unspecified, text: (String) -> Unit = {}) {

    var txt by remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        value = txt, onValueChange = {
            txt = it
            text(txt)
        },
        placeholder = {
            InitTxtHint(hint = hint)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(height),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {

            }),
        shape = RoundedCornerShape(
            10.dp
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colors.colorB066FF,
            unfocusedBorderColor = borderColor,
            cursorColor = MaterialTheme.colors.colorB066FF,
            focusedContainerColor = MaterialTheme.colors.colorF0EFFF,
            unfocusedContainerColor = MaterialTheme.colors.colorF0EFFF,
        )
    )

}

@Composable
private fun InitTxtHint(hint: String) {
    Text(
        text = hint,
        style = MaterialTheme.styles.s16w400h19,
        color = MaterialTheme.colors.colorA7A3FF
    )
}

@Composable
fun BasePasswordTextField(hint: String = "Password", password: (String) -> Unit = {}, isShown: (Boolean) -> Unit = {}) {

    var textPassword by remember {
        mutableStateOf("")
    }

    var isPasswordShown by remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        value = textPassword,
        onValueChange = {
            textPassword = it
            password(textPassword)
        },
        placeholder = {
            InitTxtHint(hint = hint)
        },
        leadingIcon = {
            Icon(
                painter = painterResource(
                    id = R.drawable.ic_locked
                ),
                contentDescription = null
            )
        },
        trailingIcon = {
            IconButton(onClick = {
                isPasswordShown = !isPasswordShown
                isShown(isPasswordShown)
            }) {
                val icon = if (isPasswordShown) R.drawable.ic_eye_off else R.drawable.ic_password_toggle
                Icon(painter = painterResource(id = icon), contentDescription = null)
            }
        },
        modifier = Modifier
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Go
        ),
        keyboardActions = KeyboardActions(onGo = {

        }),
        shape = RoundedCornerShape(
            10.dp
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedContainerColor = MaterialTheme.colors.colorF0EFFF,
            unfocusedContainerColor = MaterialTheme.colors.colorF0EFFF,
            disabledContainerColor = MaterialTheme.colors.colorF0EFFF,
            cursorColor = MaterialTheme.colors.colorB066FF,
            focusedBorderColor = MaterialTheme.colors.colorB066FF,
            unfocusedBorderColor = Color.Transparent,
            focusedLeadingIconColor = Color.Black,
            unfocusedLeadingIconColor = MaterialTheme.colors.colorA7A3FF,
            focusedTrailingIconColor = Color.Black,
            unfocusedTrailingIconColor = MaterialTheme.colors.colorA7A3FF,
        ),
        visualTransformation = if (isPasswordShown) VisualTransformation.None else PasswordVisualTransformation(),
    )

}

@Composable
fun BaseLeadingIconTextField(hint: String = "Login", icon: Int? = null, keyboardType: KeyboardType = KeyboardType.Text, login: (String) -> Unit = {}) {

    var txt by remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        value = txt,
        onValueChange = {
            txt = it
            login(txt)
        },
        placeholder = {
            InitTxtHint(hint = hint)
        },
        leadingIcon = icon?.let {
            {
                Icon(painter = painterResource(id = icon), contentDescription = null)
            }
        },
        modifier = Modifier
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        shape = RoundedCornerShape(
            10.dp
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedBorderColor = MaterialTheme.colors.colorB066FF,
            unfocusedBorderColor = Color.Transparent,
            focusedContainerColor = MaterialTheme.colors.colorF0EFFF,
            unfocusedContainerColor = MaterialTheme.colors.colorF0EFFF,
            cursorColor = MaterialTheme.colors.colorB066FF,
            focusedLeadingIconColor = Color.Black,
            unfocusedLeadingIconColor = MaterialTheme.colors.colorA7A3FF
        )
    )

}
