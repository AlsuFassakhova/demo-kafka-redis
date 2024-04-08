package com.example.demokafkaconsumer.redis;

import com.example.demokafkaconsumer.kafka.consumer.Notification
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.redis.serializer.RedisSerializer
import java.io.IOException
import java.util.*

class NotificationRedisSerializer : RedisSerializer<Notification> {

    private val objectMapper = ObjectMapper()

    override fun serialize(value: Notification?): ByteArray? {
        return try {
            if (value != null) {
                val jsonString = objectMapper.writeValueAsString(value)
                jsonString.toByteArray(Charsets.UTF_8)
            } else {
                null
            }
        } catch (e: IOException) {
            throw RuntimeException("Failed to serialize Notification", e)
        }
    }

    override fun deserialize(data: ByteArray?): Notification? {
        return try {
            val node = objectMapper.readTree(data)

            val userId = UUID.fromString(node["userId"].asText())
            val message = node["message"].asText()

            Notification(userId, message)
        } catch (e: IOException) {
            throw RuntimeException("Failed to deserialize Notification", e)
        }
    }
}
