package com.ably.assignment.adapter.persistence.model

import javax.persistence.*


@Entity
@Table(name = "authority")
data class AuthorityData(
    @Id
    @Column(name = "authority_name", length = 50) val authorityName: String
)