package org.ult1mma.orderservice.dto

import org.ult1mma.orderservice.model.Order
import org.ult1mma.orderservice.model.OrderStatus
import java.math.BigDecimal

fun Order.toDto(): OrderDto = OrderDto(
    id = this.id,
    userId = this.userId,
    productId = this.productId,
    quantity = this.quantity,
    status = OrderStatus.valueOf(this.status)
)

// Map CreateOrderRequest to Order entity; price not stored in Order
fun CreateOrderRequest.toEntity(): Order = Order(
    userId = this.userId,
    productId = this.productId,
    quantity = this.quantity,
    status = OrderStatus.NEW.name
)