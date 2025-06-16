package com.example.bookslibrary.data.local

import androidx.room.*

@Dao
interface BookDao {
    @Query("SELECT * FROM books")
    suspend fun getAll(): List<BookEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(book: BookEntity)

    @Update
    suspend fun update(book: BookEntity)

    @Delete
    suspend fun delete(book: BookEntity)
}