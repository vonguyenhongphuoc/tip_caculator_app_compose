package com.devhp.firstcompose.model


import com.google.gson.annotations.SerializedName

data class Offer(
    @SerializedName("finskyOfferType")
    val finskyOfferType: Int? = 0,
    @SerializedName("listPrice")
    val listPrice: ListPriceX? = ListPriceX(),
    @SerializedName("retailPrice")
    val retailPrice: RetailPrice? = RetailPrice()
)