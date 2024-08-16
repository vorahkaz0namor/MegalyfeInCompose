package com.example.megalifecompose.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.megalifecompose.ui.first_pers_choosen.FirstPersChoosenRoute
import com.example.megalifecompose.ui.forgot_password.ForgotPasswordRoute
import com.example.megalifecompose.ui.login.LoginRoute
import com.example.megalifecompose.ui.login.UserAgreementScreen
import com.example.megalifecompose.ui.main.nav_bar.BottomNavigationBar
import com.example.megalifecompose.ui.new_password.NewPasswordRoute
import com.example.megalifecompose.ui.success.CreatePasswordSuccessScreen
import com.example.megalifecompose.ui.support.SupportScreenRoute

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {

    navigation(route = Graph.AUTH, startDestination = AuthScreen.Login.route) {
        composable(route = AuthScreen.Login.route) {
            LoginRoute(
                navigateToPass = {
                    navController.navigate(AuthScreen.NewPassword.route)
                },
                navigateToMain = {
                    navController.navigate(route = AuthScreen.MAIN_SCREEN) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                },
                navigateToForgot = {
                    navController.navigate(AuthScreen.Forgot.route)
                },
                navigateToSupport = {
                    navController.navigate(AuthScreen.Support.route)
                },
                navigateToAgreement = {
                    navController.navigate(AuthScreen.UserAgreement.route)
                }
            )
        }
        composable(route = AuthScreen.NewPassword.route) {
            NewPasswordRoute(navController)
        }
        composable(route = AuthScreen.Forgot.route) {
            ForgotPasswordRoute(navController)
        }
        composable(route = AuthScreen.Success.route) {
            CreatePasswordSuccessScreen(navController)
        }

        composable(route = AuthScreen.Main.route) {
            BottomNavigationBar()
        }

        composable(route = AuthScreen.Support.route) {
            SupportScreenRoute(navController)
        }

        composable(route = AuthScreen.FirstPers.route) {
            FirstPersChoosenRoute(navController)
        }
        composable(route = AuthScreen.UserAgreement.route) {
            UserAgreementScreen { navController.navigateUp() }
        }
    }

}

sealed class AuthScreen(val route: String) {

    companion object {
        const val LOGIN_SCREEN = "login_screen"
        const val FORGOT_SCREEN = "forgot_screen"
        const val NEW_PASSWORD_SCREEN = "new_password_screen"
        const val SUCCESS_SCREEN = "success_screen"
        const val SUPPORT_SCREEN = "support_screen"
        const val MAIN_SCREEN = "main_screen"
        const val FIRST_PERS_SCREEN = "first_pers_screen"
        const val USER_AGREEMENT = "user_agreement"
    }

    data object Login : AuthScreen(LOGIN_SCREEN)
    data object Forgot : AuthScreen(FORGOT_SCREEN)
    data object NewPassword : AuthScreen(NEW_PASSWORD_SCREEN)
    data object Success : AuthScreen(SUCCESS_SCREEN)
    data object Support : AuthScreen(SUPPORT_SCREEN)
    data object Main : AuthScreen(MAIN_SCREEN)
    data object FirstPers : AuthScreen(FIRST_PERS_SCREEN)
    data object UserAgreement : AuthScreen(USER_AGREEMENT)
}