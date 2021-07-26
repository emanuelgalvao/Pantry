package com.emanuelgalvao.pantry.service.repository.remote

import com.emanuelgalvao.pantry.service.model.ResponseAPI
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {

    @GET("/mercadoria/consulta/")
    fun findProduct(@Query("ean") barCode: String, @Query("access-token") token: String, @Query("_format") format: String): Call<ResponseAPI>
}