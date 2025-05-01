package com.example.bookshelfapp.ui.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.bookshelfapp.data.BookshelfRepository
import kotlinx.coroutines.launch
import okio.IOException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelfapp.BookshelfApplication
import com.example.bookshelfapp.model.Book


sealed interface BookshelfUiState {
    data class Success(val books: List<Book>) : BookshelfUiState
    object Error: BookshelfUiState

    object Loading: BookshelfUiState
}

class BookViewModel(private val bookshelfRepository: BookshelfRepository) : ViewModel() {
    var bookshelfUiState: BookshelfUiState by mutableStateOf(BookshelfUiState.Loading)
        private set

    init {
        getBooks()
    }


    fun getBooks() {
        viewModelScope.launch {
            bookshelfUiState = BookshelfUiState.Loading
            bookshelfUiState = try {
                val bookList = bookshelfRepository.getBooks()
                val books = bookList.items.map { it.book }
                BookshelfUiState.Success(books)
            } catch (e: IOException) {
                BookshelfUiState.Error
            } catch (e: HttpException) {
                BookshelfUiState.Error
            }
        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as BookshelfApplication)
                val bookshelfRepository = application.container.bookshelfRepository
                BookViewModel(bookshelfRepository = bookshelfRepository)
            }
        }
    }
}