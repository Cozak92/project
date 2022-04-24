package com.ably.assignment.adapter.api.model

import com.ably.assignment.domain.model.User
import com.ably.assignment.domain.vo.Authority
import com.ably.assignment.domain.vo.FullName
import com.ably.assignment.domain.model.Phone
import com.ably.assignment.domain.vo.Information
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

class UserDto {
    data class UserRegisterDto(
        @field:NotEmpty
        @Size(max = 50)
        val password: String,
        @field:Email
        @field:NotEmpty
        @Size(max = 50)
        val email: String,
        @field:NotEmpty
        @Size(max = 20)
        val nickname: String,
        @field:NotEmpty
        @Size(max = 20)
        val firstName: String,
        @field:NotEmpty
        @Size(max = 20)
        val lastName: String,
        @field:NotEmpty
        val phoneCountryCode: String,
        @field:NotEmpty
        val phoneNumber: String,
    ) {
        fun toDomainModel(role: Authority) = User(
            fullName = FullName(firstName, lastName),
            password = password,
            phone = Phone(phoneCountryCode, phoneNumber),
            information = Information(email = email, nickname = nickname),
            authorities = mutableSetOf(role)
        )
    }

    data class UserAuthDto(@field:NotEmpty @field:Email val email: String, @field:NotEmpty var password: String)
    data class UserTokenDto(@field:NotEmpty val value: String)
    data class UserResponseDto(
        val id: Long,
        @field:NotEmpty
        @Size(max = 50)
        val email: String,

        @field:NotEmpty
        @Size(max = 20)
        val nickname: String,

        @field:NotEmpty
        @Size(max = 20)
        val firstName: String,

        @field:NotEmpty
        @Size(max = 20)
        val lastName: String,

        @field:NotEmpty
        val phoneCountryCode: String,

        @field:NotEmpty
        val phoneNumber: String,
    )

}