package org.ult1mma.productservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.ult1mma.productservice.model.Product

interface ProductRepository : JpaRepository<Product, Long> {
}