package org.ult1mma.orderservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.ult1mma.orderservice.model.Order


interface OrderRepository : JpaRepository<Order, Long> {
}