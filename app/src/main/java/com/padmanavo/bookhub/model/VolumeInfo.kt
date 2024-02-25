package com.padmanavo.bookhub.model

import com.google.gson.annotations.SerializedName

data class VolumeInfo (
    @SerializedName("title") val title: String,
    @SerializedName("subtitle") val subtitle: String?,
    @SerializedName("authors") val authors: List<String>?, // May be null
    @SerializedName("publisher") val publisher: String?,
    @SerializedName("publishedDate") val publishedDate: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("industryIdentifiers") val industryIdentifiers: List<IndustryIdentifier>?,
    @SerializedName("pageCount") val pageCount: Int?,
    @SerializedName("categories") val categories: List<String>?,
    @SerializedName("maturityRating") val maturityRating: String,
    @SerializedName("averageRating") val averageRating: Double?,
    @SerializedName("ratingsCount") val ratingsCount: Int?,
    @SerializedName("imageLinks") val imageLinks: ImageLinks?,
    @SerializedName("language") val language: String
)