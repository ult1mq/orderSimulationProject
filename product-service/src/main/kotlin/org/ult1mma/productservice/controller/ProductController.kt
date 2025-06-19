package org.ult1mma.productservice.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.ult1mma.productservice.model.Product
import org.ult1mma.productservice.service.ProductService


@RestController
@RequestMapping("/products")
class ProductController(val productService: ProductService) {

    @GetMapping
    fun getAll() = productService.getAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) =
        (productService.getById(id)?.let { ResponseEntity.ok(it)} ?: ResponseEntity.notFound().build())

    @PostMapping
    fun create(@RequestBody product: Product) = productService.create(product)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = productService.delete(id)
}