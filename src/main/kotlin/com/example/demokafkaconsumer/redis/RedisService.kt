package com.example.demokafkaconsumer.redis

import com.example.demokafkaconsumer.kafka.consumer.Notification
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.ListOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.Executors


@Service
class RedisService(
    private val redisTemplate: RedisTemplate<String, Notification>,
    @Value("\${spring.redis.queue}") private val queueName: String,
) {

    fun pushToQueue(value: Notification) {
        val listOperations: ListOperations<String, Notification> = redisTemplate.opsForList()
        listOperations.rightPush(queueName, value)
    }

    fun popFromQueue(): Any? {
        val listOperations: ListOperations<String, Notification> = redisTemplate.opsForList()
        return listOperations.leftPop(queueName)
    }

    fun startPolling() {
        while (true) {
            val result = popFromQueue()
            if (result != null) {
                println("Получен элемент: $result")
            } else {
                println("пусто")
                Thread.sleep(5000)
            }
        }
    }
}

