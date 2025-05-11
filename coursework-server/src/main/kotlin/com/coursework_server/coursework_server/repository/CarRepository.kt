package com.coursework_server.coursework_server.repository
import com.coursework_server.coursework_server.model.Car
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CarRepository : JpaRepository<Car, Long>
