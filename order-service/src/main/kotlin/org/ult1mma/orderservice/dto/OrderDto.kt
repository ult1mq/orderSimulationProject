package org.ult1mma.orderservice.dto

import org.ult1mma.orderservice.model.OrderStatus

data class OrderDto(
    val id: Long,
    val userId: Long,
    val productId: Long,
    val quantity: Long,
    val status: OrderStatus
)