package com.emanuelgalvao.qualocarro.repository

import com.emanuelgalvao.pantry.service.model.ProductAPI
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {

    @GET("?ean={barCode}}&access-token={token}&_format=json")
    fun findProduct(@Path(value = "barCode", encoded = true) barCode: String, @Path(value = "token", encoded = true) token: String): Call<ProductAPI>
}