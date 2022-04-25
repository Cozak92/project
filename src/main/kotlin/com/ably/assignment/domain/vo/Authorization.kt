package com.ably.assignment.domain.vo

import com.ably.assignment.adapter.api.model.UserDto.*
import javax.validation.constraints.NotEmpty

data class Authorization(val email: String, var password: String, var token: String?){
    fun toUserAuthResponseDto():UserAuthResponseDto{
        return UserAuthResponseDto(value = token!!)
    }
}
