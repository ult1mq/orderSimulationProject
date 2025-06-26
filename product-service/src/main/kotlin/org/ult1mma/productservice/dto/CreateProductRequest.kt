package org.ult1mma.productservice.dto

data class CreateProductRequest (
    val name: String,
    val description: String,
    val price: Double,
)