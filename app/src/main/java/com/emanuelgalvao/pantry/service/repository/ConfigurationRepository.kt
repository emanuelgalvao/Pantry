package com.emanuelgalvao.pantry.service.repository

import android.app.Application
import com.emanuelgalvao.pantry.service.model.Configuration
import com.emanuelgalvao.pantry.service.repository.local.LocalDatabase

class ConfigurationRepository(application: Application) {

    private val mDatabase = LocalDatabase.getDatabase(application).configurationDAO()

    fun save(configuration: Configuration): Long {
        return mDatabase.save(configuration)
    }

    fun update(configuration: Configuration): Int {
        return mDatabase.update(configuration)
    }

    fun find(): Configuration {
        return mDatabase.find()
    }

}