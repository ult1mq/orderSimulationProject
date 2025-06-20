package org.ult1mma.userservice.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.ult1mma.userservice.model.User
import org.ult1mma.userservice.repository.UserRepository

@Service
class UserService(private val userRepository: UserRepository) {

    private val logger = LoggerFactory.getLogger(UserService::class.java)

    fun getAll(): List<User> {
        logger.info("Получение списка всех пользователей")
        val users = userRepository.findAll()
        logger.info("Найдено пользователей: {}", users.size)
        return users
    }

    fun getById(id: Long): User? {
        logger.info("Запрос пользователя по id={}", id)
        val user = userRepository.findById(id).orElse(null)
        if (user == null) {
            logger.warn("Пользователь с id={} не найден", id)
        } else {
            logger.info("Пользователь с id={} найден: {}", id, user)
        }
        return user
    }

    fun create(user: User): User {
        logger.info("Создание пользователя: {}", user)
        val saved = userRepository.save(user)
        logger.info("Пользователь успешно создан: id={}", saved.id)
        return saved
    }

    fun delete(id: Long) {
        logger.info("Удаление пользователя с id={}", id)
        userRepository.deleteById(id)
        logger.info("Пользователь с id={} удалён", id)
    }
}