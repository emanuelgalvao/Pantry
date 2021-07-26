package com.emanuelgalvao.pantry.service.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

open class BaseRepository {

    fun isConnectionAvailable(context: Context): Boolean {
        val result: Boolean
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = cm.activeNetwork ?: return false
        val actNw = cm.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }

        return result
    }
}