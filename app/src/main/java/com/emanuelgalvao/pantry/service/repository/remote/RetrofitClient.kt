package com.emanuelgalvao.qualocarro.repository

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import javax.security.cert.CertificateException

class RetrofitClient {

    companion object{

        private lateinit var retrofit: Retrofit
        private val baseUrl = "http://brasilapi.simplescontrole.com.br/mercadoria/consulta/"

        private fun getRetrofitInstance(): Retrofit {

            if(!::retrofit.isInitialized) {
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