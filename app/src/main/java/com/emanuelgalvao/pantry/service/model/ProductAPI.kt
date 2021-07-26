package com.emanuelgalvao.pantry.service.model

import com.google.gson.annotations.SerializedName

data class ProductAPI(
    @SerializedName("nome")
    var name: String = "",

    @SerializedName("ean")
    var ean: String = "",

    @SerializedName("tipo_embalagem")
    var packagingType: String = "",

    @SerializedName("quantidade_embalagem")
    var quantityPackaging: String = "",
)