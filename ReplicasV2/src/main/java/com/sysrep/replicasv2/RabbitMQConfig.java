package com.sysrep.replicasv2;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${replica.write.queue}")
    private String writeQueue;

    @Value("${replica.read.queue}")
    private String readQueue;

    @Bean
    public Queue writeQueue() {
        return new Queue(writeQueue, true);
    }

    @Bean
    public Queue readQueue() {
        return new Queue(readQueue, true);
    }
}