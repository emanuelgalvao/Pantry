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
import com.emanuelgalvao.pantry.databinding.ActivityPantryFormBinding
import com.emanuelgalvao.pantry.util.AlertUtils
import com.emanuelgalvao.pantry.viewmodel.PantryFormViewModel
import java.text.SimpleDateFormat
import java.util.*

class PantryFormActivity : AppCompatActivity(), View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private lateinit var binding: ActivityPantryFormBinding
    private lateinit var mViewModel: PantryFormViewModel

    private var mFormatDate = SimpleDateFormat("dd/MM/yyyy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPantryFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        mViewModel = ViewModelProvider(this).get(PantryFormViewModel::class.java)

        val bundle = intent.extras
        val barCode = bundle?.getString("barCode")

        if (barCode != null) {
            findProduct(barCode)
        }else {
            binding.progressItem.isVisible = false
            binding.textWait.isVisible = false
        }

        binding.imageBack.setOnClickListener(this)
        binding.textDueDate.setOnClickListener(this)
        binding.textDueDateValue.setOnClickListener(this)
        binding.imageDate.setOnClickListener(this)
        binding.textSave.setOnClickListener(this)

        observers()
    }

    private fun findProduct(barCode: String){
        binding.textfieldProduct.isVisible = false
        binding.textDueDate.isVisible = false
        binding.textDueDateValue.isVisible = false
        binding.imageDate.isVisible = false
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
                AlertUtils.showSnackbar(binding.root, it.getMessage(), getColor(R.color.snack_red))
            }

            binding.progressItem.isVisible = false
            binding.textWait.isVisible = false
            binding.textfieldProduct.isVisible = true
            binding.textDueDate.isVisible = true
            binding.textDueDateValue.isVisible = true
            binding.imageDate.isVisible = true
        })

        mViewModel.product.observe(this, {
            binding.editProduct.setText(it.name)
        })

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
        binding.textDueDateValue.text = dateString
    }

    private fun save() {
        val description = binding.editProduct.text.toString().trim()
        val dueDate = binding.textDueDateValue.text.toString()

        binding.textSave.isVisible = false
        binding.progressSave.isVisible = true
        mViewModel.save(description, dueDate)
    }
}