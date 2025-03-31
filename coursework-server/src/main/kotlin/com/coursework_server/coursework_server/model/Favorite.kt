package com.coursework_server.coursework_server.model

import jakarta.persistence.*

@Entity
@Table(name = "favorite")
data class Favorite(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne
    @JoinColumn(name = "cars_id", nullable = false)
    val car: Car
)
