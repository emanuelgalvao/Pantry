package com.emanuelgalvao.pantry.service.listener

interface FirebaseListener<T> {

    fun onSucess(model: T)

    fun onFailure(message: String)
}