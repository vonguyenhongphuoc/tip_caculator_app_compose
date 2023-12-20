package com.devhp.firstcompose.model


import com.google.gson.annotations.SerializedName

data class PanelizationSummary(
    @SerializedName("containsEpubBubbles")
    val containsEpubBubbles: Boolean? = false,
    @SerializedName("containsImageBubbles")
    val containsImageBubbles: Boolean? = false
)