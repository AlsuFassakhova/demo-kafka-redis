package com.example.demokafkaconsumer.consumer

import com.example.demokafkaconsumer.kafka.consumer.Notification
import com.example.demokafkaconsumer.kafka.consumer.TypeOfNotice
import org.apache.kafka.common.serialization.StringDeserializer
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.testcontainers.shaded.com.fasterxml.jackson.databind.deser.std.NumberDeserializers.IntegerDeserializer
import java.time.Duration
import java.util.*


@SpringBootTest
@DirtiesContext
@ActiveProfiles("test")
@EmbeddedKafka
@TestPropertySource(properties = ["spring.kafka.bootstrap-servers=\${spring.embedded.kafka.brokers}"])
class AnotherProducerTest {
    @Autowired
    lateinit var kafkaTemplate: KafkaTemplate<Int, Notification>

    @Test
    fun `test send notification`() {
        val consumerFactory = DefaultKafkaConsumerFactory<Int, Notification>(
            mapOf(
                "bootstrap.servers" to "\${spring.kafka.bootstrap-servers}",
                "group.id" to "testGroup",
                "key.deserializer" to "org.apache.kafka.common.serialization.IntegerDeserializer",
                "value.deserializer" to "org.springframework.kafka.support.serializer.JsonDeserializer",
                "spring.json.value.default.type" to Notification::class.java.name
            )
        )

        val consumer = consumerFactory.createConsumer()
        consumer.subscribe(listOf("test"))
        println("Слушаю")

        val notification = Notification(UUID.randomUUID(), "Hi", TypeOfNotice.EMAIL)
        kafkaTemplate.send("test", notification)
        println("Отправил")

        val records = consumer.poll(Duration.ofSeconds(10))
        assertFalse(records.isEmpty)

        consumer.close()
    }
}
