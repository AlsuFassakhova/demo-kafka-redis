package com.example.demokafkaconsumer.kafka.consumer

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.serialization.Deserializer
import org.springframework.data.redis.serializer.RedisSerializer
import java.io.IOException
import java.util.*


class NotificationDeserializer : Deserializer<Notification> {

    private val objectMapper = ObjectMapper()

    override fun deserialize(topic: String, data: ByteArray): Notification? {
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


