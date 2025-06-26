package org.ult1mma.productservice.dto

import org.ult1mma.productservice.model.Product

fun Product.toDto(): ProductDto = ProductDto(
    id = this.id,
    name = this.name,
    description = this.description,
    price = this.price,
)

fun CreateProductRequest.toEntity(): Product = Product(
    name = this.name,
    description = this.description,
    price = this.price,
)