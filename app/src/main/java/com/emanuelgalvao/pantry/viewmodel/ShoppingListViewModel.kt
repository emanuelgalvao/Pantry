package com.emanuelgalvao.pantry.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.emanuelgalvao.pantry.service.listener.ValidationListener
import com.emanuelgalvao.pantry.service.model.Configuration
import com.emanuelgalvao.pantry.service.model.ShoppingItem
import com.emanuelgalvao.pantry.service.repository.ConfigurationRepository
import com.emanuelgalvao.pantry.service.repository.ShoppingItemRepository

class ShoppingListViewModel(application: Application) : AndroidViewModel(application) {

    private val mShoppingItemRepository = ShoppingItemRepository(application)
    private val mConfigurationRepository = ConfigurationRepository(application)

    private val mShoppingItemList = MutableLiveData<List<ShoppingItem>>()
    val shoppingItemList: LiveData<List<ShoppingItem>> = mShoppingItemList

    private val mValidationDelete = MutableLiveData<ValidationListener>()
    val validationDelete: LiveData<ValidationListener> = mValidationDelete

    private val mConfiguration = MutableLiveData<Configuration>()
    val configuration: LiveData<Configuration> = mConfiguration

    fun list() {
        mShoppingItemList.value = mShoppingItemRepository.getAll()
    }

    fun delete(item: ShoppingItem) {
        if (mShoppingItemRepository.delete(item) > 0) {
            mValidationDelete.value = ValidationListener()
            list()
        } else {
            mValidationDelete.value = ValidationListener("Ocorreu um erro ao excluir o item. Tente novamente.")
        }
    }

    fun update(shoppingItem: ShoppingItem, checked: Boolean) {
        shoppingItem.buyed = checked
        if (mShoppingItemRepository.update(shoppingItem) != 0) {
            list()
        }
    }

    fun getConfiguration() {
        mConfiguration.value = mConfigurationRepository.find()
    }
}