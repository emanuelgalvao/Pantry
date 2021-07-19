package com.emanuelgalvao.pantry.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.emanuelgalvao.pantry.R
import com.emanuelgalvao.pantry.util.AlertUtils
import com.emanuelgalvao.pantry.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.text_register
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        supportActionBar?.hide()

        mViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        text_register.setOnClickListener(this)

        observers()
    }

    override fun onClick(v: View?) {

        when(v?.id) {
            R.id.text_register -> register()
        }
    }

    private fun register() {
        val email = edit_email.text.toString().trim()
        val password = edit_password.text.toString().trim()

        text_register.isVisible = false
        progress_register.isVisible = true
        mViewModel.register(email, password)
    }

    private fun observers() {
        mViewModel.register.observe(this, {
            if (it.isSucess()) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                text_register.isVisible = true
                progress_register.isVisible = false
                AlertUtils.showSnackbar(root, it.getMessage())
            }
        })
    }
}