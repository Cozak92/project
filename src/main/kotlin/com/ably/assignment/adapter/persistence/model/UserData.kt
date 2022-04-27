package com.ably.assignment.adapter.persistence.model

import com.ably.assignment.domain.model.User
import com.ably.assignment.domain.vo.Authority
import com.ably.assignment.domain.vo.FullName
import com.ably.assignment.domain.model.Phone
import com.ably.assignment.domain.vo.Information
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "user")
@DynamicUpdate
data class UserData(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Column(nullable = false, length = 300)
    var password: String,

    @Column(nullable = false, length = 200)
    var email: String,

    @Column(nullable = false, length = 100)
    var nickname: String,

    @Column(nullable = false, length = 50)
    var firstName: String,

    @Column(nullable = false, length = 50)
    var lastName: String,

    @Column(nullable = false, length = 20)
    var phoneCountryCode: String,

    @Column(nullable = false)
    var phoneNumber: String,

    var deletedAt: LocalDateTime? = null,

    @AttributeOverride(name = "value", column = Column(name = "isDeleted"))
    var isDeleted: Boolean = false,

    @ManyToMany
    @JoinTable(
        name = "user_authority",
        joinColumns = [JoinColumn(name = "id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "authority_name", referencedColumnName = "authority_name")]
    )
    var authorities: MutableSet<AuthorityData>,
): BaseTime() {
    fun toDomainModel(): User = User(
        id=id,
        password = password,
        fullName= FullName(firstName,lastName),
        phone = Phone(phoneCountryCode, phoneNumber),
        information = Information(email,nickname),
        authorities = authorities.map { Authority.valueOf(it.authorityName) }.toMutableSet()
    )
}