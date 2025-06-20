package org.ult1mma.productservice.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.PositiveOrZero

@Entity
data class Product (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @Column(nullable = false)
    val name: String,
    val description: String,
    @field:PositiveOrZero
    val price: Double
) {
}