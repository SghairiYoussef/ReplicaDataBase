package com.sysrep.clientreader;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${replica.read.queue}")
    private String READ_QUEUE;


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setReplyTimeout(5000);
        return template;
    }

    @Bean
    public Queue readQueue1() {
        return new Queue(READ_QUEUE + "_1", true);
    }

    @Bean
    public Queue readQueue2() {
        return new Queue(READ_QUEUE + "_2", true);
    }

    @Bean
    public Queue readQueue3() {
        return new Queue(READ_QUEUE + "_3", true);
    }
}

