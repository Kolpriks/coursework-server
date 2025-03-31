package com.coursework_server.coursework_server.repository

import com.coursework_server.coursework_server.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long>
