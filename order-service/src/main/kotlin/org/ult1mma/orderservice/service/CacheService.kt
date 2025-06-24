package org.ult1mma.orderservice.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration


@Service
class CacheService (private val redisTemplate: StringRedisTemplate) {
    private val logger = LoggerFactory.getLogger(CacheService::class.java)

    fun save(key: String, value: String, ttlMinutes: Long = 10) {
        logger.info("Сохранение в Redis {}", key)
        redisTemplate.opsForValue().set(key, value, Duration.ofMinutes(ttlMinutes))
    }

    fun get(key: String) : String? {
        logger.info("Получение из Redis {}", key)
        return redisTemplate.opsForValue().get(key)
    }

    fun delete(key: String) {
        logger.info("Удаление в Redis {}", key)
        redisTemplate.delete(key)
    }
}