package org.dvulist.booksearch.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.dvulist.booksearch.model.BookInfo
import org.dvulist.booksearch.util.UrlLauncher
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

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
            if (book.thumbnailUrl != null && book.thumbnailUrl.isNotBlank()) {
                AsyncImage(
                    model = book.thumbnailUrl,
                    contentDescription = "Обложка книги ${book.title}",
                    modifier = Modifier
                        .size(100.dp, 150.dp)
                        .padding(end = 16.dp),
                    contentScale = ContentScale.Fit
                )
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