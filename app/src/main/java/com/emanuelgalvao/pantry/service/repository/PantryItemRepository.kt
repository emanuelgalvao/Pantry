package com.emanuelgalvao.pantry.service.repository

import android.app.Application
import com.emanuelgalvao.pantry.service.model.PantryItem
import com.emanuelgalvao.pantry.service.repository.local.LocalDatabase

class PantryItemRepository(application: Application) {

    private val mDatabase = LocalDatabase.getDatabase(application).pantryItemDAO()

    fun save(pantryItem: PantryItem): Long {
        return mDatabase.save(pantryItem)
    }

    fun delete(pantryItem: PantryItem): Int {
        return mDatabase.delete(pantryItem)
    }

    fun getAll() : List<PantryItem> {
        return mDatabase.findAll()
    }

    fun getItemsHome(): List<PantryItem> {
        return mDatabase.findItemsHome()
    }
}