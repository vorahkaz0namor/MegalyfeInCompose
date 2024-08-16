package com.example.megalifecompose.ui.success

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ccink.resources.R
import com.example.megalifecompose.navigation.AuthScreen
import com.ccink.resources.*
import com.example.megalifecompose.navigation.Graph

@Composable
fun CreatePasswordSuccessScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            modifier = Modifier
                .height(180.dp)
                .width(180.dp)
                .padding(bottom = 20.dp),
            painter = painterResource(id = R.drawable.logo_new_password), contentDescription = "logo"
        )

        Text(
            text = "Пароль успешно создан!",
            style = TextStyle(
                fontSize = 24.sp,
                lineHeight = 28.8.sp,
                fontWeight = FontWeight(600),
                color = Color(0xFF212529),
            )
        )

        Button(
            onClick = {
                navController.navigate(route = AuthScreen.FIRST_PERS_SCREEN) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = BtnEnabled,
                disabledContainerColor = BtnNotEnabled
            ),
            shape = RoundedCornerShape(20),
        ) {

            Text(
                text = "Enter", style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(700),
                    color = Color.White,
                ), modifier = Modifier.padding(vertical = 12.dp)
            )

        }

    }

}

@Preview(showBackground = true)
@Composable
fun CreatePasswordSuccessPreview() {
    CreatePasswordSuccessScreen(rememberNavController())
}