package com.example.gestion.ui.screens

import android.util.Patterns
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestion.data.UsuarioRepository
import com.example.gestion.model.Usuario

@Composable
fun RegisterScreen(
    onVolverLogin: () -> Unit
) {

    var nombre by rememberSaveable {
        mutableStateOf("")
    }

    var correo by rememberSaveable {
        mutableStateOf("")
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }

    var repetirPassword by rememberSaveable {
        mutableStateOf("")
    }

    var aceptaTerminos by rememberSaveable {
        mutableStateOf(false)
    }

    var mensaje by rememberSaveable {
        mutableStateOf("")
    }

    var esError by rememberSaveable {
        mutableStateOf(false)
    }

    Scaffold { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {

            Text(
                text = "Crear cuenta",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Completa tus datos para registrarte"
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            OutlinedTextField(
                value = nombre,
                onValueChange = {
                    nombre = it
                    mensaje = ""
                },
                label = {
                    Text("Nombre")
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(12.dp)
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
                modifier = Modifier.height(12.dp)
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
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            OutlinedTextField(
                value = repetirPassword,
                onValueChange = {
                    repetirPassword = it
                    mensaje = ""
                },
                label = {
                    Text("Repetir contraseña")
                },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            androidx.compose.foundation.layout.Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Checkbox(
                    checked = aceptaTerminos,
                    onCheckedChange = {
                        aceptaTerminos = it
                    }
                )

                Text(
                    text = "Acepto los términos y condiciones"
                )
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Button(
                onClick = {

                    val correoLimpio = correo.trim()

                    when {

                        nombre.isBlank() ||
                                correoLimpio.isBlank() ||
                                password.isBlank() ||
                                repetirPassword.isBlank() -> {

                            mensaje = "Debes completar todos los campos."
                            esError = true
                        }

                        !Patterns.EMAIL_ADDRESS
                            .matcher(correoLimpio)
                            .matches() -> {

                            mensaje = "Ingresa un correo válido."
                            esError = true
                        }

                        password.length < 4 -> {

                            mensaje =
                                "La contraseña debe tener al menos 4 caracteres."

                            esError = true
                        }

                        password != repetirPassword -> {

                            mensaje = "Las contraseñas no coinciden."
                            esError = true
                        }

                        !aceptaTerminos -> {

                            mensaje =
                                "Debes aceptar los términos y condiciones."

                            esError = true
                        }

                        else -> {

                            val registrado =
                                UsuarioRepository.registrarUsuario(
                                    Usuario(
                                        nombre = nombre.trim(),
                                        correo = correoLimpio,
                                        password = password
                                    )
                                )

                            if (registrado) {

                                mensaje =
                                    "Usuario registrado correctamente."

                                esError = false

                                nombre = ""
                                correo = ""
                                password = ""
                                repetirPassword = ""
                                aceptaTerminos = false

                            } else {

                                mensaje =
                                    "Ya existe un usuario con ese correo."

                                esError = true
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {

                Text("REGISTRAR")
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
                modifier = Modifier.height(20.dp)
            )

            TextButton(
                onClick = onVolverLogin,
                modifier = Modifier.align(
                    Alignment.CenterHorizontally
                )
            ) {

                Text(
                    text = "← Volver al inicio de sesión"
                )
            }
        }
    }
}