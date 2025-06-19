package org.ult1mma.productservice.service

import org.springframework.stereotype.Service
import org.ult1mma.productservice.model.Product
import org.ult1mma.productservice.repository.ProductRepository


@Service
class ProductService (private val repo: ProductRepository) {
    fun getAll(): List<Product> = repo.findAll()
    fun getById(id: Long) : Product? = repo.getById(id)
    fun create(product: Product): Product = repo.save(product)
    fun delete(id: Long) = repo.deleteById(id)

}