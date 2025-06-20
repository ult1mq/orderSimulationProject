package org.ult1mma.orderservice.config

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {

    private val logger = LoggerFactory.getLogger(WebClientConfig::class.java)

    @Bean
    fun webClient(): WebClient {
        logger.info("Создаётся бин WebClient (reactive HTTP клиент)")
        return WebClient.builder().build()
    }
}