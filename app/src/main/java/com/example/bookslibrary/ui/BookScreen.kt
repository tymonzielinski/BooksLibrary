package com.example.bookslibrary.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.bookslibrary.model.Book
import com.example.bookslibrary.viewmodel.BooksViewModel
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.ui.res.painterResource
import com.example.bookslibrary.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.ui.res.stringResource
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.ui.draw.shadow
import androidx.compose.material3.TextFieldDefaults
import com.example.bookslibrary.ui.theme.TextSecondary
import kotlinx.coroutines.delay

@Composable
fun BooksScreen(viewModel: BooksViewModel) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        stringResource(R.string.search_tab),
        stringResource(R.string.favorites_tab)
    )
    val books by viewModel.books.collectAsState()
    val favorites by viewModel.favorites.collectAsState()
    val suggested by viewModel.suggested.collectAsState()
    var selectedBook by remember { mutableStateOf<Book?>(null) }
    var query by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        topBar = {
            if (selectedBook == null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp, bottom = 0.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = stringResource(R.string.logo_desc),
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        },
        bottomBar = {
            if (selectedBook == null) {
                Surface(
                    tonalElevation = 8.dp,
                    shadowElevation = 8.dp,
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
                ) {
                    TabRow(
                        selectedTabIndex = selectedTab,
                        modifier = Modifier
                            .navigationBarsPadding()
                            .height(64.dp),
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.primary,
                        indicator = { tabPositions ->
                            TabRowDefaults.SecondaryIndicator(
                                Modifier
                                    .tabIndicatorOffset(tabPositions[selectedTab])
                                    .height(4.dp)
                                    .clip(RoundedCornerShape(2.dp)),
                                color = MaterialTheme.colorScheme.primary
                            )
                        },
                        divider = {}
                    ) {
                        tabs.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTab == index,
                                onClick = { selectedTab = index },
                                selectedContentColor = MaterialTheme.colorScheme.primary,
                                unselectedContentColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                                text = {
                                    Text(
                                        title,
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    ) { padding ->
        if (selectedBook != null) {
            BookDetailsScreen(book = selectedBook!!, onBack = { selectedBook = null })
        } else {
            Crossfade(targetState = selectedTab) { tab ->
                when (tab) {
                    0 -> {
                        // Search tab
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.background)
                                .padding(padding)
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top
                        ) {
                            TextField(
                                value = query,
                                onValueChange = {
                                    query = it
                                    viewModel.search(it.text)
                                },
                                label = { Text(stringResource(R.string.search_hint)) },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = stringResource(R.string.search_tab)
                                    )
                                },
                                shape = RoundedCornerShape(32.dp),
                                colors = TextFieldDefaults.colors(
                                    unfocusedContainerColor = MaterialTheme.colorScheme.surface.copy(
                                        alpha = 0.7f
                                    ),
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)
                                    .shadow(4.dp, RoundedCornerShape(32.dp))
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            if (query.text.isBlank()) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .weight(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Top
                                ) {
                                    Text(
                                        text = stringResource(R.string.new_releases),
                                        style = MaterialTheme.typography.titleMedium,
                                        modifier = Modifier.padding(vertical = 8.dp)
                                    )
                                    LazyColumn {
                                        items(suggested.size) { idx ->
                                            val book = suggested[idx]
                                            val isFavorite = favorites.any { it.id == book.id }
                                            BookItem(
                                                book = book.copy(isFavorite = isFavorite),
                                                onFavorite = {
                                                    if (isFavorite) {
                                                        viewModel.removeFavorite(book)
                                                    } else {
                                                        viewModel.addFavorite(book)
                                                    }
                                                },
                                                onClick = { selectedBook = book }
                                            )
                                        }
                                    }
                                }
                            } else {
                                LazyColumn {
                                    items(books.size) { idx ->
                                        val book = books[idx]
                                        val isFavorite = favorites.any { it.id == book.id }
                                        BookItem(
                                            book = book.copy(isFavorite = isFavorite),
                                            onFavorite = {
                                                if (isFavorite) {
                                                    viewModel.removeFavorite(book)
                                                } else {
                                                    viewModel.addFavorite(book)
                                                }
                                            },
                                            onClick = { selectedBook = book }
                                        )
                                    }
                                }
                            }
                        }
                    }

                    1 -> {
                        // Favorites tab
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.background)
                                .padding(padding)
                                .padding(16.dp)
                        ) {
                            if (favorites.isEmpty()) {
                                Text(
                                    stringResource(R.string.no_favorites),
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            } else {
                                LazyColumn {
                                    items(favorites.size, key = { favorites[it].id }) { idx ->
                                        var visible by remember { mutableStateOf(true) }
                                        val book = favorites[idx]
                                        AnimatedVisibility(
                                            visible = visible,
                                            enter = fadeIn() + expandVertically(),
                                            exit = fadeOut() + shrinkVertically()
                                        ) {
                                            BookItem(
                                                book = book.copy(isFavorite = true),
                                                onFavorite = {
                                                    visible = false
                                                },
                                                onClick = { selectedBook = book }
                                            )
                                            if (!visible) {
                                                LaunchedEffect(Unit) {
                                                    delay(300)
                                                    viewModel.removeFavorite(book)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BookItem(
    book: Book,
    onFavorite: (Book) -> Unit,
    onClick: (Book) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick(book) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val painter = if (book.coverUrl.isNullOrBlank()) {
                painterResource(id = R.drawable.no_cover)
            } else {
                rememberAsyncImagePainter(book.coverUrl)
            }
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    book.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2
                )
                Text(
                    book.author,
                    style = MaterialTheme.typography.bodyMedium.copy(color = TextSecondary),
                    maxLines = 1
                )
            }
            IconButton(onClick = { onFavorite(book) }) {
                Icon(
                    imageVector = if (book.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(R.string.favourite),
                    tint = if (book.isFavorite) MaterialTheme.colorScheme.primary else TextSecondary
                )
            }
        }
    }
}