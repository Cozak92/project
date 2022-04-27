package com.ably.assignment.infrastructure.config

import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.customizers.OperationCustomizer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.HandlerMethod


@Configuration
class OpenApiConfig {

    @Bean
    fun openAPI(@Value("\${springdoc.version}") appVersion: String?): OpenAPI {
        val info = Info().title("Ably-Backend API").version(appVersion)
            .description("에이블리 백엔드 과제")
            .termsOfService("http://swagger.io/terms/")
            .contact(Contact().name("신승혁").url("https://velog.io/@roo333").email("cozak354@gmail.com"))
            .license(License().name("Apache License Version 2.0").url("http://www.apache.org/licenses/LICENSE-2.0"))

        return OpenAPI()
            .addSecurityItem(SecurityRequirement().addList("bearerAuth").addList("SID"))
            .components(Components().addSecuritySchemes("bearerAuth",
                SecurityScheme()
                    .name("bearerAuth")
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
                ).addSecuritySchemes("SID",
                SecurityScheme()
                    .name("SID")
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("apiKey")
                    .`in`(SecurityScheme.In.HEADER)
            ))
            .info(info)
    }
}