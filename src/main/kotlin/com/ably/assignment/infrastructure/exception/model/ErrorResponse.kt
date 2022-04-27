package com.ably.assignment.infrastructure.exception.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class ErrorResponse(

    @field:JsonProperty("result_code")
    var resultCode: String? = null,

    @field:JsonProperty("http_status")
    var httpStatus: String? = null,

    @field:JsonProperty("http_method")
    var httpMethod: String? = null,

    var message: String? = null,
    var path: String? = null,
    var timestamp: LocalDateTime? = null,
    var errors: MutableList<Error>? = mutableListOf()
)