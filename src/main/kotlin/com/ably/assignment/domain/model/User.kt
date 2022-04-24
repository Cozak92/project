package com.ably.assignment.domain.model

import com.ably.assignment.adapter.api.model.UserDto.*
import com.ably.assignment.adapter.persistence.model.AuthorityData
import com.ably.assignment.adapter.persistence.model.UserData
import com.ably.assignment.domain.vo.Authority
import com.ably.assignment.domain.vo.FullName
import com.ably.assignment.domain.vo.Information
import org.springframework.security.crypto.password.PasswordEncoder
import javax.validation.constraints.NotNull

data class User(
    val id: Long? = null,

    var password: String,

    @NotNull
    val fullName: FullName,

    @NotNull
    val phone: Phone,

    @NotNull
    val information: Information,

    @NotNull
    val authorities: MutableSet<Authority>,

    var passwordEncoder: PasswordEncoder? = null,

    ){
    fun toJpaEntity(): UserData = UserData(
        id = id,
        password = password,
        firstName = fullName.firstName,
        lastName = fullName.lastName,
        phoneCountryCode = phone.countryCode,
        phoneNumber = phone.numberLine,
        email = information.email,
        nickname = information.nickname,
        authorities = authorities.map { AuthorityData(it.role) }.toMutableSet()
    )

    fun toResponseDto(): UserResponseDto = UserResponseDto(
        id = id!!,
        firstName = fullName.firstName,
        lastName = fullName.lastName,
        phoneCountryCode = phone.countryCode,
        phoneNumber = phone.numberLine,
        email = information.email,
        nickname = information.nickname,
    )

    fun encryptPassword(){
        password = passwordEncoder!!.encode(password)
    }
}