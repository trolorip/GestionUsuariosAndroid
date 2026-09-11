package com.example.gestion.util

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.speech.tts.TextToSpeech
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

class AccessibilityFeedback(
    private val context: Context
) {

    private var textToSpeech: TextToSpeech? = null
    private var vozDisponible = false

    init {

        textToSpeech = TextToSpeech(
            context.applicationContext
        ) { estado ->

            if (estado == TextToSpeech.SUCCESS) {

                val resultado =
                    textToSpeech?.setLanguage(
                        Locale("es", "CL")
                    )

                vozDisponible =
                    resultado != TextToSpeech.LANG_MISSING_DATA &&
                            resultado != TextToSpeech.LANG_NOT_SUPPORTED

                textToSpeech?.setSpeechRate(0.95f)
            }
        }
    }

    fun hablar(mensaje: String) {

        if (vozDisponible) {

            textToSpeech?.speak(
                mensaje,
                TextToSpeech.QUEUE_FLUSH,
                null,
                "mensaje_accesibilidad"
            )
        }
    }

    private fun obtenerVibrador(): Vibrator {

        return if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.S
        ) {

            val vibratorManager =
                context.getSystemService(
                    Context.VIBRATOR_MANAGER_SERVICE
                ) as VibratorManager

            vibratorManager.defaultVibrator

        } else {

            @Suppress("DEPRECATION")
            context.getSystemService(
                Context.VIBRATOR_SERVICE
            ) as Vibrator
        }
    }

    fun vibrarError() {

        val vibrator = obtenerVibrador()

        val patron = longArrayOf(
            0,
            150,
            100,
            300
        )

        if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.O
        ) {

            vibrator.vibrate(
                VibrationEffect.createWaveform(
                    patron,
                    -1
                )
            )

        } else {

            @Suppress("DEPRECATION")
            vibrator.vibrate(
                patron,
                -1
            )
        }
    }

    fun vibrarExito() {

        val vibrator = obtenerVibrador()

        if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.O
        ) {

            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    150,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )

        } else {

            @Suppress("DEPRECATION")
            vibrator.vibrate(150)
        }
    }

    fun error(mensaje: String) {

        vibrarError()
        hablar(mensaje)
    }

    fun exito(mensaje: String) {

        vibrarExito()
        hablar(mensaje)
    }

    fun cerrar() {

        textToSpeech?.stop()
        textToSpeech?.shutdown()
    }
}

@Composable
fun rememberAccessibilityFeedback():
        AccessibilityFeedback {

    val context = LocalContext.current

    val feedback = remember(context) {
        AccessibilityFeedback(context)
    }

    DisposableEffect(feedback) {

        onDispose {
            feedback.cerrar()
        }
    }

    return feedback
}