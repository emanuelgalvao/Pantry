package com.emanuelgalvao.pantry.service.repository

import android.app.Application
import com.emanuelgalvao.pantry.service.model.ShoppingItem
import com.emanuelgalvao.pantry.service.repository.local.LocalDatabase

class ShoppingItemRepository(application: Application) {

    private val mDatabase = LocalDatabase.getDatabase(application).shoppingItemDAO()

    fun save(shoppingItem: ShoppingItem): Long {
        return mDatabase.save(shoppingItem)
    }

    fun update(shoppingItem: ShoppingItem): Int {
        return mDatabase.update(shoppingItem)
    }

    fun delete(pshoppingItem: ShoppingItem): Int {
        return mDatabase.delete(pshoppingItem)
    }

    fun getAll() : List<ShoppingItem> {
        return mDatabase.findAll()
    }

    fun getItemsHome(): List<ShoppingItem> {
        return mDatabase.findItemsHome()
    }
}