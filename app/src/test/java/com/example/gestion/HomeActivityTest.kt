package com.example.gestion

import android.view.View
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(
    RobolectricTestRunner::class
)
@Config(sdk = [35])
class HomeActivityTest {

    @Test
    fun homeActivity_cargaContenedor() {

        val activity =
            Robolectric
                .buildActivity(
                    HomeActivity::class.java
                )
                .setup()
                .get()

        val contenedor =
            activity.findViewById<View>(
                R.id.fragmentContainer
            )

        assertNotNull(
            contenedor
        )
    }
}