package com.padmanavo.bookhub.model

import com.google.gson.annotations.SerializedName

data class Book(
    @SerializedName("book_id")
    val bookId: String,
    val bookName: String,
    val bookAuthor: String,
    val bookRating: String,
    val bookPrice: String,
    val bookImage: String
)
