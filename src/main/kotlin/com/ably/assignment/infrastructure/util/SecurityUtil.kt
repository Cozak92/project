package com.ably.assignment.infrastructure.util

import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

@Component
class SecurityUtil {
    private val logger = LoggerFactory.getLogger(SecurityUtil::class.java)
    val currentUserEmail: String?
        get() {
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication == null) {
                logger.debug("Security Context에 인증 정보가 없습니다.")
                return null
            }
            var email: String? = null

            if (authentication.principal is UserDetails) {
                val springSecurityUser = authentication.principal as UserDetails

                email = springSecurityUser.username
            } else if (authentication.principal is String) {
                email = authentication.principal as String
            }
            return email
        }
}