package com.ably.assignment.mapper

import com.ably.assignment.domain.dto.UserDto
import com.ably.assignment.domain.entity.User
import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty


@Mapper
interface UserMapper {
    @Mappings(
        Mapping(source = "userInformation.email", target = "email"),
        Mapping(source = "userInformation.nickname", target = "nickname"),
        Mapping(source = "userInformation.name", target = "name"),
        Mapping(source = "userInformation.phoneNationalCode", target = "phoneNationalCode"),
        Mapping(source = "userInformation.phoneNumber", target = "phoneNumber"),
    )
    fun convertToDto(person: User) : UserDto
    @InheritInverseConfiguration
    fun convertToEntity(personDto: UserDto) : User
}