package com.example.demokafkaconsumer.redis

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class DemoApplicationRunner(private val redisService: RedisService) : ApplicationRunner{
    override fun run(args: ApplicationArguments?) {
        println("Начинаю извлекать из редис")
        redisService.startPolling()
    }
}