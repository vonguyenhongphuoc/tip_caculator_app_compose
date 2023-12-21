package com.devhp.firstcompose.model


import com.google.gson.annotations.SerializedName

data class VolumeInfo(
    @SerializedName("allowAnonLogging")
    val allowAnonLogging: Boolean? = false,
    @SerializedName("authors")
    val authors: List<String?>? = listOf(),
    @SerializedName("averageRating")
    val averageRating: Double? = 0.0,
    @SerializedName("canonicalVolumeLink")
    val canonicalVolumeLink: String? = "",
    @SerializedName("categories")
    val categories: List<String?>? = listOf(),
    @SerializedName("contentVersion")
    val contentVersion: String? = "",
    @SerializedName("description")
    val description: String? = "",
    @SerializedName("imageLinks")
    val imageLinks: ImageLinks? = ImageLinks(),
    @SerializedName("industryIdentifiers")
    val industryIdentifiers: List<IndustryIdentifier?>? = listOf(),
    @SerializedName("infoLink")
    val infoLink: String? = "",
    @SerializedName("language")
    val language: String? = "",
    @SerializedName("maturityRating")
    val maturityRating: String? = "",
    @SerializedName("pageCount")
    val pageCount: Int? = 0,
    @SerializedName("panelizationSummary")
    val panelizationSummary: PanelizationSummary? = PanelizationSummary(),
    @SerializedName("previewLink")
    val previewLink: String? = "",
    @SerializedName("printType")
    val printType: String? = "",
    @SerializedName("publishedDate")
    val publishedDate: String? = "",
    @SerializedName("publisher")
    val publisher: String? = "",
    @SerializedName("ratingsCount")
    val ratingsCount: Int? = 0,
    @SerializedName("readingModes")
    val readingModes: ReadingModes? = ReadingModes(),
    @SerializedName("subtitle")
    val subtitle: String? = "",
    @SerializedName("title")
    val title: String? = ""
)