package com.ably.assignment.domain.entity

import javax.persistence.*


@Entity
@Table(name = "authority")
data class Authority(
    @Id
    @Column(name = "authority_name", length = 50) val authorityName: String
)