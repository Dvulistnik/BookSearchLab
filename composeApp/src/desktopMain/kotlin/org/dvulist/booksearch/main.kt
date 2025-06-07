package org.dvulist.booksearch

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.dvulist.booksearch.ui.BookSearchAppTheme
import org.dvulist.booksearch.ui.MainScreen

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