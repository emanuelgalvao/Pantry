package com.emanuelgalvao.pantry.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.emanuelgalvao.pantry.service.listener.ApiListener
import com.emanuelgalvao.pantry.service.listener.ValidationListener
import com.emanuelgalvao.pantry.service.model.PantryItem
import com.emanuelgalvao.pantry.service.model.ProductAPI
import com.emanuelgalvao.pantry.service.model.ResponseAPI
import com.emanuelgalvao.pantry.service.repository.PantryItemRepository
import com.emanuelgalvao.pantry.service.repository.ProductRepository

class PantryFormViewModel(application: Application) : AndroidViewModel(application) {

    private val mProductRepository = ProductRepository(application)
    private val mPantryItemRepository = PantryItemRepository(application)

    private val mValidation = MutableLiveData<ValidationListener>()
    val validation: LiveData<ValidationListener> = mValidation

    private val mProduct = MutableLiveData<ProductAPI>()
    val product: LiveData<ProductAPI> = mProduct

    private val mValidationSave = MutableLiveData<ValidationListener>()
    val validationSave: LiveData<ValidationListener> = mValidationSave

    fun findProduct(barCode: String) {
        mProductRepository.findProduct(barCode, object: ApiListener<ResponseAPI> {
            override fun onSucess(model: ResponseAPI) {
                if (model.product.name != "") {
                    mProduct.value = model.product
                    mValidation.value = ValidationListener()
                } else {
                    mValidation.value = ValidationListener("Produto não encontrado!\nPreencha os dados manualmente.")
                }
            }

            override fun onFailure(message: String) {
                mValidation.value = ValidationListener(message)
            }
        })
    }

    fun save(description: String, dueDate: String) {
        if (description != "" && dueDate != "Clique para escolher...") {

            val pantryItem = PantryItem()
            pantryItem.description = description
            pantryItem.dueDate = dueDate

            val insert: Long = mPantryItemRepository.save(pantryItem)

            if (insert != 0L) {
                mValidationSave.value = ValidationListener()
            } else {
                mValidationSave.value = ValidationListener("Ocorreu um erro ao salvar o item. Tente novamente.")
            }
        } else if (description == "") {
            mValidationSave.value = ValidationListener("Preencha o nome do produto.")
        } else {
            mValidationSave.value = ValidationListener("Preencha a data de validade.")
        }
    }
}