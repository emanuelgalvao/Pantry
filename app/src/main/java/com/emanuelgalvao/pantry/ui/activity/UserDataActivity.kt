package com.emanuelgalvao.pantry.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.emanuelgalvao.pantry.R
import com.emanuelgalvao.pantry.databinding.ActivityUserDataBinding
import com.emanuelgalvao.pantry.util.AlertUtils
import com.emanuelgalvao.pantry.viewmodel.ProfileViewModel

class UserDataActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityUserDataBinding
    private lateinit var mViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        mViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        binding.imageBack.setOnClickListener(this)
        binding.textSave.setOnClickListener(this)

        mViewModel.getUserName()

        observers()
    }

    private fun observers() {
        mViewModel.updateData.observe(this, {
            if (it.isSucess()) {
                Toast.makeText(this, "Dados atualizados com sucesso.", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                binding.textSave.isVisible = true
                binding.progressSave.isVisible = false
                AlertUtils.showSnackbar(binding.root, it.getMessage(), getColor(R.color.snack_red))
            }
        })

        mViewModel.userName.observe(this, {
            binding.editName.setText(it)
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.text_save -> save()
            R.id.image_back -> finish()
        }
    }

    private fun save() {
        val name = binding.editName.text.toString().trim()

        binding.textSave.isVisible = false
        binding.progressSave.isVisible = true
        mViewModel.updateUser(name)
    }

}