package com.ably.assignment.infrastructure.filter

import com.ably.assignment.infrastructure.jwt.TokenProvider
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtFilter(private val tokenProvider: TokenProvider): OncePerRequestFilter() {
    private val AUTHORIZATION_HEADER = "Authorization"
    private val logger = LoggerFactory.getLogger(JwtFilter::class.java)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val requestURI = request.requestURI

        resolveToken(request)?.also { token ->
            if(tokenProvider.validateToken(token)){
                tokenProvider.getAuthentication(token).also { authentication ->
                    SecurityContextHolder.getContext().authentication = authentication
                    println("\"Security Context에 '${authentication.name}' 인증 정보를 저장했습니다, uri: $requestURI\"")
                    logger.debug("Security Context에 '${authentication.name}' 인증 정보를 저장했습니다, uri: $requestURI")
                }
            }
        } ?: logger.debug("유효한 JWT 토큰이 없습니다, uri: $requestURI")

        filterChain.doFilter(request, response)
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        return request.getHeader(AUTHORIZATION_HEADER)?.let{
            if(it.startsWith("Bearer ")){
                it.substring(7)
            } else {
                null
            }
        }
    }


}