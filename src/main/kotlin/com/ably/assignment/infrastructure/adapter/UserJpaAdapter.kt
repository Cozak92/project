package com.ably.assignment.infrastructure.adapter

import com.ably.assignment.domain.entity.User
import com.ably.assignment.domain.port.outbound.UserOutBoundPort
import com.ably.assignment.infrastructure.repository.UserJpaRepository
import org.springframework.stereotype.Component


@Component
class UserJpaAdapter(private val userJpaRepository: UserJpaRepository): UserOutBoundPort {
    override fun save(user: User): User {
        return userJpaRepository.save(user)
    }

    override fun findByEmail(email: String): User? {
        return userJpaRepository.findByUserInformation_Email(email)
    }
}