package com.devhp.firstcompose.model


import com.google.gson.annotations.SerializedName

data class Book(
    @SerializedName("items")
    val items: List<Item>? = listOf(),
    @SerializedName("kind")
    val kind: String? = "",
    @SerializedName("totalItems")
    val totalItems: Int? = 0
)