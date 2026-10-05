package com.example.gestion

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import com.example.gestion.ui.fragments.HomeMenuFragment

class HomeActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        if (savedInstanceState == null) {

            supportFragmentManager.commit {

                replace(
                    R.id.fragmentContainer,
                    HomeMenuFragment()
                )
            }
        }
    }
}