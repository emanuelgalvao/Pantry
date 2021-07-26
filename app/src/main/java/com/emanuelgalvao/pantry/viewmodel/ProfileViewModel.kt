package com.emanuelgalvao.pantry.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.emanuelgalvao.pantry.service.listener.ApiListener
import com.emanuelgalvao.pantry.service.listener.ValidationListener
import com.emanuelgalvao.pantry.service.model.User
import com.emanuelgalvao.pantry.service.repository.UserRepository

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val mUserRepository: UserRepository = UserRepository(application)

    private val mLogout = MutableLiveData<ValidationListener>()
    val logout: LiveData<ValidationListener> = mLogout

    private val mUpdateData = MutableLiveData<ValidationListener>()
    val updateData: LiveData<ValidationListener> = mUpdateData

    private val mUserName = MutableLiveData<String>()
    val userName: LiveData<String> = mUserName

    fun logout() {
        mUserRepository.logout()
        mLogout.value = ValidationListener()
    }

    fun getUserName() {
        mUserName.value = mUserRepository.getUserName()
    }

    fun updateUser(name: String) {

        if (name != "") {
            mUserRepository.updateName(name, object : ApiListener<User> {
                override fun onSucess(model: User) {
                    mUpdateData.value = ValidationListener()
                }

                override fun onFailure(message: String) {
                    mUpdateData.value = ValidationListener(message)
                }

            })
        } else {
            mUpdateData.value = ValidationListener("Preencha o nome.")
        }
    }
}