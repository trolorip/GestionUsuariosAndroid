package com.example.gestion

import com.example.gestion.util.Validaciones
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidacionesTest {

    @Test
    fun camposCompletos_retornaTrue() {

        val resultado =
            Validaciones.camposCompletos(
                "usuario@gmail.com",
                "123456"
            )

        assertTrue(resultado)
    }

    @Test
    fun correoVacio_retornaFalse() {

        val resultado =
            Validaciones.camposCompletos(
                "",
                "123456"
            )

        assertFalse(resultado)
    }

    @Test
    fun passwordCorta_retornaFalse() {

        val resultado =
            Validaciones.passwordValida(
                "123"
            )

        assertFalse(resultado)
    }

    @Test
    fun passwordSeisCaracteres_retornaTrue() {

        val resultado =
            Validaciones.passwordValida(
                "123456"
            )

        assertTrue(resultado)
    }

    @Test
    fun passwordsIguales_retornaTrue() {

        val resultado =
            Validaciones.passwordsCoinciden(
                "123456",
                "123456"
            )

        assertTrue(resultado)
    }

    @Test
    fun passwordsDistintas_retornaFalse() {

        val resultado =
            Validaciones.passwordsCoinciden(
                "123456",
                "654321"
            )

        assertFalse(resultado)
    }
}