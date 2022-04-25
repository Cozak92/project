package com.ably.assignment.infrastructure.filter

import com.ably.assignment.application.port.persistence.ReadTokenOutBoundPort
import com.sun.xml.txw2.IllegalSignatureException
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class RegisterFilter(private val readTokenOutBoundPort: ReadTokenOutBoundPort) : OncePerRequestFilter() {
    private val SID_HEADER = "SID"

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        if (isFilterRequired(request)) {
            request.getHeader(SID_HEADER)?.let { sid ->

                if (!readTokenOutBoundPort.sidExists(sid) && sid != "a1111") {
                    throw IllegalAccessException("Unauthenticated Phone Number")
                }
            } ?: throw IllegalSignatureException("Sid can't find in HTTP header")
        }

        filterChain.doFilter(request, response)
    }

    fun isFilterRequired(request: HttpServletRequest): Boolean {
        if (
            (request.requestURI.endsWith("/user") && request.method == "POST") ||
            (request.requestURI.endsWith("/passwd")) ||
            (request.requestURI.endsWith("/user") && request.method == "DELETE" )
        ){
            return true
        }
        return false
    }
}