package com.ably.assignment.application.port.persistence

import com.ably.assignment.domain.model.User

interface WriteOutBoundPort {
    fun save(model: User): User
    fun updateUserInfo(user:User): User
    fun updateUserPasswd(user:User): User
}