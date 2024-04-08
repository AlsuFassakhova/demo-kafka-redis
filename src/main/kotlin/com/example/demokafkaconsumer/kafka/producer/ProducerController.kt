package com.example.demokafkaconsumer.kafka.producer

import com.example.demokafkaconsumer.kafka.consumer.Notification
import com.example.demokafkaconsumer.kafka.consumer.TypeOfNotice
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.*

@Controller
class ProducerController(val producerService: ProducerService) {

    @GetMapping("/test/kafka")
    @ResponseStatus(HttpStatus.OK)
    fun sendToKafka(){
        producerService.sendNotification("sms", Notification(UUID.randomUUID(), "Hi"))
    }
}