package com.emanuelgalvao.pantry.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.emanuelgalvao.pantry.R
import com.emanuelgalvao.pantry.databinding.ActivityShoppingListFormBinding
import com.emanuelgalvao.pantry.util.AlertUtils
import com.emanuelgalvao.pantry.viewmodel.ShoppingListFormViewModel

class ShoppingListFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityShoppingListFormBinding
    private lateinit var mViewModel: ShoppingListFormViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingListFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        mViewModel = ViewModelProvider(this).get(ShoppingListFormViewModel::class.java)

        binding.imageBack.setOnClickListener(this)
        binding.textSave.setOnClickListener(this)

        observers()

    }

    private fun observers() {
        mViewModel.validationSave.observe(this, {
            if (!it.isSucess()) {
                AlertUtils.showSnackbar(binding.root, it.getMessage(), getColor(R.color.snack_red))
                binding.textSave.isVisible = true
                binding.progressSave.isVisible = false
            } else {
                Toast.makeText(this, "Item salvo com sucesso.", Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.image_back -> finish()
            R.id.text_save -> save()
        }
    }

    private fun save() {
        val description = binding.editProduct.text.toString().trim()
        val quantity = binding.editQuantity.text.toString()

        binding.textSave.isVisible = false
        binding.progressSave.isVisible = true
        mViewModel.save(description, quantity)
    }
}