package com.example.gestion.util

interface UsuarioDataSource {

    fun correoExiste(
        correo: String
    ): Boolean
}

class RegistroValidator(
    private val dataSource: UsuarioDataSource
) {

    fun puedeRegistrar(
        correo: String
    ): Boolean {

        return !dataSource
            .correoExiste(correo)
    }
}