package com.emanuelgalvao.pantry.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.emanuelgalvao.pantry.R
import com.emanuelgalvao.pantry.viewmodel.ConfigurationViewModel
import kotlinx.android.synthetic.main.activity_configuration.*
import kotlinx.android.synthetic.main.activity_user_data.image_back

class ConfigurationActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener {

    private lateinit var mViewModel: ConfigurationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuration)

        supportActionBar?.hide()

        mViewModel = ViewModelProvider(this).get(ConfigurationViewModel::class.java)

        image_back.setOnClickListener(this)
        switch_flash.setOnClickListener(this)
        switch_shopping_item_delete.setOnClickListener(this)
        spinner_days_due.onItemSelectedListener = this

        mViewModel.getConfiguration()

        observers()
    }

    private fun observers() {

        mViewModel.configuration.observe(this, {
            switch_flash.isChecked = it.enableFlash
            spinner_days_due.setSelection(it.dueDays - 1)
            switch_shopping_item_delete.isChecked = it.deleteShoppingItem
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.image_back -> finish()
            R.id.switch_flash -> mViewModel.setEnableFlash(switch_flash.isChecked)
            R.id.switch_shopping_item_delete -> mViewModel.setDeleteShoppingItem(switch_shopping_item_delete.isChecked)
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        mViewModel.setDueDays(position + 1)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

}