package com.example.bookshelfapp.network

import com.example.bookshelfapp.model.BookList
import retrofit2.http.GET

interface BookshelfApiService {

    companion object{
        const val BASE_URL = "https://www.googleapis.com/books/v1/"
    }
    @GET("volumes")
    suspend fun getBooks(
        //@Query("q") searchQuery: String,
    ): BookList
}