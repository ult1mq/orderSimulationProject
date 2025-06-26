package org.ult1mma.orderservice.dto


data class OrderDto(
    val id: Long,
    val userId: Long,
    val productId: Long,
    val quantity: Long,
    val status: String
)