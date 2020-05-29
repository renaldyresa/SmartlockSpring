package com.smartlock.smartlock.model.entity

import javax.persistence.*

@Entity
@Table(name = "user")
data class User(
        @Id
        var id: Int = 0,
        var active: Boolean = true,
        var username: String = "",
        var password: String = "",
        var email: String? = null,
        var name: String? = null,
        var role: String = "USER"
)