package com.ably.assignment.adapter.api.model

import java.time.LocalDateTime

data class Response(
    var message: String,
    var status: String,
    var timestamp: LocalDateTime,
    var data: MutableList<Any> = mutableListOf(),
)
