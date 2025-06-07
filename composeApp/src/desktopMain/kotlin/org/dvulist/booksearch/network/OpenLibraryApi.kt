package org.dvulist.booksearch.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.* // Импортируем для использования параметров
import org.dvulist.booksearch.model.OpenLibraryBookApiResponse

class OpenLibraryApi(private val client: HttpClient = HttpClientProvider.client) {
    private val BASE_URL = "https://openlibrary.org/search.json"

    /**
     * Выполняет поиск деталей книги по названию и опционально по автору.
     * @param title Название книги для поиска.
     * @param author (Необязательно) Имя автора для уточнения поиска.
     * @return Объект OpenLibraryBookApiResponse, содержащий найденные детали книг.
     */
    suspend fun searchBookDetails(title: String, author: String? = null): OpenLibraryBookApiResponse {
        return client.get(BASE_URL) {
            // Вместо ручного создания строки запроса, используем блок parameters
            // Ktor сам позаботится об URL-кодировании значений
            parameter("title", title) // Просто передаем параметр "title" и его значение
            if (author != null) {
                parameter("author", author) // Просто передаем параметр "author" и его значение
            }
        }.body()
    }
}