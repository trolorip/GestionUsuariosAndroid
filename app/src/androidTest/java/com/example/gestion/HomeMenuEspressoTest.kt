package com.example.gestion

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(
    AndroidJUnit4::class
)
class HomeMenuEspressoTest {

    @get:Rule
    val activityRule =
        ActivityScenarioRule(
            HomeActivity::class.java
        )

    @Test
    fun homeMenu_muestraBotones() {

        onView(
            withId(
                R.id.btnEscribir
            )
        ).check(
            matches(
                isDisplayed()
            )
        )

        onView(
            withId(
                R.id.btnHablar
            )
        ).check(
            matches(
                isDisplayed()
            )
        )

        onView(
            withId(
                R.id.btnUsuarios
            )
        ).check(
            matches(
                isDisplayed()
            )
        )
    }
}