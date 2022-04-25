package com.ably.assignment.adapter.persistence.model

import com.ably.assignment.adapter.api.model.SidDto
import org.springframework.data.redis.core.RedisHash
import javax.persistence.Id

@RedisHash(value = "token", timeToLive = 300)
data class SidData(
    @Id var id: String,

    val value: String
){
    fun toSidDto():SidDto{
        return SidDto(sid = id)
    }
}