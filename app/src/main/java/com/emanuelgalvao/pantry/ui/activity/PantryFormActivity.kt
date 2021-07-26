package com.emanuelgalvao.pantry.ui.activity

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.emanuelgalvao.pantry.R
import com.emanuelgalvao.pantry.util.AlertUtils
import com.emanuelgalvao.pantry.viewmodel.PantryFormViewModel
import kotlinx.android.synthetic.main.activity_pantry_form.*
import java.text.SimpleDateFormat
import java.util.*

class PantryFormActivity : AppCompatActivity(), View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private lateinit var mViewModel: PantryFormViewModel

    private var mFormatDate = SimpleDateFormat("dd/MM/yyyy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantry_form)

        supportActionBar?.hide()

        mViewModel = ViewModelProvider(this).get(PantryFormViewModel::class.java)

        val bundle = intent.extras
        val barCode = bundle?.getString("barCode")

        if (barCode != null) {
            findProduct(barCode)
        }else {
            progress_item.isVisible = false
            text_wait.isVisible = false
        }

        image_back.setOnClickListener(this)
        text_due_date.setOnClickListener(this)
        text_due_date_value.setOnClickListener(this)
        image_date.setOnClickListener(this)
        text_save.setOnClickListener(this)

        observers()
    }

    private fun findProduct(barCode: String){
        textfield_product.isVisible = false
        text_due_date.isVisible = false
        text_due_date_value.isVisible = false
        image_date.isVisible = false
        mViewModel.findProduct(barCode)
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.image_back -> finish()
            R.id.text_due_date, R.id.text_due_date_value, R.id.image_date -> showDatePickerDialog()
            R.id.text_save -> save()
        }
    }

    private fun observers(){
        mViewModel.validation.observe(this, {
            if (!it.isSucess()) {
                AlertUtils.showSnackbar(root, it.getMessage(), getColor(R.color.snack_red))
            }

            progress_item.isVisible = false
            text_wait.isVisible = false
            textfield_product.isVisible = true
            text_due_date.isVisible = true
            text_due_date_value.isVisible = true
            image_date.isVisible = true
        })

        mViewModel.product.observe(this, {
            edit_product.setText(it.name)
        })

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

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(this, this, year, month, day).show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateString = mFormatDate.format(calendar.time)
        text_due_date_value.text = dateString
    }

    private fun save() {
        val description = edit_product.text.toString().trim()
        val dueDate = text_due_date_value.text.toString()

        text_save.isVisible = false
        progress_save.isVisible = true
        mViewModel.save(description, dueDate)
    }
}