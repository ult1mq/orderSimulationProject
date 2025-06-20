package org.ult1mma.orderservice.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono
import kotlin.Boolean
@Service
class ExternalServiceClient(private val webClient: WebClient) {

    private val logger = LoggerFactory.getLogger(ExternalServiceClient::class.java)

    fun checkUserExists(userId: Long): Mono<Boolean> {
        logger.info("Проверка наличия пользователя с id={}", userId)
        return webClient.get()
            .uri("http://user-service:8082/users/$userId")
            .retrieve()
            .bodyToMono<String>()
            .map {
                logger.info("Пользователь с id={} найден (ответ user-service 200)", userId)
                true
            }
            .onErrorResume { ex ->
                logger.warn("Ошибка при проверке пользователя userId={}: {} (user-service недоступен или не найден)", userId, ex.message)
                Mono.just(false)
            }
    }

    fun checkProductExists(productId: Long): Mono<Boolean> {
        logger.info("Проверка наличия товара с id={}", productId)
        return webClient.get()
            .uri("http://product-service:8081/products/$productId")
            .retrieve()
            .bodyToMono<String>()
            .map {
                logger.info("Товар с id={} найден (ответ product-service 200)", productId)
                true
            }
            .onErrorResume { ex ->
                logger.warn("Ошибка при проверке товара productId={}: {} (product-service недоступен или не найден)", productId, ex.message)
                Mono.just(false)
            }
    }
}