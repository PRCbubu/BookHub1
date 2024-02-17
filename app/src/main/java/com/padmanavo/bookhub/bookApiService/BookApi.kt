package com.padmanavo.bookhub.bookApiService

import com.padmanavo.bookhub.model.Book
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface BookApi
{
    @GET("fetch_books/")
    fun fetchBooks(@Header("token") token: String): Call<List<Book>>
}