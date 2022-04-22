package com.ably.assignment.domain.port.outbound

import com.ably.assignment.domain.entity.User

interface UserOutBoundPort {
    fun save(user:User): User
    fun findByEmail(email: String): User?
}