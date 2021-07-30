package com.emanuelgalvao.pantry.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.emanuelgalvao.pantry.R
import com.emanuelgalvao.pantry.databinding.ActivityConfigurationBinding
import com.emanuelgalvao.pantry.viewmodel.ConfigurationViewModel

class ConfigurationActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityConfigurationBinding
    private lateinit var mViewModel: ConfigurationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigurationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        mViewModel = ViewModelProvider(this).get(ConfigurationViewModel::class.java)

        binding.imageBack.setOnClickListener(this)
        binding.switchFlash.setOnClickListener(this)
        binding.switchShoppingItemDelete.setOnClickListener(this)
        binding.switchDarkMode.setOnClickListener(this)
        binding.spinnerDaysDue.onItemSelectedListener = this

        mViewModel.getConfiguration()

        observers()
    }

    private fun observers() {

        mViewModel.configuration.observe(this, {
            binding.switchFlash.isChecked = it.enableFlash
            binding.spinnerDaysDue.setSelection(it.dueDays - 1)
            binding.switchShoppingItemDelete.isChecked = it.deleteShoppingItem
            binding.switchDarkMode.isChecked = it.darkMode
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.image_back -> finish()
            R.id.switch_flash -> mViewModel.setEnableFlash(binding.switchFlash.isChecked)
            R.id.switch_shopping_item_delete -> mViewModel.setDeleteShoppingItem(binding.switchShoppingItemDelete.isChecked)
            R.id.switch_dark_mode -> mViewModel.setDarkMode(binding.switchDarkMode.isChecked)
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        mViewModel.setDueDays(position + 1)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

}