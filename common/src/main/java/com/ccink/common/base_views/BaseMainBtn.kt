package com.ccink.common.base_views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ccink.resources.colors
import com.ccink.resources.styles


@Composable
@Preview
private fun PreviewBaseMainBtn() {
    BaseMainBtn(title = "Выбрать")
}

@Composable
fun BaseMainBtn(title: String, btnVisibility: Boolean = true, onClickAction: () -> Unit = {}) {
    Button(
        onClick = {
            onClickAction()
        },
        modifier = Modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colors.colorB066FF,
            disabledContainerColor = MaterialTheme.colors.colorD4AAFF
        ),
        shape = RoundedCornerShape(20),
        enabled = btnVisibility
    ) {

        Text(
            text = title,
            style = MaterialTheme.styles.s16w700,
            color = Color.White,
            modifier = Modifier.padding(vertical = 12.dp)
        )

    }
}