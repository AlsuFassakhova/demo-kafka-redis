package com.example.demokafkaconsumer.kafka.producer

import com.example.demokafkaconsumer.kafka.consumer.Notification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class ProducerService(@Autowired private val kafkaTemplate: KafkaTemplate<String, Notification>) {

    fun sendNotification(topic: String, notification: Notification) {
        kafkaTemplate.send(topic, notification)
    }
}