package com.ably.assignment.domain.vo

import javax.validation.constraints.NotNull


data class Information(
    @NotNull
    val email: String,
    @NotNull
    val nickname: String,
)