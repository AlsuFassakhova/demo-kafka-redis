package com.example.demokafkaconsumer.kafka.consumer

import com.example.demokafkaconsumer.redis.RedisService
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

@Service
class ConsumerService (private val redisService: RedisService){

    val receivedMessages: BlockingQueue<Notification> = LinkedBlockingQueue()

    @KafkaListener(topics = ["push", "email", "sms"], groupId = "\${spring.kafka.consumer.group-id}")
    fun listenMessage(notification: Notification) {
        println(notification)
        receivedMessages.put(notification)

        redisService.pushToQueue(notification)
        println("Saved to redis")
        redisService.popFromQueue()
    }
}