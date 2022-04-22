package com.ably.assignment.domain.entity

import com.ably.assignment.domain.vo.UserInformation
import javax.persistence.*

@Entity
@Table(name = "user")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(nullable = false, length = 300)
    var password: String,

    @AttributeOverride(name = "value" , column=Column(name="isDeleted"))
    val isDeleted: Boolean,

    @Embedded
    var userInformation: UserInformation,

    @ManyToMany
    @JoinTable(
        name = "user_authority",
        joinColumns = [JoinColumn(name = "id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "authority_name", referencedColumnName = "authority_name")]
    )
    var authorities: MutableSet<Authority>
){
    fun changeUserInformation(newUserInformation: UserInformation){
        userInformation = newUserInformation
    }
    fun changePassword(newPassword: String){
        password = newPassword
    }

}