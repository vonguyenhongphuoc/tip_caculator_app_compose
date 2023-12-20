package com.devhp.firstcompose.model


import com.google.gson.annotations.SerializedName

data class ListPrice(
    @SerializedName("amount")
    val amount: Int? = 0,
    @SerializedName("currencyCode")
    val currencyCode: String? = ""
)