package com.coursework_server.coursework_server.model

import jakarta.persistence.*

@Entity
@Table(
    name = "assignment",
    uniqueConstraints = [UniqueConstraint(columnNames = ["user_id", "car_id", "manager_id"])]
)
data class Assignment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    val car: Car,

    @ManyToOne
    @JoinColumn(name = "manager_id", nullable = false)
    val manager: User
)
