package com.emanuelgalvao.pantry.service.repository.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    companion object{

        private lateinit var retrofit: Retrofit
        private const val baseUrl = "http://brasilapi.simplescontrole.com.br/"

        private fun getRetrofitInstance(): Retrofit {

            if(!Companion::retrofit.isInitialized) {
                val httpClient = OkHttpClient()

                retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

            return retrofit
        }

        fun <T> createService(serviceClass: Class<T>): T {
            return getRetrofitInstance().create(serviceClass)
        }
    }
}