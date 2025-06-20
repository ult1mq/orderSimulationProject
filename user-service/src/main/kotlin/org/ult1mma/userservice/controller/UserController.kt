package org.ult1mma.userservice.controller

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.ult1mma.userservice.model.User
import org.ult1mma.userservice.service.UserService

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    private val logger = LoggerFactory.getLogger(UserController::class.java)

    @GetMapping
    fun getAll(): List<User> {
        logger.info("Получен запрос: получить всех пользователей")
        return userService.getAll()
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<User> {
        logger.info("Получен запрос: получить пользователя по id={}", id)
        val user = userService.getById(id)
        return if (user != null) {
            logger.info("Пользователь с id={} найден", id)
            ResponseEntity.ok(user)
        } else {
            logger.warn("Пользователь с id={} не найден", id)
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun create(@RequestBody user: User): User {
        logger.info("Получен запрос: создать пользователя: {}", user)
        val saved = userService.create(user)
        logger.info("Пользователь создан: id={}", saved.id)
        return saved
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        logger.info("Получен запрос: удалить пользователя id={}", id)
        userService.delete(id)
        logger.info("Пользователь с id={} удалён", id)
    }
}