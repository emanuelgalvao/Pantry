package com.emanuelgalvao.pantry.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.emanuelgalvao.pantry.service.listener.ApiListener
import com.emanuelgalvao.pantry.service.listener.ValidationListener
import com.emanuelgalvao.pantry.service.model.Configuration
import com.emanuelgalvao.pantry.service.model.User
import com.emanuelgalvao.pantry.service.repository.ConfigurationRepository
import com.emanuelgalvao.pantry.service.repository.UserRepository
import com.emanuelgalvao.pantry.util.StringUtils

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val mUserRepository: UserRepository = UserRepository(application)
    private val mConfigurationRepository: ConfigurationRepository = ConfigurationRepository(application)

    private val mLogin = MutableLiveData<ValidationListener>()
    val login: LiveData<ValidationListener> = mLogin

    private val mRegister = MutableLiveData<ValidationListener>()
    val register: LiveData<ValidationListener> = mRegister

    private val mLogged = MutableLiveData<ValidationListener>()
    val logged: LiveData<ValidationListener> = mLogged

    fun verifySignedInUser() {
        mUserRepository.verifySignedInUser(object : ApiListener<User>{
            override fun onSucess(model: User) {
                mLogged.value = ValidationListener()
            }

            override fun onFailure(message: String) {
                mLogged.value = ValidationListener("Não logado")
            }

        })
    }

    fun login(email: String, password: String) {

        if (email != "" && password != "") {
            if(!StringUtils.validateEmail(email)) {
                mLogin.value = ValidationListener("Email inválido!")
            }else if(!StringUtils.validatePassword(password)) {
                mLogin.value = ValidationListener("Senha inválida!")
            } else {
                mUserRepository.login(email, password, object : ApiListener<User> {
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

    fun register(name: String, email: String, password: String) {

        if (name != "" && email != "" && password != "") {
            if(!StringUtils.validateEmail(email)) {
                mRegister.value = ValidationListener("Email inválido!")
            }else if(!StringUtils.validatePassword(password)) {
                mRegister.value = ValidationListener("A senha deve ter pelo menos 6 caracteres.")
            } else {
                mUserRepository.register(name, email, password, object : ApiListener<User> {
                    override fun onSucess(model: User) {
                        mRegister.value = ValidationListener()
                    }
                    override fun onFailure(message: String) {
                        mRegister.value = ValidationListener(message)
                    }
                })
            }
        } else if (name == ""){
            mRegister.value = ValidationListener("Preencha o nome.")
        } else if(email == ""){
            mRegister.value = ValidationListener("Preencha o email.")
        } else {
            mRegister.value = ValidationListener("Preencha a senha.")
        }
    }

    fun verifyConfiguration() {
        val configuration: Configuration = mConfigurationRepository.find()

        if (configuration == null) {
            mConfigurationRepository.save(Configuration())
        }
    }
}