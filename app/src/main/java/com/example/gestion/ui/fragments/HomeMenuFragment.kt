package com.example.gestion.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.gestion.MainActivity
import com.example.gestion.R
import com.example.gestion.data.FirebaseService

class HomeMenuFragment :
    Fragment(R.layout.fragment_home_menu) {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(
            view,
            savedInstanceState
        )

        val btnEscribir =
            view.findViewById<Button>(
                R.id.btnEscribir
            )

        val btnHablar =
            view.findViewById<Button>(
                R.id.btnHablar
            )

        val btnUsuarios =
            view.findViewById<Button>(
                R.id.btnUsuarios
            )

        val btnCerrarSesion =
            view.findViewById<Button>(
                R.id.btnCerrarSesion
            )

        btnEscribir.setOnClickListener {

            parentFragmentManager.commit {

                replace(
                    R.id.fragmentContainer,
                    EscribirFragment()
                )

                addToBackStack(null)
            }
        }

        btnHablar.setOnClickListener {

            parentFragmentManager.commit {

                replace(
                    R.id.fragmentContainer,
                    HablarFragment()
                )

                addToBackStack(null)
            }
        }

        btnUsuarios.setOnClickListener {

            parentFragmentManager.commit {

                replace(
                    R.id.fragmentContainer,
                    UsuariosFragment()
                )

                addToBackStack(null)
            }
        }

        btnCerrarSesion.setOnClickListener {

            FirebaseService.cerrarSesion()

            val intent =
                Intent(
                    requireContext(),
                    MainActivity::class.java
                )

            startActivity(intent)

            requireActivity().finish()
        }
    }
}