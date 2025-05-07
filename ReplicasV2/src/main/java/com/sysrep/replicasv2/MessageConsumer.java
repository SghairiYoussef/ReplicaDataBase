package com.sysrep.replicasv2;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageConsumer {

    private final Writer writer;
    private final Reader reader;
    private final RabbitTemplate rabbitTemplate;

    @Value("${replica.id}")
    private int replicaId;

    public MessageConsumer(Writer writer, Reader reader, RabbitTemplate rabbitTemplate) {
        this.writer = writer;
        this.reader = reader;
        this.rabbitTemplate = rabbitTemplate;
    }

    private final String filePath = "./data/replica%d/data.txt".formatted(replicaId);

    @RabbitListener(queues = "${replica.write.queue}")
    public void receiveWrite(String message) {
        try {
            writer.appendLine(filePath, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "${replica.read.queue}")
    public void receiveReadRequest(Message message) {
        String payload = new String(message.getBody());
        String replyTo = message.getMessageProperties().getReplyTo();

        try {
            if (payload.equalsIgnoreCase("READ_ALL")) {
                List<String> lines = reader.readAllLines(filePath);
                for (String line : lines) {
                    rabbitTemplate.convertAndSend("", replyTo, line);
                }
            } else if (payload.equalsIgnoreCase("READ_LAST")) {
                String line = reader.readLastLine(filePath);
                rabbitTemplate.convertAndSend("", replyTo, line);
            } else {
                rabbitTemplate.convertAndSend("", replyTo, "Invalid read type");
            }
        } catch (Exception e) {
            rabbitTemplate.convertAndSend("", replyTo, "ERROR: " + e.getMessage());
        }
    }
}
