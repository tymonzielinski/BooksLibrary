package com.example.bookslibrary.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey val id: String,
    val title: String,
    val author: String,
    val description: String?,
    val coverUrl: String?,
    val isFavorite: Boolean = false
)