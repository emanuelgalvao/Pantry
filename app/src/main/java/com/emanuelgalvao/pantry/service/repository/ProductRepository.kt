package com.emanuelgalvao.pantry.service.repository

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.emanuelgalvao.pantry.service.listener.ApiListener
import com.emanuelgalvao.pantry.service.model.ResponseAPI
import com.emanuelgalvao.pantry.service.repository.remote.ProductService
import com.emanuelgalvao.pantry.service.repository.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepository(private val application: Application) : BaseRepository() {

    private val mRemote = RetrofitClient.createService(ProductService::class.java)

    fun findProduct(barCode: String, listener: ApiListener<ResponseAPI>) {

        val ai: ApplicationInfo = application.packageManager.getApplicationInfo(application.packageName, PackageManager.GET_META_DATA)
        val token = ai.metaData["apiToken"]

        val call: Call<ResponseAPI> = mRemote.findProduct(barCode, token.toString(), "json")

        if(!isConnectionAvailable(application)) {
            listener.onFailure("Conexão à internet necessária para uso da applicação.")
            return
        }

        call.enqueue(object : Callback<ResponseAPI> {
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                if (response.code() == 200) {
                    response.body()?.let { listener.onSucess(it) }
                }
            }
            override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
                listener.onFailure("Um erro inesperado ocorreu. Tente novamente mais tarde.")
            }
        })
    }
}