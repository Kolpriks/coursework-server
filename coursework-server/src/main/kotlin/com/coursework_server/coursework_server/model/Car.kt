package com.coursework_server.coursework_server.model

import java.math.BigDecimal
import jakarta.persistence.*

@Entity
@Table(name = "cars")
data class Car(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val name: String,

    val price: BigDecimal,

    @Lob
    @JsonIgnore
    val image: ByteArray? = null,

    val description: String? = null,

    val consumption: BigDecimal,

    val seats: Int,

    val co2: BigDecimal
)
