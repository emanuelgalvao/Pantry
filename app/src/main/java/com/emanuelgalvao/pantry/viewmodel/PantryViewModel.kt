package com.emanuelgalvao.pantry.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.emanuelgalvao.pantry.service.listener.ValidationListener
import com.emanuelgalvao.pantry.service.model.PantryItem
import com.emanuelgalvao.pantry.service.repository.PantryItemRepository

class PantryViewModel(application: Application) : AndroidViewModel(application) {

    private val mPantryItemRepository = PantryItemRepository(application)

    private val mPantryItemList = MutableLiveData<List<PantryItem>>()
    val pantryItemList: LiveData<List<PantryItem>> = mPantryItemList

    private val mValidationDelete = MutableLiveData<ValidationListener>()
    val validationDelete: LiveData<ValidationListener> = mValidationDelete

    fun list() {
        mPantryItemList.value = mPantryItemRepository.getAll()
    }

    fun delete(item: PantryItem){
        if(mPantryItemRepository.delete(item) > 0) {
            mValidationDelete.value = ValidationListener()
            list()
        } else {
            mValidationDelete.value = ValidationListener("Ocorreu um erro ao excluir o item. Tente novamente.")
        }
    }
}