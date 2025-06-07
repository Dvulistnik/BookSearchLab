package org.dvulist.booksearch.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import org.dvulist.booksearch.model.GoogleBooksApiResponse

/**
 * Клиент для взаимодействия с Google Books API.
 * Использует предоставленный HttpClient для выполнения запросов.
 */
class GoogleBooksApi(private val client: HttpClient = HttpClientProvider.client) {
    private val BASE_URL = "https://www.googleapis.com/books/v1/volumes"

    /**
     * Выполняет поиск книг по заданному запросу.
     * @param query Ключевое слово для поиска (например, название книги, автор, ISBN).
     * @return Объект GoogleBooksApiResponse, содержащий список найденных книг.
     */
    suspend fun searchBooks(query: String): GoogleBooksApiResponse {
        return client.get(BASE_URL) {
            parameter("q", query)
            parameter("maxResults", 40) // Явно указываем максимум
        }.body()
    }
}