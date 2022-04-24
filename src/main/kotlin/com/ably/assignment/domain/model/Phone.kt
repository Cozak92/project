package com.ably.assignment.domain.model

import com.google.i18n.phonenumbers.PhoneNumberUtil
import javax.validation.constraints.NotNull

data class Phone(
    @NotNull
    val countryCode: String,
    @NotNull
    val numberLine: String,
    ) {
    val number: String
        get() {
            return "(+${countryCode}) $numberLine"
        }

    fun isValid():Unit {
        val phoneUtil = PhoneNumberUtil.getInstance()
        val parsedPhone = phoneUtil.parse(number,"KR")
        if (!phoneUtil.isValidNumber(parsedPhone)){
            throw IllegalArgumentException("Invalid Phone Number")
        }
    }
}