package com.emanuelgalvao.pantry.service.listener

interface ApiListener<T> {

    fun onSucess(model: T)

    fun onFailure(message: String)
}