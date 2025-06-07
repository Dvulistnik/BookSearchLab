package org.dvulist.booksearch.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.dvulist.booksearch.model.BookInfo
import org.dvulist.booksearch.network.HttpClientProvider.client
import org.dvulist.booksearch.util.UrlLauncher
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image


/**
 * Composable-функция для отображения одной карточки книги.
 *
 * @param book Объект BookInfo, содержащий данные о книге.
 * @param modifier Модификатор для настройки внешнего вида карточки.
 */
@Composable
fun BookCard(
    book: BookInfo,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { },
        elevation = 4.dp
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            val thumbnailUrl = book.thumbnailUrl?.replace("http://", "https://")

            if (!thumbnailUrl.isNullOrBlank()) {
                val imageBitmapState = remember { mutableStateOf<ImageBitmap?>(null) }
                val loadErrorState = remember { mutableStateOf(false) }

                LaunchedEffect(thumbnailUrl) {
                    try {
                        val bytes: ByteArray = client.get(thumbnailUrl).body()
                        val skiaImage = Image.makeFromEncoded(bytes)
                        imageBitmapState.value = skiaImage.toComposeImageBitmap()
                    } catch (e: Exception) {
                        println("Ошибка загрузки изображения: ${e.message}")
                        loadErrorState.value = true
                    }
                }

                when {
                    imageBitmapState.value != null -> {
                        Image(
                            bitmap = imageBitmapState.value!!,
                            contentDescription = "Обложка книги ${book.title}",
                            modifier = Modifier
                                .size(100.dp, 150.dp)
                                .padding(end = 16.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                    loadErrorState.value -> {
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = "Ошибка загрузки",
                            modifier = Modifier
                                .size(100.dp, 150.dp)
                                .padding(end = 16.dp)
                        )
                    }
                    else -> {
                        Spacer(
                            modifier = Modifier
                                .size(100.dp, 150.dp)
                                .padding(end = 16.dp)
                        )
                    }
                }
            } else {
                Spacer(modifier = Modifier.size(100.dp, 150.dp).padding(end = 16.dp))
            }


            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = book.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(text = "Автор: ${book.authors}", fontSize = 14.sp)
                Spacer(modifier = Modifier.height(4.dp))

                val bookDetails = mutableListOf<String>()
                book.publishedYear?.let { bookDetails.add("Год: $it") }
                book.pageCount?.let { bookDetails.add("Стр: $it") }
                if (bookDetails.isNotEmpty()) {
                    Text(text = bookDetails.joinToString(" | "), fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                }


                book.description?.let {
                    Text(
                        text = it,
                        fontSize = 12.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    book.previewLink?.let { link ->
                        Button(
                            onClick = { UrlLauncher.openUrl(link) },
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text("Просмотр (Google)")
                        }
                    }
                    book.openLibraryLink?.let { link ->
                        Button(
                            onClick = { UrlLauncher.openUrl(link) }
                        ) {
                            Text("Читать (Open Library)")
                        }
                    }
                }
            }
        }
    }
}