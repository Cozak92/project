package com.ably.assignment.adapter.persistence.model

import org.springframework.data.redis.core.RedisHash
import javax.persistence.Id

@RedisHash(value = "token", timeToLive = 300)
data class SidData(
    @Id var id: String? = null,

    val value: String
)