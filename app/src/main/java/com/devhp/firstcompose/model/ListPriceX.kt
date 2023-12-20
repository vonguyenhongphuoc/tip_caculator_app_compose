package com.devhp.firstcompose.model


import com.google.gson.annotations.SerializedName

data class ListPriceX(
    @SerializedName("amountInMicros")
    val amountInMicros: Long? = 0,
    @SerializedName("currencyCode")
    val currencyCode: String? = ""
)