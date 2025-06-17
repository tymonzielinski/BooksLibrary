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

    private val _suggested = MutableStateFlow<List<Book>>(emptyList())
    val suggested: StateFlow<List<Book>> = _suggested

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        refreshFavorites()
        fetchSuggestedBooks()
    }

    private fun fetchSuggestedBooks() {
        viewModelScope.launch {
            try {
                _suggested.value = repository.getNewestBooks()
                _error.value = null
            } catch (e: Exception) {
                _suggested.value = emptyList()
                _error.value = "error_loading"
            }
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            if (query.length < 2) {
                _books.value = emptyList()
                _error.value = null
            } else {
                try {
                    _books.value = repository.searchBooks(query)
                    _error.value = null
                } catch (e: Exception) {
                    _books.value = emptyList()
                    _error.value = "error_loading"
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

    fun clearError() {
        _error.value = null
    }
}