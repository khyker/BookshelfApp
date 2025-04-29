package com.example.bookshelfapp.data

import com.example.bookshelfapp.model.BookList
import com.example.bookshelfapp.network.BookshelfApiService

interface BookshelfRepository {
    suspend fun getBooks(): BookList
}

class NetBooksRepository(
    private val bookshelfApiService: BookshelfApiService
): BookshelfRepository {
    override suspend fun getBooks(): BookList = bookshelfApiService.getBooks()
}