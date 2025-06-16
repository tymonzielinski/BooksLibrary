package com.example.bookslibrary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookslibrary.data.BookRepository
import com.example.bookslibrary.model.Book
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class BooksViewModel(private val repository: BookRepository) : ViewModel() {
    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books

    private val _favorites = MutableStateFlow<List<Book>>(emptyList())
    val favorites: StateFlow<List<Book>> = _favorites

    init {
        refreshFavorites()
    }

    fun search(query: String) {
        viewModelScope.launch {
            if (query.length < 2) {
                _books.value = emptyList()
            } else {
                try {
                    _books.value = repository.searchBooks(query)
                } catch (e: Exception) {
                    _books.value = emptyList()
                }
            }
        }
    }

    fun addFavorite(book: Book) {
        viewModelScope.launch {
            repository.addFavorite(book)
            refreshFavorites()
        }
    }

    fun removeFavorite(book: Book) {
        viewModelScope.launch {
            repository.removeFavorite(book)
            refreshFavorites()
        }
    }

    private fun refreshFavorites() {
        viewModelScope.launch {
            _favorites.value = repository.getFavoriteBooks()
        }
    }
}