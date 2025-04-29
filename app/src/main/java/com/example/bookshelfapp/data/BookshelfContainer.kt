package com.example.bookshelfapp.data

import com.example.bookshelfapp.network.BookshelfApiService
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType



interface BookshelfContainer{
    val bookshelfRepository: BookshelfRepository
}

class AppContainer: BookshelfContainer {
    private val baseUrl = "https://www.googleapis.com/books/"
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: BookshelfApiService by lazy{
        retrofit.create(BookshelfApiService::class.java)
    }

    override val bookshelfRepository: BookshelfRepository by lazy{
        NetBooksRepository(retrofitService)
    }

    
}