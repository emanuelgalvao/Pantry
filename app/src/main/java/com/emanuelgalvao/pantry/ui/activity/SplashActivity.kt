package com.emanuelgalvao.pantry.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.emanuelgalvao.pantry.R
import com.emanuelgalvao.pantry.viewmodel.LoginViewModel

class SplashActivity : AppCompatActivity() {

    private lateinit var mViewModel: LoginViewModel

    private var logged = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        mViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        observers()
        mViewModel.verifySignedInUser()
        mViewModel.verifyConfiguration()

        Handler(Looper.getMainLooper()).postDelayed({

            val intent: Intent = if (logged) {
                Intent(this, MainActivity::class.java)
            } else {
                Intent(this, LoginActivity::class.java)
            }
            startActivity(intent)
            finish()
        }, 3000)
    }

    private fun observers() {
        mViewModel.logged.observe(this, {
            if (it.isSucess()) {
                logged = true
            }
        })

        mViewModel.configuration.observe(this, {
            if (it.darkMode)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        })
    }
}