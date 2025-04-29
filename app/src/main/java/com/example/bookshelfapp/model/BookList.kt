package com.example.bookshelfapp.model

import kotlinx.serialization.Serializable

@Serializable
data class BookList(
    val items: List<Volume> = emptyList()
)

@Serializable
data class Volume(val book: Book)

@Serializable
data class Book(
    val title: String,
    val authors: List<String> = emptyList(),
    val description: String,
    val imageLinks: ImageLinks? = null
)

@Serializable
data class ImageLinks(
    val thumbnail: String = ""
)