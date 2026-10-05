package com.example.gestion.ui.fragments

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.gestion.R
import java.util.Locale

class EscribirFragment :
    Fragment(R.layout.fragment_escribir),
    TextToSpeech.OnInitListener {

    private var textToSpeech: TextToSpeech? = null

    private lateinit var etMensaje: EditText
    private lateinit var tvEstado: TextView

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(
            view,
            savedInstanceState
        )

        etMensaje =
            view.findViewById(
                R.id.etMensaje
            )

        tvEstado =
            view.findViewById(
                R.id.tvEstadoEscribir
            )

        val btnReproducir =
            view.findViewById<Button>(
                R.id.btnReproducir
            )

        val btnLimpiar =
            view.findViewById<Button>(
                R.id.btnLimpiar
            )

        val btnVolver =
            view.findViewById<Button>(
                R.id.btnVolverEscribir
            )

        textToSpeech =
            TextToSpeech(
                requireContext(),
                this
            )

        btnReproducir.setOnClickListener {

            val mensaje =
                etMensaje.text
                    .toString()
                    .trim()

            if (mensaje.isEmpty()) {

                tvEstado.text =
                    "Debes escribir un mensaje."

            } else {

                tvEstado.text =
                    "Reproduciendo mensaje..."

                textToSpeech?.speak(
                    mensaje,
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    "mensajeEscrito"
                )
            }
        }

        btnLimpiar.setOnClickListener {

            etMensaje.text.clear()

            tvEstado.text = ""
        }

        btnVolver.setOnClickListener {

            parentFragmentManager
                .popBackStack()
        }
    }

    override fun onInit(
        status: Int
    ) {

        if (
            status ==
            TextToSpeech.SUCCESS
        ) {

            val resultado =
                textToSpeech?.setLanguage(
                    Locale("es", "CL")
                )

            if (
                resultado ==
                TextToSpeech.LANG_MISSING_DATA ||
                resultado ==
                TextToSpeech.LANG_NOT_SUPPORTED
            ) {

                tvEstado.text =
                    "El idioma no está disponible."
            }

        } else {

            tvEstado.text =
                "No fue posible iniciar el sistema de voz."
        }
    }

    override fun onDestroyView() {

        textToSpeech?.stop()
        textToSpeech?.shutdown()

        super.onDestroyView()
    }
}