package com.ably.assignment.application.jwt

import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class JwtFilter(private val tokenProvider: TokenProvider): GenericFilterBean() {
    override fun doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, filterChain: FilterChain) {
        val httpServletRequest = servletRequest as HttpServletRequest
        val requestURI = httpServletRequest.requestURI

        resolveToken(httpServletRequest)?.also {
            if(tokenProvider.validateToken(it)){
                tokenProvider.getAuthentication(it).also { authentication ->
                    SecurityContextHolder.getContext().authentication = authentication
                    Companion.logger.debug("Security Context에 '$authentication.name' 인증 정보를 저장했습니다, uri: $requestURI")
                }
            }
        } ?: Companion.logger.debug("유효한 JWT 토큰이 없습니다, uri: $requestURI")

        filterChain.doFilter(servletRequest, servletResponse)
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(AUTHORIZATION_HEADER)
        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else null
    }

    companion object {
        private val logger = LoggerFactory.getLogger(JwtFilter::class.java)
        const val AUTHORIZATION_HEADER = "Authorization"
    }
}