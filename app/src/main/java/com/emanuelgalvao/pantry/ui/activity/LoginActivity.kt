package com.emanuelgalvao.pantry.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.emanuelgalvao.pantry.R
import com.emanuelgalvao.pantry.databinding.ActivityLoginBinding
import com.emanuelgalvao.pantry.util.AlertUtils
import com.emanuelgalvao.pantry.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var mViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        supportActionBar?.hide()

        binding.textEnterButton.setOnClickListener(this)
        binding.textRegister.setOnClickListener(this)

        observers()
    }

    override fun onClick(v: View?) {

        when(v?.id) {
            R.id.text_enter_button -> login()
            R.id.text_register -> startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun login() {
        val email = binding.editEmail.text.toString().trim()
        val password = binding.editPassword.text.toString().trim()

        binding.textEnterButton.isVisible = false
        binding.progressLogin.isVisible = true
        mViewModel.login(email, password)
    }

    private fun observers() {
        mViewModel.login.observe(this, {
            if (it.isSucess()) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                binding.textEnterButton.isVisible = true
                binding.progressLogin.isVisible = false
                AlertUtils.showSnackbar(binding.root, it.getMessage(), getColor(R.color.snack_red))
            }
        })
    }
}