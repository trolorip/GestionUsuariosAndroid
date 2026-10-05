package com.example.gestion.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gestion.ui.screens.LoginScreen
import com.example.gestion.ui.screens.RecoverScreen
import com.example.gestion.ui.screens.RegisterScreen
import com.example.gestion.ui.screens.UsuariosScreen

import android.app.Activity
import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import com.example.gestion.HomeActivity

@Composable
fun AppNavigation() {
    val context = LocalContext.current

    val navController =
        rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        composable("login") {

            LoginScreen(

                onIrRegistro = {
                    navController.navigate("registro")
                },

                onIrRecuperar = {
                    navController.navigate("recuperar")
                },

                onLoginExitoso = {

                    val intent =
                        Intent(
                            context,
                            HomeActivity::class.java
                        )

                    context.startActivity(intent)

                    (context as? Activity)?.finish()
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

        composable("usuarios") {

            UsuariosScreen(
                onCerrarSesion = {

                    navController.navigate("login") {

                        popUpTo("usuarios") {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}