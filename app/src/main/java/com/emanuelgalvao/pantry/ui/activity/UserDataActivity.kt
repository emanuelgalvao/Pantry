package com.emanuelgalvao.pantry.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.emanuelgalvao.pantry.R
import com.emanuelgalvao.pantry.util.AlertUtils
import com.emanuelgalvao.pantry.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.activity_user_data.*

class UserDataActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_data)

        supportActionBar?.hide()

        mViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        image_back.setOnClickListener(this)
        text_save.setOnClickListener(this)

        mViewModel.getUserName()

        observers()
    }

    private fun observers() {
        mViewModel.updateData.observe(this, {
            if (it.isSucess()) {
                Toast.makeText(this, "Dados atualizados com sucesso.", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                text_save.isVisible = true
                progress_save.isVisible = false
                AlertUtils.showSnackbar(root, it.getMessage(), getColor(R.color.snack_red))
            }
        })

        mViewModel.userName.observe(this, {
            edit_name.setText(it)
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.text_save -> save()
            R.id.image_back -> finish()
        }
    }

    private fun save() {
        val name = edit_name.text.toString().trim()

        text_save.isVisible = false
        progress_save.isVisible = true
        mViewModel.updateUser(name)
    }

}