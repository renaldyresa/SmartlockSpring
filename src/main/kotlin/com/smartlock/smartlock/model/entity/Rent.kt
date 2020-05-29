package com.smartlock.smartlock.model.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "rent")
data class Rent(
        @Id
        @Column(name = "id")
        var idRent: Int = 0,
        @Column(name = "order_date")
        var orderDate: String = "",
        @Column(name = "date_in")
        var dateIn: String = "",
        @Column(name = "date_out")
        var dateOut: String = "",
        @Column(name = "id_door")
        var idDoor: String = "",
        var username: String = ""
)