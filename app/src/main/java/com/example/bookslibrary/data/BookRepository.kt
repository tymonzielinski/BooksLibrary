package com.example.bookslibrary.data

import com.example.bookslibrary.data.local.BookDao
import com.example.bookslibrary.data.local.toBook
import com.example.bookslibrary.data.local.toEntity
import com.example.bookslibrary.data.remote.GoogleBooksApi
import com.example.bookslibrary.data.remote.toBook
import com.example.bookslibrary.model.Book

class BookRepository(
    private val bookDao: BookDao,
    private val googleBooksApi: GoogleBooksApi
) {
    suspend fun searchBooks(query: String): List<Book> {
        val searchQuery = "intitle:$query OR inauthor:$query"
        val response = googleBooksApi.searchBooks(searchQuery)
        return response.items?.map { it.toBook() } ?: emptyList()
    }

    suspend fun getFavoriteBooks(): List<Book> =
        bookDao.getAll().filter { it.isFavorite }.map { it.toBook() }

    suspend fun addFavorite(book: Book) {
        bookDao.insert(book.toEntity().copy(isFavorite = true))
    }

    suspend fun removeFavorite(book: Book) {
        bookDao.insert(book.toEntity().copy(isFavorite = false))
    }
}