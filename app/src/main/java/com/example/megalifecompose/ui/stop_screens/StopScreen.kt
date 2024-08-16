package com.example.megalifecompose.ui.stop_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ccink.common.base_views.BaseSpacer
import com.ccink.resources.styles

@Composable
@Preview
private fun StopScreenPreview() {
    StopScreen(rememberNavController())
}

@Composable
fun StopScreenRoute(navController: NavController) {
    StopScreen(navController)
}

@Composable
private fun StopScreen(navController: NavController) {

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        Image(painter = painterResource(id = com.ccink.resources.R.drawable.stop_screen_logo), contentDescription = null)

        BaseSpacer(height = 24.dp)

        Text(text = "Страница в разработке", style = MaterialTheme.styles.s20h24w900)

        BaseSpacer(height = 16.dp)

        Text(text = "Мы работаем над ней,\nчтобы тебе стало интереснее ", style = MaterialTheme.styles.s14h17w400, textAlign = TextAlign.Center)

        BaseSpacer(height = 16.dp)
    }
}
