package org.ult1mma.userservice.service

import org.springframework.stereotype.Service
import org.ult1mma.userservice.model.User
import org.ult1mma.userservice.repository.UserRepository
import java.util.Optional

@Service
class UserService(private val userRepository: UserRepository) {
    fun getAll(): List<User> = userRepository.findAll()
    fun getById(id: Long): User? = userRepository.findById(id).orElse(null)
    fun create(user: User): User = userRepository.save(user)
    fun delete(id: Long) = userRepository.deleteById(id)

}