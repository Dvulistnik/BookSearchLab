package org.dvulist.booksearch.model

import kotlinx.serialization.Serializable

@Serializable
data class GoogleBooksApiResponse(
    val kind: String? = null,
    val totalItems: Int? = null,
    val items: List<Volume>? = null
)

@Serializable
data class Volume(
    val kind: String? = null,
    val id: String,
    val etag: String? = null,
    val selfLink: String? = null,
    val volumeInfo: VolumeInfo,
    val saleInfo: SaleInfo? = null,
    val accessInfo: AccessInfo? = null,
    val searchInfo: SearchInfo? = null
)

@Serializable
data class VolumeInfo(
    val title: String? = null,
    val subtitle: String? = null,
    val authors: List<String>? = null,
    val publisher: String? = null,
    val publishedDate: String? = null,
    val description: String? = null,
    val industryIdentifiers: List<IndustryIdentifier>? = null,
    val readingModes: ReadingModes? = null,
    val pageCount: Int? = null,
    val printType: String? = null,
    val categories: List<String>? = null,
    val maturityRating: String? = null,
    val allowAnonLogging: Boolean? = null,
    val contentVersion: String? = null,
    val imageLinks: ImageLinks? = null,
    val language: String? = null,
    val previewLink: String? = null,
    val infoLink: String? = null,
    val canonicalVolumeLink: String? = null
)

@Serializable
data class IndustryIdentifier(
    val type: String? = null,
    val identifier: String? = null
)

@Serializable
data class ReadingModes(
    val text: Boolean? = null,
    val image: Boolean? = null
)

@Serializable
data class ImageLinks(
    val smallThumbnail: String? = null,
    val thumbnail: String? = null
)

@Serializable
data class SaleInfo(
    val country: String? = null,
    val saleability: String? = null,
    val isEbook: Boolean? = null
)

@Serializable
data class AccessInfo(
    val country: String? = null,
    val viewability: String? = null,
    val embeddable: Boolean? = null,
    val publicDomain: Boolean? = null,
    val textToSpeechPermission: String? = null,
    val epub: Epub? = null,
    val pdf: Pdf? = null,
    val webReaderLink: String? = null,
    val accessViewStatus: String? = null,
    val quoteSharingAllowed: Boolean? = null
)

@Serializable
data class Epub(
    val isAvailable: Boolean? = null,
    val acsTokenLink: String? = null
)

@Serializable
data class Pdf(
    val isAvailable: Boolean? = null,
    val acsTokenLink: String? = null
)

@Serializable
data class SearchInfo(
    val textSnippet: String? = null
)