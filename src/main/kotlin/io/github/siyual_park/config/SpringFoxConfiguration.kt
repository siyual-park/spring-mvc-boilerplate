package io.github.siyual_park.config

import io.github.siyual_park.model.JsonView
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.util.MimeType
import springfox.documentation.annotations.ApiIgnore
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiKey
import springfox.documentation.service.AuthorizationScope
import springfox.documentation.service.SecurityReference
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import java.time.Instant

@Configuration
class SpringFoxConfiguration {
    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2).apply {
            directModelSubstitute(Instant::class.java, Long::class.java)
            directModelSubstitute(MimeType::class.java, String::class.java)
            genericModelSubstitutes(JsonView::class.java)
        }
            .select()
            .apis(
                RequestHandlerSelectors.withClassAnnotation(Api::class.java)
                    .or(RequestHandlerSelectors.withMethodAnnotation(ApiOperation::class.java))
            )
            .paths(PathSelectors.any())
            .build()
            .ignoredParameterTypes(ApiIgnore::class.java, AuthenticationPrincipal::class.java)
            .securitySchemes(listOf(apiKey()))
            .securityContexts(listOf(securityContext()))
    }

    private fun securityContext(): SecurityContext {
        return SecurityContext.builder().securityReferences(defaultAuth()).build()
    }

    private fun apiKey(): ApiKey {
        return ApiKey("Authorization", "Authorization", "header")
    }

    private fun defaultAuth(): List<SecurityReference> {
        val authorizationScope = AuthorizationScope("global", "access All")
        return listOf(SecurityReference("Authorization", arrayOf(authorizationScope)))
    }
}
