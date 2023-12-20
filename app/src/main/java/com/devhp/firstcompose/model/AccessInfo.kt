package com.devhp.firstcompose.model


import com.google.gson.annotations.SerializedName

data class AccessInfo(
    @SerializedName("accessViewStatus")
    val accessViewStatus: String? = "",
    @SerializedName("country")
    val country: String? = "",
    @SerializedName("embeddable")
    val embeddable: Boolean? = false,
    @SerializedName("epub")
    val epub: Epub? = Epub(),
    @SerializedName("pdf")
    val pdf: Pdf? = Pdf(),
    @SerializedName("publicDomain")
    val publicDomain: Boolean? = false,
    @SerializedName("quoteSharingAllowed")
    val quoteSharingAllowed: Boolean? = false,
    @SerializedName("textToSpeechPermission")
    val textToSpeechPermission: String? = "",
    @SerializedName("viewability")
    val viewability: String? = "",
    @SerializedName("webReaderLink")
    val webReaderLink: String? = ""
)