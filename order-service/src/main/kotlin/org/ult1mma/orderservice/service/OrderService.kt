package org.ult1mma.orderservice.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.ult1mma.orderservice.model.Order
import org.ult1mma.orderservice.repository.OrderRepository
import reactor.core.publisher.Mono

@Service
class OrderService(
    private val orderRepo: OrderRepository,
    private val external: ExternalServiceClient,
    private val cacheService: CacheService,
    private val objectMapper: ObjectMapper
) {
    private val logger = LoggerFactory.getLogger(OrderService::class.java)

    fun getAll(): List<Order> {
        logger.info("Получение всех заказов")
        val orders = orderRepo.findAll()
        logger.info("Найдено {} заказов", orders.size)
        return orders

    }

    fun getById(id: Long): Order? {
        logger.info("Запрос заказа по id={}", id)
        val cacheKey = "order:$id"
        val cachedOrderJson = cacheService.get(cacheKey)
        if (cachedOrderJson != null) {
            logger.info("Найден заказ {} в Redis", id)
            return objectMapper.readValue(cachedOrderJson, Order::class.java)
        }
        val order = orderRepo.findById(id).orElse(null)
        if (order == null) {
            logger.warn("Заказ с id={} не найден", id)
        } else {
            logger.info("Заказ с id={} найден: {}", id, order)
            cacheService.save(cacheKey, objectMapper.writeValueAsString(order))
        }
        return order
    }

    fun create(order: Order): Mono<Order> {
        logger.info("Попытка создать заказ: {}", order)
        val userExists = external.checkUserExists(order.userId)
        val productExists = external.checkProductExists(order.productId)
        return Mono.zip(userExists, productExists)
            .flatMap { tuple ->
                val u = tuple.t1
                val p = tuple.t2
                if (!u) {
                    logger.warn("Пользователь с id={} не найден. Заказ не будет создан.", order.userId)
                    return@flatMap Mono.error<Order>(RuntimeException("User not found"))
                }
                if (!p) {
                    logger.warn("Товар с id={} не найден. Заказ не будет создан.", order.productId)
                    return@flatMap Mono.error<Order>(RuntimeException("Product not found"))
                }
                logger.info("Пользователь и товар найдены. Создаём заказ для userId={}, productId={}", order.userId, order.productId)
                Mono.fromCallable {
                    val saved = orderRepo.save(order)
                    logger.info("Заказ успешно сохранён: id={}", saved.id)
                    saved
                }
            }
            .doOnError { ex ->
                logger.error("Ошибка при создании заказа: {}", ex.message)
            }
    }

    fun delete(id: Long) {
        logger.info("Удаление заказа с id={}", id)
        if (!orderRepo.existsById(id)) {
            logger.warn("Заказ с таким id={} не найден", id)
            return
        }
        cacheService.delete("order:$id")
        orderRepo.deleteById(id)
        logger.info("Заказ с id={} удалён", id)
    }
}