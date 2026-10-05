package com.example.gestion.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseService {

    private val auth =
        FirebaseAuth.getInstance()

    private val db =
        FirebaseFirestore.getInstance()

    fun registrarUsuario(
        nombre: String,
        correo: String,
        password: String,
        resultado: (Boolean, String) -> Unit
    ) {

        auth.createUserWithEmailAndPassword(
            correo,
            password
        )
            .addOnSuccessListener { authResult ->

                val uid =
                    authResult.user?.uid

                if (uid == null) {

                    resultado(
                        false,
                        "No fue posible crear el usuario."
                    )

                    return@addOnSuccessListener
                }

                val usuario = hashMapOf(
                    "uid" to uid,
                    "nombre" to nombre,
                    "correo" to correo
                )

                db.collection("usuarios")
                    .document(uid)
                    .set(usuario)
                    .addOnSuccessListener {

                        resultado(
                            true,
                            "Usuario registrado correctamente."
                        )
                    }
                    .addOnFailureListener {

                        resultado(
                            false,
                            "La cuenta fue creada, pero ocurrió un error al guardar los datos."
                        )
                    }
            }
            .addOnFailureListener { error ->

                resultado(
                    false,
                    error.localizedMessage
                        ?: "Error al registrar usuario."
                )
            }
    }

    fun iniciarSesion(
        correo: String,
        password: String,
        resultado: (Boolean, String) -> Unit
    ) {

        auth.signInWithEmailAndPassword(
            correo,
            password
        )
            .addOnSuccessListener {

                resultado(
                    true,
                    "Inicio de sesión correcto."
                )
            }
            .addOnFailureListener {

                resultado(
                    false,
                    "Correo o contraseña incorrectos."
                )
            }
    }

    fun recuperarPassword(
        correo: String,
        resultado: (Boolean, String) -> Unit
    ) {

        auth.sendPasswordResetEmail(correo)
            .addOnSuccessListener {

                resultado(
                    true,
                    "Se ha enviado un correo para restablecer tu contraseña."
                )
            }
            .addOnFailureListener { error ->

                resultado(
                    false,
                    error.localizedMessage
                        ?: "No fue posible enviar el correo."
                )
            }
    }

    fun cerrarSesion() {

        auth.signOut()
    }

    fun usuarioActual() =
        auth.currentUser

    fun obtenerUsuarios(
        resultado: (List<Map<String, Any>>) -> Unit
    ) {

        db.collection("usuarios")
            .get()
            .addOnSuccessListener { documentos ->

                val usuarios =
                    documentos.map {
                        it.data
                    }

                resultado(usuarios)
            }
            .addOnFailureListener {

                resultado(emptyList())
            }
    }

    fun actualizarNombre(
        uid: String,
        nuevoNombre: String,
        resultado: (Boolean) -> Unit
    ) {

        db.collection("usuarios")
            .document(uid)
            .update(
                "nombre",
                nuevoNombre
            )
            .addOnSuccessListener {

                resultado(true)
            }
            .addOnFailureListener {

                resultado(false)
            }
    }

    fun eliminarPerfil(
        uid: String,
        resultado: (Boolean) -> Unit
    ) {

        db.collection("usuarios")
            .document(uid)
            .delete()
            .addOnSuccessListener {

                resultado(true)
            }
            .addOnFailureListener {

                resultado(false)
            }
    }
}