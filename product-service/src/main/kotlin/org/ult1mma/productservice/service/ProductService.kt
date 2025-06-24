package org.ult1mma.productservice.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.ult1mma.productservice.model.Product
import org.ult1mma.productservice.repository.ProductRepository


@Service
class ProductService(private val repo: ProductRepository,
    private val objectMapper: ObjectMapper,
    private val cacheService: CacheService) {

    private val logger = LoggerFactory.getLogger(ProductService::class.java)

    fun getAll(): List<Product> {
        logger.info("Получение списка всех товаров")
        val products = repo.findAll()
        logger.info("Найдено товаров: {}", products.size)
        return products
    }

    fun getById(id: Long): Product? {
        logger.info("Запрос товара по id={}", id)
        val cacheKey = "product:$id"
        val cachedProductJson = cacheService.get(cacheKey)
        if (cachedProductJson != null) {
            logger.info("Найден продукт {} в Redis", id)
            return objectMapper.readValue(cachedProductJson, Product::class.java)
        }
        val product = repo.findById(id).orElse(null)
        if (product == null) {
            logger.warn("Продукт с id={} не найден", id)
        } else {
            logger.info("Продукт с id={} найден", id)
            cacheService.save(cacheKey, objectMapper.writeValueAsString(product))
        }
        return product

    }

    fun create(product: Product): Product {
        logger.info("Создание товара: {}", product)
        if (product.price < 0) {
            logger.warn("Отрицательная стоимость товара id={}, не сохранен", product.id)
            return product
        }
        val saved = repo.save(product)
        logger.info("Товар успешно создан: id={}", saved.id)
        return saved
    }

    fun delete(id: Long) {
        logger.info("Удаление товара с id={}", id)
        if (!repo.existsById(id)) {
            logger.warn("Продукт с таким id={} не найден", id)
            return
        }
        cacheService.delete("product:$id")
        repo.deleteById(id)
        logger.info("Товар с id={} удалён", id)
    }
}