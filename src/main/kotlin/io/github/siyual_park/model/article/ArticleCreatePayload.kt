package io.github.siyual_park.model.article

import org.springframework.util.MimeType

data class ArticleCreatePayload(
    var title: String,
    var content: String,
    var contentType: MimeType,
    var categoryId: String
)
