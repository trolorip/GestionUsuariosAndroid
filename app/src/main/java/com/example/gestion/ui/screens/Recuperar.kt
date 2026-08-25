package com.example.gestion.ui.screens

import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestion.data.UsuarioRepository

@Composable
fun RecoverScreen(
    onVolverLogin: () -> Unit
) {

    var correo by rememberSaveable {
        mutableStateOf("")
    }

    var mensajeError by rememberSaveable {
        mutableStateOf("")
    }

    var mostrarPopup by rememberSaveable {
        mutableStateOf(false)
    }

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
                text = "Recuperar contraseña",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Ingresa el correo asociado a tu cuenta"
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            OutlinedTextField(
                value = correo,
                onValueChange = {
                    correo = it
                    mensajeError = ""
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

            Button(
                onClick = {

                    val correoLimpio = correo.trim()

                    if (
                        !Patterns.EMAIL_ADDRESS
                            .matcher(correoLimpio)
                            .matches()
                    ) {

                        mensajeError =
                            "Ingresa un correo electrónico válido."

                    } else {

                        val usuario =
                            UsuarioRepository.buscarUsuario(
                                correoLimpio
                            )

                        if (usuario != null) {

                            mensajeError = ""
                            mostrarPopup = true

                        } else {

                            mensajeError =
                                "No existe una cuenta asociada a ese correo."
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "RECUPERAR CONTRASEÑA"
                )
            }

            if (mensajeError.isNotEmpty()) {

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Text(
                    text = mensajeError,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            TextButton(
                onClick = onVolverLogin
            ) {

                Text(
                    text = "← Volver al inicio de sesión"
                )
            }
        }
    }

    if (mostrarPopup) {

        AlertDialog(
            onDismissRequest = {
                mostrarPopup = false
            },

            title = {
                Text(
                    text = "Correo enviado"
                )
            },

            text = {
                Text(
                    text =
                        "Se ha enviado un mensaje a $correo para restablecer tu contraseña."
                )
            },

            confirmButton = {

                Button(
                    onClick = {
                        mostrarPopup = false
                        onVolverLogin()
                    }
                ) {

                    Text(
                        text = "Aceptar"
                    )
                }
            }
        )
    }
}