package org.ult1mma.orderservice.controller

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.ult1mma.orderservice.dto.OrderDto
import org.ult1mma.orderservice.dto.toDto
import org.ult1mma.orderservice.model.Order
import org.ult1mma.orderservice.service.OrderService
import reactor.core.publisher.Mono
import org.ult1mma.orderservice.dto.CreateOrderRequest
import org.ult1mma.orderservice.dto.toDto
import org.ult1mma.orderservice.dto.toEntity

@RestController
@RequestMapping("/orders")
class OrderController(private val orderService: OrderService) {

    private val logger = LoggerFactory.getLogger(OrderController::class.java)

    @GetMapping
    fun getAll(): List<OrderDto> {
        logger.info("Получен запрос: получить все заказы")
        return orderService.getAll().map { it.toDto() }
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<OrderDto> {
        logger.info("Получен запрос: получить заказ по id={}", id)
        return orderService.getById(id)
            ?.let {
                logger.info("Заказ с id={} найден", id)
                ResponseEntity.ok(it.toDto())
            }
            ?: run {
                logger.warn("Заказ с id={} не найден", id)
                ResponseEntity.notFound().build()
            }
    }
    @PostMapping
    fun create(@RequestBody request: CreateOrderRequest): Mono<ResponseEntity<OrderDto>> {
        logger.info("Получен запрос: создать заказ: {}", request)
        val order = request.toEntity()
        return orderService.create(order)
            .map {
                logger.info("Заказ успешно создан: id={}", it.id)
                ResponseEntity.ok(it.toDto())
            }
            .onErrorResume { ex ->
                logger.error("Ошибка при создании заказа: {}", ex.message)
                Mono.just(ResponseEntity.badRequest().body(null))
            }
    }


    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) : ResponseEntity<Void> {
        logger.info("Получен запрос: удалить заказ id={}", id)
        return if (orderService.delete(id)) {
            ResponseEntity.noContent().build()
        }
        else {
            ResponseEntity.notFound().build()
        }
    }


}

