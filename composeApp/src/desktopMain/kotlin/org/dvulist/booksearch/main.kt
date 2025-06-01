package org.dvulist.booksearch

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.dvulist.booksearch.ui.MainScreen
import org.dvulist.booksearch.ui.BookSearchAppTheme // Импортируем нашу тему

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Book Search App"
    ) {
        BookSearchAppTheme {
            MainScreen()
        }
    }
}