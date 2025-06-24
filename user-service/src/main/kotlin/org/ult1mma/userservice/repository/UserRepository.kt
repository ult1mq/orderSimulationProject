package org.ult1mma.userservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.ult1mma.userservice.model.User

interface UserRepository : JpaRepository<User, Long> {
    fun existsByEmail(email: String): Boolean
    fun existsByName(name: String): Boolean
}