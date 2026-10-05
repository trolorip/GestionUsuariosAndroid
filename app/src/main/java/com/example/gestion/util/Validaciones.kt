package com.example.gestion.util

object Validaciones {

    fun camposCompletos(
        correo: String,
        password: String
    ): Boolean {

        return correo.isNotBlank() &&
                password.isNotBlank()
    }

    fun passwordValida(
        password: String
    ): Boolean {

        return password.length >= 6
    }

    fun passwordsCoinciden(
        password: String,
        repetirPassword: String
    ): Boolean {

        return password == repetirPassword
    }
}