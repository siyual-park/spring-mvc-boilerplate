package io.github.siyual_park.controller

import io.github.siyual_park.domain.Paginator
import io.github.siyual_park.domain.comment.CommentResponsePayloadMapper
import io.github.siyual_park.model.comment.Comment
import io.github.siyual_park.model.comment.CommentResponsePayload
import io.github.siyual_park.repository.ArticleRepository
import io.github.siyual_park.repository.CommentRepository
import io.github.siyual_park.repository.specification.CommentSpecification
import io.swagger.annotations.Api
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Stream

@Api
@RestController
@RequestMapping("/articles")
class ArticleCommentController(
    commentRepository: CommentRepository,
    private val articleRepository: ArticleRepository,
    commentResponsePayloadMapper: CommentResponsePayloadMapper
) {
    private val paginator = Paginator.of(commentRepository, commentResponsePayloadMapper)

    @GetMapping("/{article-id}/comments")
    fun findAllById(
        @PathVariable("article-id") id: String,
        @RequestParam("page", required = false) offset: Int?,
        @RequestParam("per_page", required = false) limit: Int?,
        @RequestParam("sort", required = false) property: String?,
        @RequestParam("order", required = false) direction: Sort.Direction?
    ): ResponseEntity<Stream<CommentResponsePayload>> {
        val article = articleRepository.findByIdOrFail(id)

        return paginator.query(
            offset = offset,
            limit = limit,
            sort = Sort.by(
                direction ?: Sort.Direction.ASC,
                property ?: Comment::createdAt.name
            ),
            spec = CommentSpecification.withArticle(article)
        )
    }
}
