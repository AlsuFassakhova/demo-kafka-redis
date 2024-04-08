//package com.example.demokafkaconsumer.consumer
//
//import com.example.demokafkaconsumer.kafka.consumer.Notification
//import com.example.demokafkaconsumer.kafka.consumer.TypeOfNotice
//import com.example.demokafkaconsumer.kafka.producer.ProducerService
//import org.apache.kafka.clients.consumer.ConsumerConfig
//import org.apache.kafka.clients.consumer.KafkaConsumer
//import org.apache.kafka.clients.producer.MockProducer
//import org.apache.kafka.common.serialization.IntegerDeserializer
//import org.apache.kafka.common.serialization.StringSerializer
//import org.junit.jupiter.api.*
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.kafka.core.DefaultKafkaProducerFactory
//import org.springframework.kafka.core.KafkaTemplate
//import org.springframework.kafka.core.ProducerFactory
//import org.springframework.kafka.support.serializer.JsonDeserializer
//import org.springframework.kafka.support.serializer.JsonSerializer
//import org.springframework.kafka.test.context.EmbeddedKafka
//import org.springframework.test.annotation.DirtiesContext
//import org.springframework.test.context.TestPropertySource
//import java.util.*
//
//
//@SpringBootTest
//@TestPropertySource(properties = [
//    "spring.kafka.producer.bootstrap-servers=localhost:9092",
//    "spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer",
//    "spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer"
//])
//class ProducerServiceTest {
//
//    @Autowired
//    private lateinit var kafkaTemplate: KafkaTemplate<String, Notification>
//
//    @Test
//    fun `test sendNotification`() {
//        // Создаем MockProducer для тестирования
//        val mockProduсer = MockProducer(true, StringSerializer(), StringSerializer())
//
//
//        // Создаем экземпляр сервиса с использованием mockProducer
//        val producerService = ProducerService(kafkaTemplate)
//
//        // Создаем тестовую уведомление
//        val topic = "test-topic"
//        val notification = Notification("Test message")
//
//        // Отправляем уведомление
//        producerService.sendNotification(topic, notification)
//
//        // Проверяем, что сообщение было отправлено
//        assertEquals(1, mockProducer.history().size)
//        val sentRecord = mockProducer.history().first()
//        assertEquals(topic, sentRecord.topic())
//        assertEquals(notification, sentRecord.value())
//
//        // Проверяем, что сериализация прошла успешно
//        assertEquals(JsonSerializer<Notification>().serialize(topic, notification), sentRecord.value())
//    }
//}