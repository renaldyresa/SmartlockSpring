package com.smartlock.smartlock.model.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table


@Entity
@Table(name = "door")
data class Door(
        @Id
        @Column(name = "id")
        var idDoor: String = "",
        var token: String = "",
        var pin: Int = 0,
        @Column(name = "status_rent")
        var statusRent: Boolean? = null,
        @Column(name = "status_door")
        var statusDoor: Boolean? = null,
        var owner: String = "",
        var rent: Int? = null
)