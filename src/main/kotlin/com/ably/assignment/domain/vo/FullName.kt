package com.ably.assignment.domain.vo

import javax.validation.constraints.NotNull

data class FullName(
    @NotNull
    val firstName: String,
    @NotNull
    val lastName: String,
)
