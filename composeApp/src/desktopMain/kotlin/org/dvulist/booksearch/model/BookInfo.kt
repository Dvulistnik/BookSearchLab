package org.dvulist.booksearch.model

/**
 * Объединенная модель данных для книги, используемая в UI.
 * Содержит информацию, собранную из Google Books API и Open Library API.
 */
data class BookInfo(
    val id: String, // ID книги из Google Books API
    val title: String,
    val authors: String, // Объединенная строка авторов
    val description: String?,
    val thumbnailUrl: String?, // URL обложки книги
    val publishedYear: Int?, // Год издания из Open Library
    val pageCount: Int?, // Количество страниц из Open Library
    val previewLink: String?, // Ссылка на предпросмотр из Google Books
    val openLibraryLink: String? // Ссылка на книгу в Open Library
)