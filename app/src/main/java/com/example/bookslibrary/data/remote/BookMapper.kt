package com.example.bookslibrary.data.remote

import com.example.bookslibrary.model.Book

fun GoogleBookItem.toBook(): Book = Book(
    id = id,
    title = volumeInfo.title ?: "",
    author = volumeInfo.authors?.joinToString(", ") ?: "",
    description = volumeInfo.description,
    coverUrl = volumeInfo.imageLinks?.thumbnail?.replace("http://", "https://"),
    isFavorite = false
)