package com.ably.assignment.application.service

import com.ably.assignment.application.port.persistence.ReadOutBoundPort
import com.ably.assignment.domain.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import javax.transaction.Transactional


@Component
class CustomUserDetailService(private val readOutBoundPort: ReadOutBoundPort) : UserDetailsService {
    @Transactional
    override fun loadUserByUsername(email: String): UserDetails {
        if(!readOutBoundPort.existsByEmail(email)){
            throw IllegalArgumentException("User doesn't exist")
        }
        val user = readOutBoundPort.getUserByEmail(email)
        return createUser(user)

    }
    private fun createUser(user: User): org.springframework.security.core.userdetails.User {
        val grantedAuthorities: List<GrantedAuthority> = user.authorities.map { authority -> SimpleGrantedAuthority(authority.role) }.toList()
        return org.springframework.security.core.userdetails.User(
            user.information.email,
            user.password,
            grantedAuthorities
        )
    }
}