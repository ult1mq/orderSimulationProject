package org.ult1mma.productservice.controller

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.ult1mma.productservice.dto.CreateProductRequest
import org.ult1mma.productservice.dto.ProductDto
import org.ult1mma.productservice.dto.toDto
import org.ult1mma.productservice.dto.toEntity
import org.ult1mma.productservice.model.Product
import org.ult1mma.productservice.service.ProductService


@RestController
@RequestMapping("/products")
class ProductController(val productService: ProductService) {

    private val logger = LoggerFactory.getLogger(ProductController::class.java)

    @GetMapping
    fun getAll(): List<ProductDto> {
        logger.info("Получен запрос: получить все товары")
        return productService.getAll().map{it.toDto()}
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<ProductDto> {
        logger.info("Получен запрос: получить товар по id={}", id)
        val product = productService.getById(id)
        return if (product != null) {
            logger.info("Товар с id={} найден", id)
            ResponseEntity.ok(product.toDto())
        } else {
            logger.warn("Товар с id={} не найден", id)
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun create(@RequestBody product: CreateProductRequest): ProductDto {
        logger.info("Получен запрос: создать товар: {}", product)

        val productEntity = product.toEntity()
        val saved = productService.create(productEntity)
        logger.info("Товар создан: id={}", saved.id)
        return saved.toDto()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        logger.info("Получен запрос: удалить товар id={}", id)
        productService.delete(id)
        logger.info("Товар с id={} удалён", id)
    }
}