package com.emanuelgalvao.pantry.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.emanuelgalvao.pantry.R
import com.emanuelgalvao.pantry.databinding.ActivityRegisterBinding
import com.emanuelgalvao.pantry.util.AlertUtils
import com.emanuelgalvao.pantry.viewmodel.LoginViewModel

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var mViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        mViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding.textRegister.setOnClickListener(this)

        observers()
    }

    override fun onClick(v: View?) {

        when(v?.id) {
            R.id.text_register -> register()
        }
    }

    private fun register() {
        val name = binding.editName.text.toString().trim()
        val email = binding.editEmail.text.toString().trim()
        val password = binding.editPassword.text.toString().trim()

        binding.textRegister.isVisible = false
        binding.progressRegister.isVisible = true
        mViewModel.register(name, email, password)
    }

    private fun observers() {
        mViewModel.register.observe(this, {
            if (it.isSucess()) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                binding.textRegister.isVisible = true
                binding.progressRegister.isVisible = false
                AlertUtils.showSnackbar(binding.root, it.getMessage(), getColor(R.color.snack_red))
            }
        })
    }
}