package com.example.demokafkaconsumer.consumer

import com.example.demokafkaconsumer.kafka.consumer.ConsumerService
import com.example.demokafkaconsumer.kafka.consumer.Notification
import com.example.demokafkaconsumer.kafka.consumer.TypeOfNotice
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.fail
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer
import org.springframework.test.context.junit4.SpringRunner
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.junit.jupiter.Container
import java.util.*
import java.util.concurrent.TimeUnit


@RunWith(SpringRunner::class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KafkaConsumerTest {

    @Autowired
    lateinit var consumerService: ConsumerService

    @Autowired
    lateinit var kafkaTemplate: KafkaTemplate<String, Notification>

    @Container
    val kafkaContainer: GenericContainer<*> = GenericContainer<Nothing>("confluentinc/cp-kafka:latest")
        .apply {
            withExposedPorts(9092)
            withEnv("KAFKA_ADVERTISED_LISTENERS", "PLAINTEXT://localhost:9092")
            waitingFor(Wait.forListeningPort())
        }

    @Test
    fun `test consumer should receive wright message from kafka`() {
        val notification = Notification(
            UUID.randomUUID(),
            "Hello from test", TypeOfNotice.SMS
        )

        kafkaTemplate.send("sms", notification)

        val receivedNotification = consumerService.receivedMessages.poll(10, TimeUnit.SECONDS)

        if (receivedNotification == null) {
            assertTrue(false)
        } else {
            assertTrue(notification.userId == receivedNotification.userId)
            assertTrue(notification.message == receivedNotification.message)
            assertTrue(notification.type == receivedNotification.type)
        }
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, Notification> {
        val producerFactory: ProducerFactory<String, Notification> = DefaultKafkaProducerFactory(
            mapOf(
                "bootstrap.servers" to "localhost:9092",
                "key.serializer" to "org.apache.kafka.common.serialization.StringSerializer",
                "value.serializer" to JsonSerializer::class.java
            )
        )
        return KafkaTemplate(producerFactory)
    }

    @BeforeAll
    fun setUp() {
        kafkaContainer.start()
    }

    @AfterAll
    fun tearDown() {
        kafkaContainer.stop()
    }
}
