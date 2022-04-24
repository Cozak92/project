package com.ably.assignment.adapter.persistence

import com.ably.assignment.application.port.persistence.ReadOutBoundPort
import com.ably.assignment.application.port.persistence.WriteOutBoundPort
import com.ably.assignment.domain.model.User
import com.ably.assignment.infrastructure.annotations.Adapter
import org.springframework.data.repository.findByIdOrNull


@Adapter
class UserJpaAdapter(private val userJpaRepository: UserJpaRepository): ReadOutBoundPort, WriteOutBoundPort {

    override fun existsByEmail(email: String): Boolean {
        return userJpaRepository.findByEmail(email)?.let{
            true
        } ?: false
    }

    override fun existsById(userId: Long): Boolean {
        return userJpaRepository.findByIdOrNull(userId)?.let{
            true
        } ?: false
    }

    override fun getUserByEmail(email: String): User {
        return userJpaRepository.findByEmail(email)!!.toDomainModel()
    }

    override fun getUserById(userId: Long): User {
        return userJpaRepository.findByIdOrNull(userId)!!.toDomainModel()
    }

    override fun existsByPhone(countryCode: String, number: String): Boolean {
        return userJpaRepository.findByPhoneCountryCodeAndPhoneNumber(countryCode,number)?.let{
            true
        } ?: false
    }

    override fun save(model: User): User {
        return userJpaRepository.save(model.toJpaEntity()).toDomainModel()
    }
}