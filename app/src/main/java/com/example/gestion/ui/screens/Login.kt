package com.example.gestion.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestion.data.UsuarioRepository

@Composable
fun Login(
    onIrRegistro: () -> Unit = {},
    onIrRecuperar: () -> Unit = {}
) {

    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var mostrarPassword by remember { mutableStateOf(false) }
    var mensaje by remember { mutableStateOf("") }
    var esError by remember { mutableStateOf(false) }

    Scaffold { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Gestión de Usuarios",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Iniciar sesión",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(
                modifier = Modifier.height(32.dp)
            )

            OutlinedTextField(
                value = correo,
                onValueChange = {
                    correo = it
                    mensaje = ""
                },
                label = {
                    Text("Correo electrónico")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    mensaje = ""
                },
                label = {
                    Text("Contraseña")
                },
                singleLine = true,
                visualTransformation =
                    if (mostrarPassword) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            mostrarPassword = !mostrarPassword
                        }
                    ) {
                        Text(
                            text =
                                if (mostrarPassword) {
                                    "Ocultar"
                                } else {
                                    "Ver"
                                }
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Button(
                onClick = {

                    if (correo.isBlank() || password.isBlank()) {

                        mensaje = "Debes completar todos los campos."
                        esError = true

                    } else {

                        val loginCorrecto =
                            UsuarioRepository.validarLogin(
                                correo.trim(),
                                password
                            )

                        if (loginCorrecto) {

                            mensaje = "Inicio de sesión correcto."
                            esError = false

                        } else {

                            mensaje = "Correo o contraseña incorrectos."
                            esError = true
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "INICIAR SESIÓN"
                )
            }

            if (mensaje.isNotEmpty()) {

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Text(
                    text = mensaje,
                    color =
                        if (esError) {
                            MaterialTheme.colorScheme.error
                        } else {
                            MaterialTheme.colorScheme.primary
                        }
                )
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            TextButton(
                onClick = onIrRecuperar
            ) {
                Text(
                    text = "¿Olvidaste tu contraseña?"
                )
            }

            TextButton(
                onClick = onIrRegistro
            ) {
                Text(
                    text = "¿No tienes cuenta? Regístrate"
                )
            }
        }
    }
}