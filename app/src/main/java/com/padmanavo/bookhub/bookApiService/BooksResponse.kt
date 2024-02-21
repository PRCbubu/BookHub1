package com.padmanavo.bookhub.bookApiService

import com.google.gson.annotations.SerializedName
import com.padmanavo.bookhub.model.Book

data class BooksResponse(
    @SerializedName("kind") val kind: String,
    @SerializedName("totalItems") val totalItems: Int,
    @SerializedName("items") val items: List<Book>
)
