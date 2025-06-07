package org.dvulist.booksearch.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Объект, предоставляющий настроенный HTTP-клиент Ktor.
 * Это синглтон, чтобы избежать создания множества экземпляров HttpClient.
 */
object HttpClientProvider {
    val client: HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
    }
}