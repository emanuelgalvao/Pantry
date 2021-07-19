package com.emanuelgalvao.pantry.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.emanuelgalvao.pantry.R
import com.emanuelgalvao.pantry.util.AlertUtils
import com.emanuelgalvao.pantry.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        mViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        text_enter_button.setOnClickListener(this)
        text_register.setOnClickListener(this)

        observers()
    }

    override fun onClick(v: View?) {

        when(v?.id) {
            R.id.text_enter_button -> login()
            R.id.text_register -> startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun login() {
        val email = edit_email.text.toString().trim()
        val password = edit_password.text.toString().trim()

        text_enter_button.isVisible = false
        progress_login.isVisible = true
        mViewModel.login(email, password)
    }

    private fun observers() {
        mViewModel.login.observe(this, {
            if (it.isSucess()) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                text_enter_button.isVisible = true
                progress_login.isVisible = false
                AlertUtils.showSnackbar(root, it.getMessage())
            }
        })
    }
}