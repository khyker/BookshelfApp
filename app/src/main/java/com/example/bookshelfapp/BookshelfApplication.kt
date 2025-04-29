package com.example.bookshelfapp

import android.app.Application
import com.example.bookshelfapp.data.AppContainer
import com.example.bookshelfapp.data.BookshelfContainer

class BookshelfApplication : Application() {
    lateinit var container: BookshelfContainer
    override fun onCreate() {
        super.onCreate()
        container = AppContainer()
    }
}