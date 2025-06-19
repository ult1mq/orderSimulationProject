package org.ult1mma.orderservice.service

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono
import kotlin.Boolean
@Service
class ExternalServiceClient (private val webClient: WebClient) {

    fun checkUserExists(userId: Long): Mono<Boolean> {
        return webClient.get()
            .uri("http://user-service:8082/users/$userId")
            .retrieve()
            .bodyToMono<String>()
            .map{true}
            .onErrorReturn(false)
    }

    fun checkProductExists(productId: Long): Mono<Boolean> {
        return webClient.get()
            .uri("http://product-service:8081/products/$productId")
            .retrieve()
            .bodyToMono<String>()
            .map{true}
            .onErrorReturn(false)
    }


}