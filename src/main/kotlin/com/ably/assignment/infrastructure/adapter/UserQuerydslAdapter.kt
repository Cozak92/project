package com.ably.assignment.infrastructure.adapter

import com.ably.assignment.domain.entity.User
import com.ably.assignment.domain.port.outbound.UserOutBoundPort

class UserQuerydslAdapter: UserOutBoundPort {
    override fun save(user: User): User {
        TODO("Not yet implemented")
    }

    override fun findByEmail(email: String): User {
        TODO("Not yet implemented")
    }
}