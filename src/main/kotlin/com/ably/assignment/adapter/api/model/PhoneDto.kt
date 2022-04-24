package com.ably.assignment.adapter.api.model

import com.ably.assignment.domain.model.Phone
import javax.validation.constraints.NotEmpty

class PhoneDto {

    data class PhoneVerifyingDto(
        @field:NotEmpty
        val phoneCountryCode: String,
        @field:NotEmpty
        val phoneNumber: String,
    ){
        fun toDomainModel() = Phone(
            countryCode = phoneCountryCode,
            numberLine = phoneNumber
        )
    }

}