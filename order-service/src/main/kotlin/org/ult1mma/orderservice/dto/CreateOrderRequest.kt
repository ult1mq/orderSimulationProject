package org.ult1mma.orderservice.dto

data class CreateOrderRequest(
    val userId: Long,
    val productId: Long,
    val quantity: Long

)
