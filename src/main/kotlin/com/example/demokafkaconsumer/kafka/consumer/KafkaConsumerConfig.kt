//package com.example.demokafkaconsumer.consumer
//
//import org.apache.kafka.clients.consumer.ConsumerConfig
//import org.apache.kafka.common.serialization.StringDeserializer
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
//import org.springframework.kafka.core.ConsumerFactory
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory
//import org.springframework.kafka.support.serializer.JsonDeserializer
//
//@Configuration
//class KafkaConsumerConfig {
//    @Value("\${spring.kafka.consumer.properties.spring.json.trusted.packages}")
//    lateinit var trustedPackage: String
//
//    @Value("\${spring.kafka.bootstrap-servers}")
//    lateinit var bootstrapServers: String
//
//    @Value("\${spring.kafka.group-id}")
//    lateinit var groupId: String
//
//    @Bean
//    fun notificationConsumerFactory(): ConsumerFactory<String, Notification> {
//        val jsonDeserializer = JsonDeserializer(Notification::class.java, false)
//        jsonDeserializer.addTrustedPackages(trustedPackage)
//        return DefaultKafkaConsumerFactory(
//            consumerConfig(),
//            StringDeserializer(),
//            jsonDeserializer
//        )
//    }
//
//    @Bean
//    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, Notification> {
//        val factory = ConcurrentKafkaListenerContainerFactory<String, Notification>()
//        factory.consumerFactory = notificationConsumerFactory()
//        return factory
//    }
//
//    private fun consumerConfig(): Map<String, Any> {
//        val props = HashMap<String, Any>()
//        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
//        props[ConsumerConfig.GROUP_ID_CONFIG] = groupId
//        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
//        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java
//        props[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest"
//        return props
//    }
//}