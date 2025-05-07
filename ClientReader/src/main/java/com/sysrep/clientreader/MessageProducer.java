package com.sysrep.clientreader;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;

    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public String sendAndReceive(String queueName, String key) {
        return (String) rabbitTemplate.convertSendAndReceive(queueName, key);
    }

}

