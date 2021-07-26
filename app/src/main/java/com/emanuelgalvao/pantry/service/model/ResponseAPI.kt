package com.emanuelgalvao.pantry.service.model

import com.google.gson.annotations.SerializedName

data class ResponseAPI(
    var api: Api = Api(),
    @SerializedName("return")
    var product: ProductAPI =  ProductAPI()
)

data class Api(
    var version: String = "",
    var rand: String = "")

