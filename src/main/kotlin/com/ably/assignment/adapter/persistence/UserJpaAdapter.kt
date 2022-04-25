package com.ably.assignment.adapter.persistence

import com.ably.assignment.application.port.persistence.ReadOutBoundPort
import com.ably.assignment.application.port.persistence.WriteOutBoundPort
import com.ably.assignment.domain.model.User
import com.ably.assignment.infrastructure.annotations.Adapter
import org.springframework.data.repository.findByIdOrNull


@Adapter
class UserJpaAdapter(private val userJpaRepository: UserJpaRepository) : ReadOutBoundPort, WriteOutBoundPort {

    override fun existsByEmail(email: String): Boolean {
        return userJpaRepository.findByEmailAndIsDeletedFalse(email)?.let {
            true
        } ?: false
    }

    override fun existsById(userId: Long): Boolean {
        return userJpaRepository.findByIdOrNull(userId)?.let {
            true
        } ?: false
    }

    override fun existsByPhone(countryCode: String, number: String): Boolean {
        return userJpaRepository.findByPhoneCountryCodeAndPhoneNumberAndIsDeletedFalse(countryCode, number)?.let {
            true
        } ?: false
    }

    override fun getUserByEmail(email: String): User {
        return userJpaRepository.findByEmailAndIsDeletedFalse(email)!!.toDomainModel()
    }

    override fun getUserById(userId: Long): User {
        return userJpaRepository.findByIdOrNull(userId)!!.toDomainModel()
    }

    override fun save(model: User): User {
        return userJpaRepository.save(model.toJpaEntity()).toDomainModel()
    }

    override fun updateUserInfo(user: User): User {
        val userId = user.id
        val changedUser = userJpaRepository.findByIdOrNull(userId)!!.apply {
            nickname = user.information!!.nickname
            firstName = user.fullName!!.firstName
            lastName = user.fullName!!.lastName
        }
        return changedUser.toDomainModel()
    }

    override fun updateUserPasswd(user: User): User {
        val userId = user.id
        val changedUser = userJpaRepository.findByIdOrNull(userId)!!.apply {
            password = user.password!!
        }
        return changedUser.toDomainModel()
    }
}