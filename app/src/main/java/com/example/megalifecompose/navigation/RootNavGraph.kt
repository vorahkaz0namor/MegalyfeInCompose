package com.example.megalifecompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun RootNavGraph(navController: NavHostController) {
    NavHost(navController = navController, route = Graph.ROOT, startDestination = Graph.AUTH) {
        authNavGraph(navController = navController)
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val AUTH = "auth_graph"
}