package org.dvulist.booksearch.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.dvulist.booksearch.model.BookInfo
import org.dvulist.booksearch.network.GoogleBooksApi
import org.dvulist.booksearch.network.OpenLibraryApi
import org.dvulist.booksearch.network.HttpClientProvider

/**
 * Основной экран приложения для поиска книг.
 */
@Composable
fun MainScreen() {
    // Состояние для поля ввода поиска
    var searchQuery by remember { mutableStateOf("") }
    // Список найденных книг
    val books = remember { mutableStateListOf<BookInfo>() }
    // Состояние для индикатора загрузки
    var isLoading by remember { mutableStateOf(false) }
    // Сообщение об ошибке
    var errorMessage by remember { mutableStateOf<String?>(null) }
    // CoroutineScope для выполнения асинхронных операций
    val coroutineScope = rememberCoroutineScope()

    // Инициализация API-клиентов
    val googleBooksApi = remember { GoogleBooksApi(HttpClientProvider.client) }
    val openLibraryApi = remember { OpenLibraryApi(HttpClientProvider.client) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Введите ключевое слово (название, автор, ISBN)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = {
                    errorMessage = null
                    isLoading = true
                    books.clear()
                    coroutineScope.launch {
                        try {
                            // 1. Поиск в Google Books API
                            val googleBooksResponse = googleBooksApi.searchBooks(searchQuery)
                            val fetchedBooks = mutableListOf<BookInfo>()

                            googleBooksResponse.items?.forEach { googleBook ->
                                val title = googleBook.volumeInfo.title ?: "Название неизвестно"
                                val authors = googleBook.volumeInfo.authors?.joinToString(", ") ?: "Автор неизвестен"
                                val description = googleBook.volumeInfo.description
                                val thumbnailUrl = googleBook.volumeInfo.imageLinks?.thumbnail
                                val previewLink = googleBook.volumeInfo.previewLink

                                var publishedYear: Int? = null
                                var pageCount: Int? = null
                                var openLibraryLink: String? = null

                                // 2. Запрос деталей в Open Library API (по названию и/или автору)
                                try {
                                    val openLibraryResponse = openLibraryApi.searchBookDetails(title, googleBook.volumeInfo.authors?.firstOrNull())
                                    openLibraryResponse.docs?.firstOrNull()?.let { olDoc ->
                                        publishedYear = olDoc.first_publish_year
                                        pageCount = olDoc.number_of_pages_median

                                        if (olDoc.public_scan_b == true && olDoc.ia != null && olDoc.ia.isNotEmpty()) {
                                            openLibraryLink = "https://archive.org/details/${olDoc.ia.first()}"
                                        }
                                    }
                                } catch (e: Exception) {
                                    System.err.println("Ошибка при запросе к Open Library для книги '$title': ${e.message}")
                                }

                                fetchedBooks.add(
                                    BookInfo(
                                        id = googleBook.id,
                                        title = title,
                                        authors = authors,
                                        description = description,
                                        thumbnailUrl = thumbnailUrl,
                                        publishedYear = publishedYear,
                                        pageCount = pageCount,
                                        previewLink = previewLink,
                                        openLibraryLink = openLibraryLink
                                    )
                                )
                            }
                            books.addAll(fetchedBooks) // Добавляем найденные книги в список
                            if (books.isEmpty()) {
                                errorMessage = "Книги по вашему запросу не найдены."
                            }
                        } catch (e: Exception) {
                            errorMessage = "Ошибка при загрузке данных: ${e.message}"
                            e.printStackTrace()
                        } finally {
                            isLoading = false
                        }
                    }
                }) {
                    Icon(Icons.Default.Search, contentDescription = "Поиск")
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        } else if (errorMessage != null) {
            Text(text = errorMessage!!, color = MaterialTheme.colors.error)
        } else {
            // Список найденных книг
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(books) { book ->
                    BookCard(book) // Отображаем каждую книгу с помощью BookCard
                }
            }
        }
    }
}