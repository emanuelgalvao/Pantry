package com.emanuelgalvao.pantry.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.emanuelgalvao.pantry.R
import com.emanuelgalvao.pantry.util.AlertUtils
import com.emanuelgalvao.pantry.viewmodel.ShoppingListFormViewModel
import kotlinx.android.synthetic.main.activity_pantry_form.image_back
import kotlinx.android.synthetic.main.activity_shopping_list_form.*
import kotlinx.android.synthetic.main.activity_shopping_list_form.edit_product
import kotlinx.android.synthetic.main.activity_shopping_list_form.progress_save
import kotlinx.android.synthetic.main.activity_shopping_list_form.text_save

class ShoppingListFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mViewModel: ShoppingListFormViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_list_form)

        supportActionBar?.hide()

        mViewModel = ViewModelProvider(this).get(ShoppingListFormViewModel::class.java)

        image_back.setOnClickListener(this)
        text_save.setOnClickListener(this)

        observers()

    }

    private fun observers() {
        mViewModel.validationSave.observe(this, {
            if (!it.isSucess()) {
                AlertUtils.showSnackbar(root, it.getMessage(), getColor(R.color.snack_red))
                text_save.isVisible = true
                progress_save.isVisible = false
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
        val description = edit_product.text.toString().trim()
        val quantity = edit_quantity.text.toString()

        text_save.isVisible = false
        progress_save.isVisible = true
        mViewModel.save(description, quantity)
    }
}