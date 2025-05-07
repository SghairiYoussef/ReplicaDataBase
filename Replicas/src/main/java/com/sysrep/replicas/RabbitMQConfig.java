package com.sysrep.replicas;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitMQConfig {

    @Value("${replica.id}")
    private int replicaId;

    @Bean
    public Queue writeQueue() {
        return new Queue("write_queue_" + replicaId, true);
    }

    @Bean
    public Queue readQueue() {
        return new Queue("read_queue_" + replicaId, true);
    }
}
