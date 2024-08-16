package com.example.megalifecompose.ui.support

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.ccink.common.base_views.BaseContainerWithBackBtn
import com.ccink.common.base_views.BaseTextField
import com.ccink.model.Constants
import com.ccink.resources.R
import com.ccink.resources.colors
import com.ccink.resources.styles
import com.example.megalifecompose.navigation.AuthScreen

@Composable
@Preview(showBackground = true)
private fun SupportPreview() {
    SupportScreen(rememberNavController())
}

@Composable
fun SupportScreenRoute(navController: NavController, viewModel: SupportScreenViewModel = viewModel()) {
    SupportScreen(navController, viewModel.photos.collectAsStateWithLifecycle().value) {
        viewModel.addPhoto(it)
    }
}

@Composable
private fun SupportScreen(navController: NavController, listOfUri: List<Uri> = listOf(), photoPicker: (Uri) -> Unit = {}) {

    var generalTxt by remember {
        mutableStateOf("")
    }

    BaseContainerWithBackBtn(navController = navController, title = "Обращение в поддержку", rememberScrollState(), AuthScreen.LOGIN_SCREEN, AuthScreen.SUPPORT_SCREEN) {

        val options = listOf("Сообщить об ошибке", "Проблема с паролем", "Пожелания и предложения", "Другое")

        InitTextField(hint = "Имя*") {

        }

        InitTextField(hint = "Электронная почта*", KeyboardType.Email) {

        }

        InitTextField(hint = "Телефон", KeyboardType.Phone) {

        }

        InitDropDownMenu("Выберите тему*", options) {

        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )

        OutlinedTextField(value = generalTxt, onValueChange = {
            if (it.length <= Constants.MAX_CHAR) generalTxt = it
        }, placeholder = {
            Text(
                text = "Опишите проблему*", style = MaterialTheme.styles.s16w400h19, color = MaterialTheme.colors.colorA7A3FF
            )
        }, modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
            supportingText = {
                Text(
                    text = "${generalTxt.length} / ${Constants.MAX_CHAR}",
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.styles.s13h16w400,
                    textAlign = TextAlign.End,
                )
            }, keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
            ), keyboardActions = KeyboardActions(onDone = {

            }), shape = RoundedCornerShape(
                10.dp
            ), colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colors.colorB066FF,
                unfocusedBorderColor = Color.Transparent,
                cursorColor = MaterialTheme.colors.colorB066FF,
                focusedContainerColor = MaterialTheme.colors.colorF0EFFF,
                unfocusedContainerColor = MaterialTheme.colors.colorF0EFFF,
            )
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = MaterialTheme.colors.color212529)) {
                    append("Прикрепить файлы \n")
                }
                withStyle(style = SpanStyle(color = MaterialTheme.colors.color9B9B9B)) {
                    append("PNG, JPEG, PFD, DOC не более 5 мб ")
                }
            }, style = MaterialTheme.styles.s14h17w400
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )

        InitPhotoAdder(listOfUri = listOfUri) {
            photoPicker(it)
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
        )

        Button(
            onClick = {


            },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colors.colorB066FF, disabledContainerColor = MaterialTheme.colors.colorD4AAFF
            ),
            shape = RoundedCornerShape(20),
//            enabled = isBtnEnabled
        ) {

            Text(text = "Отправить", style = MaterialTheme.styles.s16w700, modifier = Modifier.padding(vertical = 12.dp), color = Color.White)

        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
        )


    }


}

@Composable
private fun InitPhotoAdder(listOfUri: List<Uri>, photoPicker: (Uri) -> Unit) {

    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->

        uri?.let {

            val size = context.contentResolver.openInputStream(it)?.use { str ->
                str.available().toLong()
            } ?: 0

            if (size <= 5 * 1024 * 1024) {
                photoPicker(it)
            } else {
                // Размер превышает 5 МБ
                // showMessage("Selected image is too large. Maximum size is 5 MB.")
            }

        }
    }

    LazyRow(content = {
        item {

            listOfUri.forEach {
                AsyncImage(
                    model = it,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(4.dp)
                        .size(80.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop,
                )
            }

            IconButton(modifier = Modifier.size(80.dp), onClick = { launcher.launch("image/*") }) {
                Image(painter = painterResource(id = R.drawable.btn_add2), contentDescription = null)
            }
        }

    })

}

@Composable
private fun InitTextField(hint: String, keyboardType: KeyboardType = KeyboardType.Text, txt: (String) -> Unit) {

    BaseTextField(hint = hint, keyboardType = keyboardType) {
        txt(it)
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(16.dp)
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InitDropDownMenu(hint: String, options: List<String>, content: (String?) -> Unit) {

    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText: String? by remember { mutableStateOf(null) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {

        OutlinedTextField(
            value = selectedOptionText ?: "",
            onValueChange = {},
            readOnly = true,
            placeholder = {
                Text(
                    text = hint, style = MaterialTheme.styles.s16w400h19, color = MaterialTheme.colors.colorA7A3FF
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(), shape = RoundedCornerShape(
                10.dp
            ), colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colors.colorB066FF,
                unfocusedBorderColor = Color.Transparent,
                cursorColor = MaterialTheme.colors.colorB066FF,
                focusedContainerColor = MaterialTheme.colors.colorF0EFFF,
                unfocusedContainerColor = MaterialTheme.colors.colorF0EFFF,
            )
        )

        DropdownMenu(
            modifier = Modifier
                .background(Color.White, shape = RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .exposedDropdownSize(),
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            offset = DpOffset(0.dp, 5.dp)
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = selectionOption,
                            style = MaterialTheme.styles.s14h17w400,
                            color = MaterialTheme.colors.color212529
                        )
                    },
                    onClick = {
                        selectedOptionText = selectionOption
                        content(selectedOptionText)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }

}
