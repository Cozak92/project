package com.ably.assignment.adapter.api

import io.swagger.v3.oas.annotations.Hidden
import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@Hidden
class AppProfileController(private val env: Environment) {

    @GetMapping("/profile")
    fun getProfile(): String? {
        return env.activeProfiles.first() ?: ""
    }
}