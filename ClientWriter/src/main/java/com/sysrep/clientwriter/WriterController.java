package com.sysrep.clientwriter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/write")
public class WriterController {

    private final MessageProducer messageProducer;

    public WriterController(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @PostMapping
    public ResponseEntity<String> write(@RequestParam String content) {
        String response = "";
        for (int i = 0; i < 3; i++) {
            String queueName = "write_queue_" + (i + 1);
            messageProducer.sendToReplica(queueName, content);
            response += "Message sent to " + queueName + "\n";
        }
        return ResponseEntity.ok(response);
    }
}
