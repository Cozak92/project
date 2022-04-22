package com.ably.assignment.domain.service

import org.springframework.security.core.userdetails.UserDetails

import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import javax.transaction.Transactional


@Component
class CustomUserDetailService : UserDetailsService {
    @Transactional
    override fun loadUserByUsername(userPk: String): UserDetails {
        TODO()
    }
}