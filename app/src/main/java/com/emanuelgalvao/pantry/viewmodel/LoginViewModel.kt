package com.emanuelgalvao.pantry.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emanuelgalvao.pantry.service.listener.FirebaseListener
import com.emanuelgalvao.pantry.service.listener.ValidationListener
import com.emanuelgalvao.pantry.service.model.User
import com.emanuelgalvao.pantry.service.repository.UserRepository
import com.emanuelgalvao.pantry.util.StringUtils

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val mRepository: UserRepository = UserRepository(application)

    private val mLogin = MutableLiveData<ValidationListener>()
    val login: LiveData<ValidationListener> = mLogin

    private val mRegister = MutableLiveData<ValidationListener>()
    val register: LiveData<ValidationListener> = mRegister

    fun login(email: String, password: String) {

        if (email != "" && password != "") {
            if(!StringUtils.validateEmail(email)) {
                mLogin.value = ValidationListener("Email inválido!")
            }else if(!StringUtils.validatePassword(password)) {
                mLogin.value = ValidationListener("Senha inválida!")
            } else {
                mRepository.login(email, password, object : FirebaseListener<User> {
                    override fun onSucess(model: User) {
                        mLogin.value = ValidationListener()
                    }
                    override fun onFailure(message: String) {
                        mLogin.value = ValidationListener(message)
                    }
                })
            }
        } else if(email == ""){
            mLogin.value = ValidationListener("Preencha o email.")
        } else {
            mLogin.value = ValidationListener("Preencha a senha.")
        }
    }

    fun register(email: String, password: String) {

        if (email != "" && password != "") {
            if(!StringUtils.validateEmail(email)) {
                mRegister.value = ValidationListener("Email inválido!")
            }else if(!StringUtils.validatePassword(password)) {
                mRegister.value = ValidationListener("A senha deve ter pelo menos 6 caracteres.")
            } else {
                mRepository.register(email, password, object : FirebaseListener<User> {
                    override fun onSucess(model: User) {
                        mRegister.value = ValidationListener()
                    }
                    override fun onFailure(message: String) {
                        mRegister.value = ValidationListener(message)
                    }
                })
            }
        } else if(email == ""){
            mRegister.value = ValidationListener("Preencha o email.")
        } else {
            mRegister.value = ValidationListener("Preencha a senha.")
        }
    }
}