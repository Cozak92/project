package com.ably.assignment.infrastructure.repository

import com.ably.assignment.domain.entity.User
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserJpaRepository: JpaRepository<User, Long> {
    fun findByUserInformation_Email(email: String): User?
    @EntityGraph(attributePaths = ["authorities"])
    fun findOneWithAuthoritiesByUserInformation_name(name: String): User?
}