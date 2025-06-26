package org.ult1mma.userservice.controller

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.ult1mma.userservice.dto.CreateUserRequest
import org.ult1mma.userservice.dto.UserDto
import org.ult1mma.userservice.dto.toDto
import org.ult1mma.userservice.dto.toEntity
import org.ult1mma.userservice.model.User
import org.ult1mma.userservice.service.UserService
import kotlin.random.Random

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    private val logger = LoggerFactory.getLogger(UserController::class.java)

    @GetMapping
    fun getAll(): List<UserDto> {
        logger.info("Получен запрос: получить всех пользователей")
        return userService.getAll().map{ it.toDto() }
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<UserDto> {
        logger.info("Получен запрос: получить пользователя по id={}", id)
        val user = userService.getById(id)
        return if (user != null) {
            logger.info("Пользователь с id={} найден", id)
            ResponseEntity.ok(user.toDto())
        } else {
            logger.warn("Пользователь с id={} не найден", id)
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun create(@RequestBody user: CreateUserRequest): UserDto {
        logger.info("Получен запрос: создать пользователя: {}", user)
        val userEntity = user.toEntity()
        val saved = userService.create(userEntity)
        logger.info("Пользователь создан: id={}", saved.id)
        return saved.toDto()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        logger.info("Получен запрос: удалить пользователя id={}", id)
        userService.delete(id)
        logger.info("Пользователь с id={} удалён", id)
    }
    @PostMapping("/generate")
    fun generateUsers(@RequestParam count: Int): List<UserDto> {
        logger.info("Запрошена генерация {} пользователей", count)
        val generated = (1..count).map {
            val user = User(
                email = "user$it${Random.nextInt(10000)}@example.com",
                password = "password", // если есть поле
                name = "TestUser$it"
            )
            userService.create(user)
        }
        logger.info("Сгенерировано {} пользователей", generated.size)
        return generated.map{ it.toDto() }
    }
}