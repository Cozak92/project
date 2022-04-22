package com.ably.assignment.domain.dto

import com.ably.assignment.domain.vo.UserInformation
import javax.persistence.Column
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.Max
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

data class UserDto(
    @field:NotEmpty
    @Size(max = 50)
    val password: String,
    @field:Email
    @Size(max = 50)
    val email: String,
    @field:NotEmpty
    @Size(max = 20)
    val nickname: String,
    @field:NotEmpty
    @Size(max = 20)
    val name: String,
    @field:NotEmpty
    val phoneNationalCode: String,
    @field:NotEmpty
    val phoneNumber: String
)