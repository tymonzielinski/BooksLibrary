package com.example.bookslibrary.model

data class Book(
    val id: String,
    val title: String,
    val author: String,
    val description: String?,
    val coverUrl: String?,
    val isFavorite: Boolean = false
)