package com.ably.assignment.adapter.persistence

import com.ably.assignment.adapter.persistence.model.UserData
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserJpaRepository : JpaRepository<UserData, Long> {
    fun findByEmailAndIsDeletedFalse(email: String): UserData?

    @EntityGraph(attributePaths = ["authorities"])
    fun findOneWithAuthoritiesByLastName(name: String): UserData?
    fun findByPhoneCountryCodeAndPhoneNumberAndIsDeletedFalse(countryCode: String, number: String): UserData?
    fun findByIdAndIsDeletedFalse(userId: Long): UserData?
}