package com.ably.assignment.application.port.persistence

import com.ably.assignment.domain.model.User

interface ReadOutBoundPort {
    fun existsByEmail(email: String): Boolean
    fun existsById(userId: Long): Boolean
    fun getUserByEmail(email: String): User
    fun getUserById(userId: Long) : User
    fun existsByPhone(countryCode: String, number: String): Boolean
}