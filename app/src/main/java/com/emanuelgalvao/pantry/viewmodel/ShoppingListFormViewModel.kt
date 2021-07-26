package com.emanuelgalvao.pantry.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.emanuelgalvao.pantry.service.listener.ValidationListener
import com.emanuelgalvao.pantry.service.model.ShoppingItem
import com.emanuelgalvao.pantry.service.repository.ShoppingItemRepository

class ShoppingListFormViewModel(application: Application) : AndroidViewModel(application) {

    private val mShoppingItemRepository = ShoppingItemRepository(application)

    private val mValidationSave = MutableLiveData<ValidationListener>()
    val validationSave: LiveData<ValidationListener> = mValidationSave

    fun save(description: String, quantity: String) {
        if (description != "" && quantity != "") {

            val shoppingItem = ShoppingItem()
            shoppingItem.description = description
            shoppingItem.quantity = quantity

            if (mShoppingItemRepository.save(shoppingItem) != 0L) {
                mValidationSave.value = ValidationListener()
            } else {
                mValidationSave.value = ValidationListener("Ocorreu um erro ao salvar o item. Tente novamente.")
            }
        } else if (description == "") {
            mValidationSave.value = ValidationListener("Preencha o nome do produto.")
        } else {
            mValidationSave.value = ValidationListener("Preencha a quantidade.")
        }
    }
}