package com.example.gestion.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gestion.ui.screens.Login
import com.example.gestion.ui.screens.RecoverScreen
import com.example.gestion.ui.screens.RegisterScreen

@Composable
fun AppNavigation() {

    val navController =
        rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        composable("login") {

            Login(
                onIrRegistro = {
                    navController.navigate("registro")
                },
                onIrRecuperar = {
                    navController.navigate("recuperar")
                }
            )
        }

        composable("registro") {

            RegisterScreen(
                onVolverLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable("recuperar") {

            RecoverScreen(
                onVolverLogin = {
                    navController.popBackStack()
                }
            )
        }
    }
}