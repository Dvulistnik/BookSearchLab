package org.dvulist.booksearch.network

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

/**
 * Объект, предоставляющий настроенный HTTP-клиент Ktor.
 * Это синглтон, чтобы избежать создания множества экземпляров HttpClient.
 */
object HttpClientProvider {
    val client: HttpClient = HttpClient(CIO) { // Инициализируем HttpClient с движком CIO
        // Устанавливаем плагин для работы с Content Negotiation, в данном случае для JSON
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true // Игнорировать поля в JSON, которых нет в модели данных
                prettyPrint = true      // Красивый вывод JSON (для отладки)
                isLenient = true        // Разрешить нестрогий парсинг JSON
            })
        }
    }
}