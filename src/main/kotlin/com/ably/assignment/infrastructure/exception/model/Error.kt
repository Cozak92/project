package com.ably.assignment.infrastructure.exception.model

data class Error(
    var field: String? = null,
    var message: String? = null,
    var value: Any? = null
)