package com.coursework_server.coursework_server.repository

import com.coursework_server.coursework_server.model.Assignment
import com.coursework_server.coursework_server.model.Car
import com.coursework_server.coursework_server.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AssignmentRepository : JpaRepository<Assignment, Long> {
    fun existsByUserAndCar(user: User, car: Car): Boolean
}
