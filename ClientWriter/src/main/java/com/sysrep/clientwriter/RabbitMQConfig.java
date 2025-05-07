package com.sysrep.clientwriter;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue writeQueue1() {
        return new Queue("write_queue_1", true);
    }

    @Bean
    public Queue writeQueue2() {
        return new Queue("write_queue_2", true);
    }

    @Bean
    public Queue writeQueue3() {
        return new Queue("write_queue_3", true);
    }
}
