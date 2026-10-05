package com.example.gestion.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.gestion.R
import com.example.gestion.data.FirebaseService

class UsuariosFragment :
    Fragment(R.layout.fragment_usuarios) {

    private lateinit var contenedorUsuarios:
            LinearLayout

    private lateinit var tvEstado:
            TextView

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        super.onViewCreated(
            view,
            savedInstanceState
        )

        contenedorUsuarios =
            view.findViewById(
                R.id.contenedorUsuarios
            )

        tvEstado =
            view.findViewById(
                R.id.tvEstadoUsuarios
            )

        val btnVolver =
            view.findViewById<Button>(
                R.id.btnVolverUsuarios
            )

        btnVolver.setOnClickListener {

            parentFragmentManager
                .popBackStack()
        }

        cargarUsuarios()
    }

    private fun cargarUsuarios() {

        tvEstado.text =
            "Cargando usuarios..."

        FirebaseService.obtenerUsuarios { usuarios ->

            contenedorUsuarios
                .removeAllViews()

            if (usuarios.isEmpty()) {

                tvEstado.text =
                    "No hay usuarios registrados."

                return@obtenerUsuarios
            }

            tvEstado.text =
                "Total de usuarios: ${usuarios.size}"

            usuarios.forEach { usuario ->

                agregarUsuario(usuario)
            }
        }
    }

    private fun agregarUsuario(
        usuario: Map<String, Any>
    ) {

        val uid =
            usuario["uid"]
                ?.toString()
                ?: return

        val nombre =
            usuario["nombre"]
                ?.toString()
                ?: "Sin nombre"

        val correo =
            usuario["correo"]
                ?.toString()
                ?: "Sin correo"

        val contenedor =
            LinearLayout(
                requireContext()
            )

        contenedor.orientation =
            LinearLayout.VERTICAL

        contenedor.setPadding(
            16,
            20,
            16,
            20
        )

        val tvNombre =
            TextView(
                requireContext()
            )

        tvNombre.text =
            nombre

        tvNombre.textSize =
            20f

        val tvCorreo =
            TextView(
                requireContext()
            )

        tvCorreo.text =
            correo

        tvCorreo.textSize =
            16f

        val btnEditar =
            Button(
                requireContext()
            )

        btnEditar.text =
            "EDITAR NOMBRE"

        val btnEliminar =
            Button(
                requireContext()
            )

        btnEliminar.text =
            "ELIMINAR"

        btnEditar.setOnClickListener {

            mostrarEditar(
                uid = uid,
                nombreActual = nombre
            )
        }

        btnEliminar.setOnClickListener {

            confirmarEliminar(
                uid = uid,
                nombre = nombre
            )
        }

        contenedor.addView(
            tvNombre
        )

        contenedor.addView(
            tvCorreo
        )

        contenedor.addView(
            btnEditar
        )

        contenedor.addView(
            btnEliminar
        )

        contenedorUsuarios.addView(
            contenedor
        )
    }

    private fun mostrarEditar(
        uid: String,
        nombreActual: String
    ) {

        val entrada =
            EditText(
                requireContext()
            )

        entrada.setText(
            nombreActual
        )

        AlertDialog.Builder(
            requireContext()
        )
            .setTitle(
                "Editar nombre"
            )
            .setView(
                entrada
            )
            .setPositiveButton(
                "Guardar"
            ) { _, _ ->

                val nuevoNombre =
                    entrada.text
                        .toString()
                        .trim()

                if (
                    nuevoNombre.isNotEmpty()
                ) {

                    FirebaseService
                        .actualizarNombre(
                            uid,
                            nuevoNombre
                        ) { correcto ->

                            if (correcto) {

                                tvEstado.text =
                                    "Usuario actualizado."

                                cargarUsuarios()

                            } else {

                                tvEstado.text =
                                    "No fue posible actualizar."
                            }
                        }
                }
            }
            .setNegativeButton(
                "Cancelar",
                null
            )
            .show()
    }

    private fun confirmarEliminar(
        uid: String,
        nombre: String
    ) {

        AlertDialog.Builder(
            requireContext()
        )
            .setTitle(
                "Eliminar usuario"
            )
            .setMessage(
                "¿Deseas eliminar el perfil de $nombre?"
            )
            .setPositiveButton(
                "Eliminar"
            ) { _, _ ->

                FirebaseService
                    .eliminarPerfil(
                        uid
                    ) { correcto ->

                        if (correcto) {

                            tvEstado.text =
                                "Usuario eliminado."

                            cargarUsuarios()

                        } else {

                            tvEstado.text =
                                "No fue posible eliminar el usuario."
                        }
                    }
            }
            .setNegativeButton(
                "Cancelar",
                null
            )
            .show()
    }
}