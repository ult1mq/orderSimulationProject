package org.ult1mma.orderservice.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.ult1mma.orderservice.model.Order
import org.ult1mma.orderservice.service.OrderService
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/orders")
class OrderController(private val orderService: OrderService) {

    @GetMapping
    fun getAll() = orderService.getAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) = orderService.getById(id)?.let{ ResponseEntity.ok(it)} ?: ResponseEntity.notFound().build()

    @PostMapping
    fun create(@RequestBody order: Order): Mono<ResponseEntity<Order>> =
        orderService.create(order)
            .map { ResponseEntity.ok(it) }
            .onErrorResume { ex -> Mono.just(ResponseEntity.badRequest().body(null)) }


    @DeleteMapping("/{id}")
    fun delete(@PathVariable id : Long) = orderService.delete(id)


}