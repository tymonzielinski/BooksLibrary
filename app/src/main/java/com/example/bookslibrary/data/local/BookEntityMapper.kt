package com.example.bookslibrary.data.local

import com.example.bookslibrary.model.Book

fun BookEntity.toBook() = Book(
    id = id,
    title = title,
    author = author,
    description = description,
    coverUrl = coverUrl,
    isFavorite = isFavorite
)

fun Book.toEntity() = BookEntity(
    id = id,
    title = title,
    author = author,
    description = description,
    coverUrl = coverUrl,
    isFavorite = isFavorite
)