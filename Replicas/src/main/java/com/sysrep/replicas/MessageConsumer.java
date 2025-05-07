package com.sysrep.replicas;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    private final Writer writer;
    private final Reader reader;

    public MessageConsumer(Writer writer, Reader reader) {
        this.writer = writer;
        this.reader = reader;
    }

    @RabbitListener(queues = "${replica.write.queue}")
    public void receiveWrite(String message) {
        try {
            writer.appendLine(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "${replica.read.queue}")
    public String receiveReadRequest(String messageType) {
        try {
            if (messageType.equalsIgnoreCase("READ_LAST")) {
                return reader.readLastLine();
            } else {
                return "Invalid read type";
            }
        } catch (Exception e) {
            return "Error processing read request";
        }
    }

}