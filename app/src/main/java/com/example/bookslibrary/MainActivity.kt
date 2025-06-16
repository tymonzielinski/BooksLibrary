package com.example.bookslibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.example.bookslibrary.data.BookRepository
import com.example.bookslibrary.data.local.AppDatabase
import com.example.bookslibrary.data.remote.GoogleBooksApi
import com.example.bookslibrary.ui.BooksScreen
import com.example.bookslibrary.ui.theme.BooksLibraryTheme
import com.example.bookslibrary.viewmodel.BooksViewModel
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_BooksLibrary)
        super.onCreate(savedInstanceState)

        // Room
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "books-db"
        ).build()

        // Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/books/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()
        val googleBooksApi = retrofit.create(GoogleBooksApi::class.java)

        // Repo
        val repository = BookRepository(db.bookDao(), googleBooksApi)

        // ViewModel
        val viewModel = BooksViewModel(repository)

        setContent {
            BooksLibraryTheme {
                BooksScreen(viewModel)
            }
        }
    }
}