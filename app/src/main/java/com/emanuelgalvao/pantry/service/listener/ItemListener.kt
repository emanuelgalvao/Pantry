package com.emanuelgalvao.pantry.service.listener

interface ItemListener<T> {

    fun onDelete(item: T)

    fun onCheck(item: T)

    fun onUnCheck(item: T)
}