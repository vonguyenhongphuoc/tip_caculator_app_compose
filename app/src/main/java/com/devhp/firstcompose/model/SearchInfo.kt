package com.devhp.firstcompose.model


import com.google.gson.annotations.SerializedName

data class SearchInfo(
    @SerializedName("textSnippet")
    val textSnippet: String? = ""
)