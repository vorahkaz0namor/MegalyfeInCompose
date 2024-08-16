package com.ccink.common.base_views

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ccink.resources.R
import com.ccink.resources.colors
import com.ccink.resources.styles

@Preview
@Composable
private fun BaseContainerPreview() {
    BaseContainerWithBackBtn(rememberNavController(), "Восстановление пароля", rememberScrollState())
}

@Composable
fun BaseContainerWithBackBtn(navController: NavController, title: String = "", scrollState: ScrollState, routeTo: String = "", routeFrom: String = "", content: @Composable () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .verticalScroll(scrollState, true)
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        )

        Box {
            Image(painter = painterResource(id = R.drawable.ic_back), contentDescription = "ic_back", modifier = Modifier.clickable {
                navController.navigate(routeTo) {
                    popUpTo(routeFrom) {
                        inclusive = true
                    }
                }
            })
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
        )

        Text(
            text = title, style = MaterialTheme.styles.s24h29w600, color = MaterialTheme.colors.color545454
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
        )

        content()
    }
}
