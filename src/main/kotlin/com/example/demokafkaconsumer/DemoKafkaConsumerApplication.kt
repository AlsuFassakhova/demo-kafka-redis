package com.example.demokafkaconsumer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.kafka.annotation.EnableKafka

@SpringBootApplication
class DemoKafkaConsumerApplication

fun main(args: Array<String>) {
	runApplication<DemoKafkaConsumerApplication>(*args)
}
