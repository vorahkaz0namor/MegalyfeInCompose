package com.example.megalifecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.ccink.resources.MegaLifeComposeTheme
import com.example.megalifecompose.navigation.RootNavGraph

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MegaLifeComposeTheme {

                RootNavGraph(navController = rememberNavController())

            }
        }
    }
}
