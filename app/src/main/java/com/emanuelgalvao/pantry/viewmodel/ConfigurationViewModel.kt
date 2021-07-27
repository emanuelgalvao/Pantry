package com.emanuelgalvao.pantry.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.emanuelgalvao.pantry.service.model.Configuration
import com.emanuelgalvao.pantry.service.repository.ConfigurationRepository

class ConfigurationViewModel(application: Application) : AndroidViewModel(application) {

    private val mConfigurationRepository: ConfigurationRepository = ConfigurationRepository(application)

    private val mConfiguration = MutableLiveData<Configuration>()
    val configuration: LiveData<Configuration> = mConfiguration

    fun getConfiguration() {
        mConfiguration.value = mConfigurationRepository.find()
    }

    fun setEnableFlash(enable: Boolean) {
        val configuration = mConfiguration.value
        if (configuration != null) {
            configuration.enableFlash = enable
            save(configuration)
        }
    }

    fun setDueDays(dueDays: Int) {
        val configuration = mConfiguration.value
        if (configuration != null) {
            configuration.dueDays = dueDays
            save(configuration)
        }
    }

    fun setDeleteShoppingItem(delete: Boolean) {
        val configuration = mConfiguration.value
        if (configuration != null) {
            configuration.deleteShoppingItem = delete
            save(configuration)
        }
    }

    private fun save(configuration: Configuration) {
        mConfigurationRepository.update(configuration)
        getConfiguration()
    }
}