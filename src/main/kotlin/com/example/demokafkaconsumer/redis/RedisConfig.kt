package com.example.demokafkaconsumer.redis

import com.example.demokafkaconsumer.kafka.consumer.Notification
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate


@Configuration
class RedisConfig {

    @Bean
    fun jedisConnectionFactory() = JedisConnectionFactory(RedisStandaloneConfiguration("localhost", 6379))

    @Bean
    fun redisTemplate(factory: RedisConnectionFactory?): RedisTemplate<String, Notification> {
        val template = RedisTemplate<String, Notification>()
        template.connectionFactory = jedisConnectionFactory()

        template.valueSerializer = NotificationRedisSerializer()
        return template
    }
}