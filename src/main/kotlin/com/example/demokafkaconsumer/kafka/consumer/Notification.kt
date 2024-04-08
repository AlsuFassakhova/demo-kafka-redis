package com.example.demokafkaconsumer.kafka.consumer;

import java.io.Serializable
import java.util.*


data class Notification(
    val userId: UUID,
    val message: String
) : Serializable

