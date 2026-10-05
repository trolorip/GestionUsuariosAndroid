package com.example.gestion.ui.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.gestion.R
import java.util.Locale

class HablarFragment :
    Fragment(R.layout.fragment_hablar) {

    private lateinit var tvTextoReconocido:
            TextView

    private val reconocimientoVoz =
        registerForActivityResult(
            ActivityResultContracts
                .StartActivityForResult()
        ) { resultado ->

            if (
                resultado.resultCode ==
                Activity.RESULT_OK
            ) {

                val textos =
                    resultado.data
                        ?.getStringArrayListExtra(
                            RecognizerIntent.EXTRA_RESULTS
                        )

                if (
                    !textos.isNullOrEmpty()
                ) {

                    tvTextoReconocido.text =
                        textos[0]
                }
            }
        }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        super.onViewCreated(
            view,
            savedInstanceState
        )

        tvTextoReconocido =
            view.findViewById(
                R.id.tvTextoReconocido
            )

        val btnEscuchar =
            view.findViewById<Button>(
                R.id.btnEscuchar
            )

        val btnLimpiar =
            view.findViewById<Button>(
                R.id.btnLimpiarHablar
            )

        val btnVolver =
            view.findViewById<Button>(
                R.id.btnVolverHablar
            )

        btnEscuchar.setOnClickListener {

            iniciarReconocimiento()
        }

        btnLimpiar.setOnClickListener {

            tvTextoReconocido.text =
                "El texto reconocido aparecerá aquí"
        }

        btnVolver.setOnClickListener {

            parentFragmentManager
                .popBackStack()
        }
    }

    private fun iniciarReconocimiento() {

        val intent =
            Intent(
                RecognizerIntent
                    .ACTION_RECOGNIZE_SPEECH
            ).apply {

                putExtra(
                    RecognizerIntent
                        .EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent
                        .LANGUAGE_MODEL_FREE_FORM
                )

                putExtra(
                    RecognizerIntent
                        .EXTRA_LANGUAGE,
                    Locale("es", "CL")
                        .toLanguageTag()
                )

                putExtra(
                    RecognizerIntent
                        .EXTRA_PROMPT,
                    "Habla ahora"
                )
            }

        reconocimientoVoz.launch(intent)
    }
}