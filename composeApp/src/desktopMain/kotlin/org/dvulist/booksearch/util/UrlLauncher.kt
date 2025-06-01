package org.dvulist.booksearch.util

import java.awt.Desktop // Класс для взаимодействия с десктопными приложениями
import java.net.URI    // Класс для представления URI (Uniform Resource Identifier)

/**
 * Объект-утилита для открытия URL-ссылок в браузере по умолчанию.
 * Работает только в JVM-средах, где поддерживается Desktop API.
 */
object UrlLauncher {
    /**
     * Открывает заданный URL в браузере по умолчанию системы.
     *
     * @param url Строка, представляющая URL для открытия.
     */
    fun openUrl(url: String) {
        // Проверяем, поддерживается ли Desktop API и действие BROWSE (открытие URL)
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(URI(url)) // Открываем URL
            } catch (e: Exception) {
                // В случае ошибки (например, неверный формат URL или нет браузера)
                System.err.println("Ошибка при попытке открыть URL: $url. Причина: ${e.message}")
                // В реальном приложении можно показать уведомление пользователю
            }
        } else {
            // Если Desktop API не поддерживается (например, на сервере или некоторых мобильных платформах)
            System.err.println("Невозможно открыть URL: $url. Desktop API не поддерживается или действие BROWSE недоступно.")
            // Можно реализовать альтернативное поведение, например, скопировать URL в буфер обмена
        }
    }
}