package org.ult1mma.orderservice.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.ult1mma.orderservice.model.Order
import org.ult1mma.orderservice.repository.OrderRepository
import reactor.core.publisher.Mono

@Service
class OrderService (private val orderRepo : OrderRepository,
    private val external: ExternalServiceClient) {
    fun getAll(): List<Order> = orderRepo.findAll()
    fun getById(id: Long): Order? = orderRepo.findById(id).orElse(null)

    fun create(order: Order): Mono<Order> {
        val userExists  = external.checkUserExists(order.userId)
        val productExists = external.checkProductExists(order.productId)
        return Mono.zip(userExists, productExists)
            .flatMap { tuple ->
                val u = tuple.t1
                val p = tuple.t2
                if (!u) return@flatMap Mono.error<Order>(RuntimeException("User not found"))
                if (!p) return@flatMap Mono.error<Order>(RuntimeException("Product not found"))
                Mono.fromCallable { orderRepo.save(order) }
            }
    }

    fun delete(id: Long) = orderRepo.deleteById(id)
}