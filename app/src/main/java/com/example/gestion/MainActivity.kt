package com.example.gestion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.gestion.navigation.AppNavigation
import com.example.gestion.ui.theme.GestionTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)

        setContent {

            GestionTheme {

                AppNavigation()
            }
        }
    }
}