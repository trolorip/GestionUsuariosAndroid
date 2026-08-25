package com.example.gestion.data

import androidx.compose.runtime.mutableStateListOf
import com.example.gestion.model.Usuario

object UsuarioRepository {

    val usuarios = mutableStateListOf(
        Usuario(
            nombre = "Ana",
            correo = "ana@gmail.com",
            password = "1234"
        ),
        Usuario(
            nombre = "Pedro",
            correo = "pedro@gmail.com",
            password = "1234"
        ),
        Usuario(
            nombre = "Sofia",
            correo = "sofia@gmail.com",
            password = "1234"
        ),
        Usuario(
            nombre = "Diego",
            correo = "diego@gmail.com",
            password = "1234"
        ),
        Usuario(
            nombre = "Camila",
            correo = "camila@gmail.com",
            password = "1234"
        )
    )

    fun registrarUsuario(usuario: Usuario): Boolean {

        val existe = usuarios.any {
            it.correo.equals(usuario.correo, ignoreCase = true)
        }

        return if (!existe) {
            usuarios.add(usuario)
            true
        } else {
            false
        }
    }

    fun validarLogin(
        correo: String,
        password: String
    ): Boolean {

        return usuarios.any {
            it.correo.equals(correo, ignoreCase = true) &&
                    it.password == password
        }
    }

    fun buscarUsuario(correo: String): Usuario? {

        return usuarios.find {
            it.correo.equals(correo, ignoreCase = true)
        }
    }

    fun actualizarPassword(
        correo: String,
        nuevaPassword: String
    ): Boolean {

        val indice = usuarios.indexOfFirst {
            it.correo.equals(correo, ignoreCase = true)
        }

        if (indice == -1) {
            return false
        }

        val usuarioActual = usuarios[indice]

        usuarios[indice] = usuarioActual.copy(
            password = nuevaPassword
        )

        return true
    }
}