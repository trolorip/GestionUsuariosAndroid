package com.example.gestion

import com.example.gestion.util.RegistroValidator
import com.example.gestion.util.UsuarioDataSource
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class MockitoTest {

    @Test
    fun correoExistente_noPermiteRegistro() {

        val repositorio =
            mock(
                UsuarioDataSource::class.java
            )

        `when`(
            repositorio.correoExiste(
                "usuario@gmail.com"
            )
        ).thenReturn(true)

        val validator =
            RegistroValidator(
                repositorio
            )

        assertFalse(
            validator.puedeRegistrar(
                "usuario@gmail.com"
            )
        )
    }

    @Test
    fun correoNuevo_permiteRegistro() {

        val repositorio =
            mock(
                UsuarioDataSource::class.java
            )

        `when`(
            repositorio.correoExiste(
                "nuevo@gmail.com"
            )
        ).thenReturn(false)

        val validator =
            RegistroValidator(
                repositorio
            )

        assertTrue(
            validator.puedeRegistrar(
                "nuevo@gmail.com"
            )
        )
    }
}