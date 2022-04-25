package com.ably.assignment.domain.vo

import javax.validation.constraints.NotNull


data class Information(

    val email: String? = null,

    val nickname: String,
)