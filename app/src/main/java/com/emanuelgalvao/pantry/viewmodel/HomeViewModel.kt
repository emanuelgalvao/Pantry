package com.emanuelgalvao.pantry.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.emanuelgalvao.pantry.service.model.PantryItem
import com.emanuelgalvao.pantry.service.model.ShoppingItem
import com.emanuelgalvao.pantry.service.repository.PantryItemRepository
import com.emanuelgalvao.pantry.service.repository.ShoppingItemRepository
import com.emanuelgalvao.pantry.service.repository.UserRepository

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val mUserRepository = UserRepository(application)
    private val mPantryItemRepository = PantryItemRepository(application)
    private val mShoppingItemRepository = ShoppingItemRepository(application)

    private var mUserName = MutableLiveData<String>()
    var userName: LiveData<String> = mUserName

    private val mPantryItemList = MutableLiveData<List<PantryItem>>()
    val pantryItemList: LiveData<List<PantryItem>> = mPantryItemList

    private val mShoppingItemList = MutableLiveData<List<ShoppingItem>>()
    val shoppingItemList: LiveData<List<ShoppingItem>> = mShoppingItemList

    fun getUserName(){
        mUserName.value = mUserRepository.getUserName()
    }

    fun listPantryItems() {
        mPantryItemList.value = mPantryItemRepository.getItemsHome()
    }

    fun listShoppingItems() {
        mShoppingItemList.value = mShoppingItemRepository.getItemsHome()
    }
}